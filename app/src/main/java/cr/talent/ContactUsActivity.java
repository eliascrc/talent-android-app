package cr.talent;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;

import javax.ejb.EJB;

import common.ParameterEncoder;
import common.SessionStorage;
import common.UserSharedPreference;
import networking.BaseResponse;
import networking.HurlStackNoRedirect;
import networking.NetworkConstants;
import networking.NetworkError;
import request.AuthenticatedRequest;
import request.EncodedPostRequest;
import request.ServiceCallback;

import static networking.NetworkConstants.USER_AUTHENTICATED;

public class ContactUsActivity extends AppCompatActivity {

    private View contactUsView;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText issueEditText;
    private Spinner issueTypeSpinner;
    private TextView emptyFirstName;
    private TextView emptyLastName;
    private TextView emptyEmail;
    private TextView emptyIssue;
    private TextView emptyIssueType;
    private TextView invalidEmailLabel;
    private Button submitButton;
    private Button confirmButton;
    private TextView issueTypeLabel;
    private TextView emailLabel;
    private TextView firstNameLabel;
    private TextView lastNameLabel;


    private ServiceCallback serviceCallback;
    @EJB
    private SessionStorage sessionStorage;

    // Constant TAG, for the DEBUG log messages
    private static final String TAG = "ContactUsActivity";
    private static final String COOKIE_HEADER_KEY = "Set-Cookie";
    private static final String SEMICOLON = ";";
    private static final String TOKEN = "token";
    private static final String FIRSTNAME = "firstName";
    private static final String LASTNAME = "lastName";
    private static final String EMAIL = "email";
    private static final String ISSUETYPE = "issueType";
    private static final String ISSUE = "issue";

