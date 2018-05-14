package cr.talent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import common.SessionStorage;
import networking.BaseResponse;
import networking.NetworkConstants;
import networking.NetworkError;
import request.ServiceCallback;
import request.SignInRequest;

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
    private TextView badEmailOrPasswordTextView;
    private TextView invalidEmailTextView;
    private ImageView organizationLogoImageView;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInButton;

    // UI references from no_network_connection_error.xml
    private View noNetworkConnectionErrorLayout;
    private Button retryConnectionButton;

    private ServiceCallback serviceCallback;

    // Constant TAG, for the DEBUG log messages
    private static final String TAG = "SignInActivity";

    private static final int SPAN_EXCLUSIVE = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
    private static final int GONE = View.GONE;
    private static final int VISIBLE = View.VISIBLE;
    private static final int INVISIBLE = View.INVISIBLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Implement inline the onPreExecute, onSuccess and onFailure methods of the ServiceCallback instance
        // They will be called when the sign in webservice returns
        serviceCallback = new ServiceCallback<BaseResponse<String>,NetworkError>() {
            private BaseResponse<String> listener;

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

            }

            @Override
            public void onErrorResponse(NetworkError error) {
                Log.d(TAG, "The method onErrorResponse was executed with error code " + error.getErrorCode());
                if (error.getErrorCode() == 0) {
                    Log.d(TAG, "ERROR: NO NETWORK CONNECTION");
                    // Make login form invisible, display no network connection error layout
                    noNetworkConnectionErrorLayout.setVisibility(VISIBLE);
                    signInView.setVisibility(GONE);
                } else if (error.getErrorCode() == 401) {
                    Log.d(TAG, "ERROR: 401 UNAUTHORIZED");
                    // Display invalid credentials error message
                    badEmailOrPasswordTextView.setVisibility(VISIBLE);
                    invalidEmailTextView.setVisibility(INVISIBLE);
                }
            }
        };

        // Access the organization sent by the previous activity
        String organizationJson = getIntent().getStringExtra("ORGANIZATION_JSON");

        setContentView(R.layout.activity_sign_in);
        organizationLogoImageView = findViewById(R.id.iv_organization_logo);
        // Set the organization's logo in the appropriate ImageView
        // For this, first get the link to the organization's logo
        String logoUrl = "";
        try {
            JSONObject organizationJsonObject = new JSONObject(organizationJson);
            logoUrl = organizationJsonObject.getString("logo");
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "logo is "+logoUrl);

        new GetOrganizationLogoTask(organizationLogoImageView).execute(logoUrl);

        signInView = findViewById(R.id.sv_sign_in_form);
        emailEditText = findViewById(R.id.et_email);
        passwordEditText = findViewById(R.id.et_password);
        signUpTextView = findViewById(R.id.tv_no_account_sign_up);
        noNetworkConnectionErrorLayout = findViewById(R.id.no_network_connection_error_layout);
        retryConnectionButton = findViewById(R.id.retry_connection_button);
        signInButton = findViewById(R.id.btn_action_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(v);
            }
        });

        forgotPasswordTextView = findViewById(R.id.tv_forgot_password);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startForgotPasswordActivity(v);
            }
        });

        badEmailOrPasswordTextView = findViewById(R.id.tv_bad_login);
        invalidEmailTextView = findViewById(R.id.tv_invalid_email);
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

        // Implements a method for the button in the no network connectivity layout
        retryConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide the no network connectivity layout and error messages in case they were visible
                noNetworkConnectionErrorLayout.setVisibility(GONE);
                badEmailOrPasswordTextView.setVisibility(INVISIBLE);
                invalidEmailTextView.setVisibility(INVISIBLE);
                signInView.setVisibility(VISIBLE);
            }
        });
    }

    public void startForgotPasswordActivity(View view){
        Intent forgotPasswordActivity = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
        startActivity(forgotPasswordActivity);
    }

    // Get data in the et_email and et_password EditTexts and attempt to sign in with it
    public void signIn(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            if(!email.contains("@")) {
                // Show invalid email error message, hide others and return
                invalidEmailTextView.setVisibility(VISIBLE);
                badEmailOrPasswordTextView.setVisibility(INVISIBLE);
                return;
            }

            // Instantiate listeners to send to a SignInRequest instance
            Response.Listener<BaseResponse<String>> listener = new Response.Listener<BaseResponse<String>>() {
                @Override
                public void onResponse(BaseResponse<String> response) {
                    serviceCallback.onSuccessResponse(response);
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    NetworkError networkError = new NetworkError();
                    if (error.networkResponse != null) {
                        networkError.setErrorCode(error.networkResponse.statusCode);
                        networkError.setErrorMessage(error.getMessage());
                    }
                    serviceCallback.onErrorResponse(networkError);
                }
            };

            // Form the request's body
            HashMap<String,String> parameters = new HashMap<>();
            parameters.put("username", email);
            parameters.put("password",password);
            String body = "";
            try {
                body = encodeParameters(parameters);
            } catch (UnsupportedEncodingException e) {

            }

            // Create and send request
            SignInRequest signInRequest = new SignInRequest(NetworkConstants.SIGN_IN_URL, body,
                    listener, errorListener, new SessionStorage());

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(signInRequest);
        }
    }

    // Receives a key and value hashmap and encodes its values to be sent in an http request
    private String encodeParameters(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    // Used to get the organization-s log asynchronously 
    private class GetOrganizationLogoTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public GetOrganizationLogoTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

