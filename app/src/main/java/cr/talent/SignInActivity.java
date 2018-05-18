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

import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import common.ParameterEncoder;
import common.SessionStorage;
import networking.BaseResponse;
import networking.NetworkConstants;
import networking.NetworkError;
import request.ServiceCallback;
import request.EncodedPostRequest;

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
                signIn(v);
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
                    setEmailEditTextColor(R.color.dark_orange);
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
                badEmailOrPasswordTextView.setVisibility(INVISIBLE);
                invalidEmailTextView.setVisibility(INVISIBLE);
                setEmailEditTextColor(R.color.dark_orange);
                signInView.setVisibility(VISIBLE);
            }
        });
    }

    private void startForgotPasswordActivity(View view){
        Intent forgotPasswordActivity = new Intent(SignInActivity.this, ForgotPasswordActivity.class);
        startActivity(forgotPasswordActivity);
    }

    // Get data in the et_email and et_password EditTexts and attempt to sign in with it
    private void signIn(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            if(!email.contains("@")) {
                // Show invalid email error message, hide others and return
                invalidEmailTextView.setVisibility(VISIBLE);
                setEmailEditTextColor(R.color.error_text);
                badEmailOrPasswordTextView.setVisibility(INVISIBLE);
                return;
            }

            // Instantiate listeners to send to the request instance
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
                // Encodes the parameters with the ParameterEncoder class declared in common
                body = ParameterEncoder.encodeHashmap(parameters);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // Create and send request
            EncodedPostRequest signInRequest = new EncodedPostRequest(NetworkConstants.SIGN_IN_URL, body,
                    listener, errorListener, new SessionStorage());

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(signInRequest);
        }
    }

    // Set the email edit text's color
    private void setEmailEditTextColor(int color) {
        GradientDrawable drawable = (GradientDrawable) emailEditText.getBackground();
        drawable.setStroke(1, getResources().getColor(color));
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

