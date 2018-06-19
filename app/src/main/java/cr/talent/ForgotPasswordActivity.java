package cr.talent;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import common.ParameterEncoder;
import common.SessionStorage;
import common.ViewFormatUtil;
import networking.BaseResponse;
import networking.NetworkConstants;
import networking.NetworkError;
import request.ServiceCallback;
import request.EncodedPostRequest;

/**
 * The screen in which the user can reset their password.
 *
 * @author Fabián Roberto Leandro.
 */

public class ForgotPasswordActivity extends AppCompatActivity {

    // UI references from activity_forgot_password.xml
    private View forgotPasswordView;
    private EditText emailEditText;
    private TextView invalidEmailTextView;
    private WebView sentEmailMessageContent;
    private Button sendEmailButton;

    // UI reference to forgot_password_email_sent.xml
    private View emailSentView;

    // UI references from no_network_connection_error.xml
    private View noNetworkConnectionErrorLayout;
    private Button retryConnectionButton;

    // Constant TAG, for the DEBUG log messages
    private static final String TAG = "ForgotPasswordActivity";

    // Visibility constants
    private static final int VISIBLE = View.VISIBLE;
    private static final int INVISIBLE = View.INVISIBLE;
    private static final int GONE = View.GONE;

    private ServiceCallback serviceCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgot_password);
        forgotPasswordView = findViewById(R.id.forgot_password_sv_id);
        emailSentView = findViewById(R.id.forgot_password_email_sent_layout);
        emailEditText = findViewById(R.id.forgot_password_et_enter_email);
        sendEmailButton = findViewById(R.id.forgot_password_btn_send_email);
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(v);
            }
        });

        sentEmailMessageContent = findViewById(R.id.forgot_password_wv_sent_email_message_content);
        sentEmailMessageContent.setVerticalScrollBarEnabled(false);
        sentEmailMessageContent.loadData(getString(R.string.email_sent_message_content), "text/html; charset=utf-8", "utf-8");

        // Hide error message
        invalidEmailTextView = findViewById(R.id.forgot_password_tv_invalid_email);
        invalidEmailTextView.setVisibility(INVISIBLE);

        noNetworkConnectionErrorLayout = findViewById(R.id.no_network_connection_error_layout);
        // Implements a method for the button in the no network connectivity layout
        retryConnectionButton = findViewById(R.id.retry_connection_button);
        retryConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide the no network connectivity layout and error messages in case they were visible
                noNetworkConnectionErrorLayout.setVisibility(GONE);
                invalidEmailTextView.setVisibility(INVISIBLE);
                ViewFormatUtil.setEditContainerColor(R.color.dark_orange, emailEditText, ForgotPasswordActivity.this);

                forgotPasswordView.setVisibility(VISIBLE);
            }
        });

        // Implement inline the onPreExecute, onSuccess and onFailure methods of the ServiceCallback instance
        // They will be called when the sign in webservice returns
        serviceCallback = new ServiceCallback<BaseResponse<String>, NetworkError>() {
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
                // Do nothing, the user should be told if the email was sent or not
            }

            @Override
            public void onErrorResponse(NetworkError error) {
                Log.d(TAG, "The method onErrorResponse was executed with error code " + error.getErrorCode());
                if (error.getErrorCode() == 0) {
                    Log.d(TAG, "ERROR: NO NETWORK CONNECTION");
                    // Make login form invisible, display no network connection error layout
                    noNetworkConnectionErrorLayout.setVisibility(VISIBLE);
                    forgotPasswordView.setVisibility(GONE);
                } else if (error.getErrorCode() == 401) {
                    Log.d(TAG, "ERROR: 400 BAD REQUEST");
                    // Do nothing, the user should be told if the email was sent or not
                }
            }
        };
    }


    private void sendEmail(View view) {
        String email = emailEditText.getText().toString();
        ViewFormatUtil.setEditContainerColor(R.color.dark_orange, emailEditText, ForgotPasswordActivity.this);
        if(!TextUtils.isEmpty(email)) {

            if(!email.contains("@")) {
                // Show invalid email error message return
                invalidEmailTextView.setVisibility(VISIBLE);
                ViewFormatUtil.setEditContainerColor(R.color.error_text, emailEditText, ForgotPasswordActivity.this);
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
            parameters.put("email", email);
            String body = "";
            try {
                // Encodes the parameters with the ParameterEncoder class declared in common
                body = ParameterEncoder.encodeHashmap(parameters);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // Create and send request
            EncodedPostRequest sendEmailRequest = new EncodedPostRequest(NetworkConstants.FORGOT_PASSWORD_SEND_EMAIL_URL,
                    body, listener, errorListener, SessionStorage.getInstance());

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(sendEmailRequest);

            // User is shown the message whether or not the email is sent
            emailSentView.setVisibility(VISIBLE);
            forgotPasswordView.setVisibility(GONE);
        }
    }
}
