package cr.talent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;


import javax.ejb.EJB;

import common.ParameterEncoder;
import common.SessionStorage;
import common.UserSharedPreference;
import common.ViewFormatUtil;
import networking.BaseResponse;
import networking.HurlStackNoRedirect;
import networking.NetworkConstants;
import networking.NetworkError;
import request.AuthenticatedRequest;
import request.ServiceCallback;
import request.EncodedPostRequest;

import static networking.NetworkConstants.USER_AUTHENTICATED;

/**
 * The screen in which the user signs in to an organization
 *
 * @author Fabián Roberto Leandro.
 */

public class SignInActivity extends AppCompatActivity {


    // UI references from activity_sign_in.xml
    private View signInView;
    private TextView signUpTextView;
    private TextView forgotPasswordTextView;
    private TextView getForgotPasswordTextView;
    private TextView badEmailOrPasswordTextView;
    private TextView invalidEmailTextView;
    private ImageView organizationLogoImageView;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInButton;

    // UI references from no_network_connection_error.xml
    private View noNetworkConnectionErrorLayout;
    private Button retryConnectionButton;

    //Instead of this variable we will import the Organization model class.
    private String organizationId;

    private ServiceCallback serviceCallback;

    // Constant TAG, for the DEBUG log messages
    private static final String TAG = "SignInActivity";

    private static final int SPAN_EXCLUSIVE = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
    private static final String USER_JSON = "USER_JSON";

    // Visibility constants
    private static final int GONE = View.GONE;
    private static final int VISIBLE = View.VISIBLE;
    private static final int INVISIBLE = View.INVISIBLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        organizationLogoImageView = findViewById(R.id.sign_in_iv_organization_logo);
        organizationLogoImageView.setVisibility(INVISIBLE);
        // Set the organization's logo in the appropriate ImageView
        // For this, first access the organization sent by the previous activity
        String organizationJson = getIntent().getStringExtra("ORGANIZATION_JSON");
        // Then get the link to the organization's logo
        String logoUrl = "";
        try {
            JSONObject organizationJsonObject = new JSONObject(organizationJson);
            logoUrl = organizationJsonObject.getString("logo");
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Organization's logo is "+logoUrl);
        //Get the uniqueIdentifier, this parsing will only be done once in the EnterOrganizationIdActivity.
        organizationId = "";
        try {
            JSONObject organizationJsonObject = new JSONObject(organizationJson);
            organizationId = organizationJsonObject.getString("uniqueIdentifier");
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        // Load the logo asynchronously
        new GetOrganizationLogoTask(organizationLogoImageView).execute(logoUrl);
        signInView = findViewById(R.id.sign_in_sv_sign_in_form);
        emailEditText = findViewById(R.id.sign_in_et_email);
        passwordEditText = findViewById(R.id.sign_in_et_password);
        signUpTextView = findViewById(R.id.sign_in_tv_no_account_sign_up);
        signInButton = findViewById(R.id.sign_in_btn_action_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRequestBody(v);
            }
        });