    boolean authenticated;
    boolean submitted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        submitted = false;
        authenticated = false;
        sessionStorage = new SessionStorage();
        setContentView(R.layout.activity_contact_us);
        initializeViews();
        String token = UserSharedPreference.getToken(ContactUsActivity.this);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRequestBody(view);
            }
        });
        Log.d(TAG, token);
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
                if(submitted){
                    setContentView(R.layout.contact_us_confirmation);
                    confirmButton = findViewById(R.id.contact_us_btn_confirm);
                    confirmButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(authenticated){
                                Intent loggedInActivity = new Intent(ContactUsActivity.this, LoggedInActivity.class);
                                ContactUsActivity.this.startActivity(loggedInActivity);
                            } else{
                                Intent landingViewActivity = new Intent(ContactUsActivity.this, LandingViewActivity.class);
                                ContactUsActivity.this.startActivity(landingViewActivity);
                            }
                        }
                    });
                } else{
                    loggedInView();
                }
            }

            @Override
            public void onErrorResponse(NetworkError error) {
                Log.d(TAG, "The method onErrorResponse was executed with error code " + error.getErrorCode());
                int errorCode = error.getErrorCode();
                if (errorCode == 0) {
                    Log.d(TAG, "ERROR: NO NETWORK CONNECTION");
                } else if (errorCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                    createRedirectRequest();
                }
                else if (!authenticated && errorCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    nonLoggedInView();
                }
            }
        };
        if (token.equals("")){
            loggedInRequest(token);
        }else{
            loggedInRequest(token);
        }
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
                listener, errorListener, sessionStorage);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Log.d(TAG, "a");
        requestQueue.add(redirectRequest);
    }



    private void loggedInRequest(String token){
        // Instantiate listeners to send to the request instance
        Response.Listener<BaseResponse<String>> listener = new Response.Listener<BaseResponse<String>>() {
            @Override
            public void onResponse(BaseResponse<String> response) {
                serviceCallback.onSuccessResponse(response);
            }
        };
        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkError networkError = new NetworkError();
                networkError.setErrorCode(error.networkResponse.statusCode);
                networkError.setErrorMessage(error.networkResponse.toString());
                Log.d(TAG, error.networkResponse.allHeaders.toString());
                if(error.networkResponse.headers.containsKey(COOKIE_HEADER_KEY)){
                    String cookie = error.networkResponse.headers.get(COOKIE_HEADER_KEY);
                    cookie = cookie.substring(0, cookie.indexOf(SEMICOLON));
                    Log.d(TAG, cookie);
                    sessionStorage.setCookieValue(cookie);
                }
                serviceCallback.onErrorResponse(networkError);
            }
        };

        // Form the request's body
        HashMap<String,String> parameters = new HashMap<>();
        parameters.put(TOKEN , token);
        String body = "";
        try {
            // Encodes the parameters with the ParameterEncoder class declared in common
            body = ParameterEncoder.encodeHashmap(parameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Create and send request
        Log.d(TAG, body);
        EncodedPostRequest signInTokenRequest = new EncodedPostRequest(NetworkConstants.SIGN_IN_TOKEN_URL, body,
                listener, errorListener, sessionStorage);

        RequestQueue requestQueue = Volley.newRequestQueue(this, new HurlStackNoRedirect());
        requestQueue.add(signInTokenRequest);
    }

    private void loggedInView(){
        authenticated = true;
        hideUserEditText();
        ViewGroup.MarginLayoutParams paramsLabel =   (ViewGroup.MarginLayoutParams) issueTypeLabel.getLayoutParams();
        paramsLabel.topMargin = dpToPx(97);
        issueTypeLabel.setLayoutParams(paramsLabel);
        ViewGroup.MarginLayoutParams paramsEditText = (ViewGroup.MarginLayoutParams) issueEditText.getLayoutParams();
        paramsEditText.height = dpToPx(319);
        issueEditText.setLayoutParams(paramsEditText);
    }

    private void nonLoggedInView(){
        authenticated = false;
    }

    private void contactUsRequest(HashMap<String, String> parameters){
        // Instantiate listeners to send to the request instance
        Response.Listener<BaseResponse<String>> listener = new Response.Listener<BaseResponse<String>>() {
            @Override
            public void onResponse(BaseResponse<String> response) {
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

        String body = "";
        try {
            // Encodes the parameters with the ParameterEncoder class declared in common
            body = ParameterEncoder.encodeHashmap(parameters);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // Create and send request
        EncodedPostRequest contactUs;
        if(authenticated){
            contactUs = new EncodedPostRequest(NetworkConstants.CONTACT_US_AUTHENTICATED,body,
                    listener, errorListener, sessionStorage);
        } else{
            contactUs = new EncodedPostRequest(NetworkConstants.CONTACT_US_UNAUTHENTICATED,body,
                    listener, errorListener, sessionStorage);
        }
        Log.d(TAG, contactUs.getHeaders().toString());
        RequestQueue requestQueue = Volley.newRequestQueue(this, new HurlStackNoRedirect());
        submitted = true;
        requestQueue.add(contactUs);
    }

    private void createRequestBody(View v){
        hideErrorText();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String issueType = issueTypeSpinner.getPrompt().toString();
        String issue = issueEditText.getText().toString();
        if (!authenticated && validFields(firstName, lastName, email, issueType, issue)){
            HashMap<String,String> parameters = new HashMap<>();
            parameters.put(FIRSTNAME, firstName);
            parameters.put(LASTNAME, lastName);
            parameters.put(EMAIL, email);
            parameters.put(ISSUETYPE, issueType);
            parameters.put(ISSUE, issue);
            contactUsRequest(parameters);
            Log.d(TAG, parameters.toString());
        }
        if(authenticated && validFields(firstName, lastName, email, issueType, issue)){
            HashMap<String,String> parameters = new HashMap<>();
            parameters.put(ISSUETYPE, issueType);
            parameters.put(ISSUE, issue);
            contactUsRequest(parameters);
            Log.d(TAG, parameters.toString());
        }
    }

    private void hideErrorText(){
        emptyFirstName.setVisibility(View.INVISIBLE);
        emptyLastName.setVisibility(View.INVISIBLE);
        emptyEmail.setVisibility(View.INVISIBLE);
        emptyIssue.setVisibility(View.INVISIBLE);
        emptyIssueType.setVisibility(View.INVISIBLE);
        invalidEmailLabel.setVisibility(View.INVISIBLE);
        setEditContainerColor(R.color.dark_orange, firstNameEditText);
        setEditContainerColor(R.color.dark_orange, lastNameEditText);
        setEditContainerColor(R.color.dark_orange, emailEditText);
        setEditContainerColor(R.color.dark_orange, issueEditText);
    }

    private void hideUserEditText(){
        emailLabel.setVisibility(View.INVISIBLE);
        firstNameLabel.setVisibility(View.INVISIBLE);
        lastNameLabel.setVisibility(View.INVISIBLE);
        emailEditText.setVisibility(View.INVISIBLE);
        firstNameEditText.setVisibility(View.INVISIBLE);
        lastNameEditText.setVisibility(View.INVISIBLE);

    }

    private boolean validFields(String firstName, String lastName, String email, String issueType, String issue){
        boolean valid = true;
        if(!authenticated && TextUtils.isEmpty(firstName)){
            valid = false;
            emptyFirstName.setVisibility(View.VISIBLE);
            setEditContainerColor(R.color.error_text, firstNameEditText);
        } if(!authenticated && TextUtils.isEmpty(lastName)){
            valid = false;
            emptyLastName.setVisibility(View.VISIBLE);
            setEditContainerColor(R.color.error_text, lastNameEditText);
        } if(!authenticated && TextUtils.isEmpty(email)){
            valid = false;
            emptyEmail.setVisibility(View.VISIBLE);
            setEditContainerColor(R.color.error_text, emailEditText);
        } else if (!authenticated && !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            valid = false;
            invalidEmailLabel.setVisibility(View.VISIBLE);
            setEditContainerColor(R.color.error_text, emailEditText);
        } if(TextUtils.isEmpty(issueType)){
            valid = false;
            emptyIssueType.setVisibility(View.VISIBLE);
        } if(TextUtils.isEmpty(issue)){
            valid = false;
            emptyIssue.setVisibility(View.VISIBLE);
            setEditContainerColor(R.color.error_text, issueEditText);
        }
        return valid;
    }
    private void initializeViews (){
        issueTypeLabel =  findViewById(R.id.contact_us_tv_issue_type);
        emailLabel = findViewById(R.id.contact_us_tv_email);
        firstNameLabel = findViewById(R.id.contact_us_tv_first_name);
        lastNameLabel = findViewById(R.id.contact_us_tv_last_name);
        contactUsView = findViewById(R.id.contact_us_layout);
        firstNameEditText = findViewById(R.id.contact_us_et_first_name);
        lastNameEditText = findViewById(R.id.contact_us_et_last_name);
        emailEditText = findViewById(R.id.contact_us_et_email);
        issueEditText = findViewById(R.id.contact_us_et_issue);
        issueTypeSpinner = findViewById(R.id.contact_us_sp_issue);
        emptyFirstName = findViewById(R.id.contact_us_tv_first_name_required);
        emptyLastName = findViewById(R.id.contact_us_tv_last_name_required);
        emptyEmail = findViewById(R.id.contact_us_tv_email_required);
        emptyIssue = findViewById(R.id.contact_us_tv_issue_required);
        emptyIssueType = findViewById(R.id.contact_us_tv_issue_type_required);
        submitButton = findViewById(R.id.contact_us_btn_submit);
        invalidEmailLabel = findViewById(R.id.contact_us_tv_email_invalid);
    }
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = ContactUsActivity.this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void setEditContainerColor(int color, EditText container) {
        GradientDrawable drawable = (GradientDrawable) container.getBackground();
        drawable.setStroke(dpToPx(1), getResources().getColor(color));
    }
}