        forgotPasswordTextView = findViewById(R.id.sign_in_tv_forgot_password);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForgotPasswordActivity(v);
            }
        });

        badEmailOrPasswordTextView = findViewById(R.id.sign_in_tv_bad_login);
        invalidEmailTextView = findViewById(R.id.sign_in_tv_invalid_email);
        // Hide the error messages
        badEmailOrPasswordTextView.setVisibility(INVISIBLE);
        invalidEmailTextView.setVisibility(INVISIBLE);

        // Makes the "Sign Up" part of the TextView clickable
        SpannableString noAccountSignIn = new SpannableString(getString(R.string.label_no_account_sign_up));
        noAccountSignIn.setSpan(new ClickableSpan(){
            // Anonymous class that extends ClickableSpan
            // Need this to start the sign up activity in the custom implementation of onClick()
            @Override
            public void onClick(View textView) {
                // start the sign up activity here
                Intent signUpActivity = new Intent(SignInActivity.this, SignUpFirstStepActivity.class);
                SignInActivity.this.startActivity(signUpActivity);
            }
        }, 23,30, SPAN_EXCLUSIVE);
        signUpTextView.setMovementMethod(LinkMovementMethod.getInstance());
        signUpTextView.setText(noAccountSignIn);

        // Implement inline the onPreExecute, onSuccess and onFailure methods of the ServiceCallback instance
        // They will be called when the sign in webservice returns
        serviceCallback = new ServiceCallback<BaseResponse<String>,NetworkError>() {
            public BaseResponse<String> listener;


            @Override
            public void onPreExecute(BaseResponse<String> listener) {
                Log.d(TAG, "The method onPreExecute was executed.");
                this.listener = listener;
            }
            @Override
            public void onSuccessResponse(BaseResponse<String> baseResponse) {
                Log.d(TAG, "The method onSuccessResponse was executed.");
                Log.d(TAG, "The method onSuccessResponse received the " + baseResponse.getHttpStatusCode()+" HTTP status code.");
                // Proceed to next activity with a logged in user
                Intent loggedInActivity = new Intent(SignInActivity.this, DashboardActivity.class);
                String userJson = baseResponse.getResponse();
                try {
                    JSONObject reader = new JSONObject(userJson);
                    String token = reader.getString(NetworkConstants.TOKEN);
                    Log.d(TAG, token);
                    UserSharedPreference.setToken(SignInActivity.this, token);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loggedInActivity.putExtra(USER_JSON,userJson);
                SignInActivity.this.startActivity(loggedInActivity);
            }

            @Override
            public void onErrorResponse(NetworkError error) {
                int errorCode = error.getErrorCode();
                if (errorCode == 0) {
                    Log.d(TAG, "ERROR: NO NETWORK CONNECTION");
                    // Make login form invisible, display no network connection error layout
                    noNetworkConnectionErrorLayout.setVisibility(VISIBLE);
                    signInView.setVisibility(GONE);
                } else if (errorCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                    createRedirectRequest();

                }
                else if (errorCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    Log.d(TAG, "ERROR: 401 UNAUTHORIZED");
                    // Display invalid credentials error message
                    toggleCredentialsError(true);
                }

            }
        };

        noNetworkConnectionErrorLayout = findViewById(R.id.no_network_connection_error_layout);
        retryConnectionButton = findViewById(R.id.retry_connection_button);
        // Implement a method for the button in the no network connectivity layout
        retryConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide the no network connectivity layout and error messages in case they were visible
                noNetworkConnectionErrorLayout.setVisibility(GONE);
                toggleCredentialsError(false);
                signInView.setVisibility(VISIBLE);
            }
        });
    }

    private void toggleCredentialsError (boolean show){
        if (show){
            badEmailOrPasswordTextView.setVisibility(VISIBLE);
            invalidEmailTextView.setVisibility(INVISIBLE);
        }
        else{
            badEmailOrPasswordTextView.setVisibility(INVISIBLE);
            invalidEmailTextView.setVisibility(INVISIBLE);
        }
    }

    private void startForgotPasswordActivity(View view){
        Intent forgotPasswordActivity = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
        startActivity(forgotPasswordActivity);
    }


    // Get data in the et_email and et_password EditTexts and attempt to sign in with it
    private void signInRequest(HashMap<String, String> parameters) {
        // Instantiate listeners to send to the request instance
        final Response.Listener<BaseResponse<String>> listener = new Response.Listener<BaseResponse<String>>() {
            @Override
            public void onResponse(BaseResponse<String> response) {
                Log.d(TAG,Integer.toString(response.getHttpStatusCode()));
                serviceCallback.onSuccessResponse(response);
            }
        };
        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkError networkError = new NetworkError();
                networkError.setErrorCode(error.networkResponse.statusCode);
                networkError.setErrorMessage(error.networkResponse.toString());
                serviceCallback.onErrorResponse(networkError);
            }
        };

        // Form the request's body

        String body = "";
        try {
            // Encodes the parameters with the ParameterEncoder class declared in common
            body = ParameterEncoder.encodeHashmap(parameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Create and send request
        EncodedPostRequest signInRequest = new EncodedPostRequest(NetworkConstants.SIGN_IN_URL,body,
                listener, errorListener, SessionStorage.getInstance());
        Log.d(TAG, signInRequest.getHeaders().toString());
        RequestQueue requestQueue = Volley.newRequestQueue(this, new HurlStackNoRedirect());
        requestQueue.add(signInRequest);
    }


    private void createRequestBody(View v){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if(validFields(email, password)){
            HashMap<String,String> parameters = new HashMap<>();
            parameters.put(NetworkConstants.PASSWORD,password);
            parameters.put(NetworkConstants.USERNAME, email);
            //when the model is created we will get this parameter from the Organization model class.
            parameters.put(NetworkConstants.ORGANIZATION_IDENTIFIER, organizationId);
            signInRequest(parameters);
            Log.d(TAG, parameters.toString());
        }



    }
    private boolean validFields(String email, String password) {
        ViewFormatUtil.setEditContainerColor(R.color.dark_orange, emailEditText, SignInActivity.this);
        boolean valid = true;
        if (TextUtils.isEmpty(email)) {
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            valid = false;
            invalidEmailTextView.setVisibility(VISIBLE);
            ViewFormatUtil.setEditContainerColor(R.color.error_text, emailEditText, SignInActivity.this);
            badEmailOrPasswordTextView.setVisibility(INVISIBLE);
        }
        if (TextUtils.isEmpty(password)) {
            valid = false;
        }
        return valid;

    }

    private void createRedirectRequest(){
        // Instantiate listeners to send to the request instance
        final Response.Listener<BaseResponse<Object>> listener = new Response.Listener<BaseResponse<Object>>() {
            @Override
            public void onResponse(BaseResponse<Object> response) {
                Log.d(TAG,Integer.toString(response.getHttpStatusCode()));
                serviceCallback.onSuccessResponse(response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkError networkError = new NetworkError();
                networkError.setErrorCode(error.networkResponse.statusCode);
                networkError.setErrorMessage(error.networkResponse.toString());
                serviceCallback.onErrorResponse(networkError);
            }
        };
        AuthenticatedRequest redirectRequest = new AuthenticatedRequest(USER_AUTHENTICATED, "",
                listener, errorListener, SessionStorage.getInstance());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(redirectRequest);
    }


    // Used to get the organization-s log asynchronously 
    private class GetOrganizationLogoTask extends AsyncTask<String, Void, Bitmap> {
        ImageView logoImageView;

        public GetOrganizationLogoTask(ImageView logoImageView) {
            this.logoImageView = logoImageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap logo = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                logo = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result) {
            logoImageView.setImageBitmap(result);
            logoImageView.setVisibility(VISIBLE);
        }
    }
}

