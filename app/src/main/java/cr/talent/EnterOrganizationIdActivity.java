package cr.talent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.net.HttpURLConnection;

import common.SessionStorage;
import networking.BaseResponse;
import networking.NetworkConstants;
import networking.NetworkError;
import request.ContentRequest;
import request.ServiceCallback;

/**
 * The screen in which the user enters the id of the organization they want to sign in to.
 *
 * @author Fabián Roberto Leandro.
 */

public class EnterOrganizationIdActivity extends AppCompatActivity {

    // UI references from activity_enter_organization_id.xml
    private View enterOrganizationIdView;
    private Button enterOrganizationIdButton;
    private EditText organizationIdEditText;

    // UI references from no_network_connection_error.xml
    private View noNetworkConnectionErrorLayout;
    private Button retryConnectionButton;

    private ServiceCallback serviceCallback;

    // Constant TAG, for the DEBUG log messages
    private static final String TAG = "EnterOrganizationIdActivity";

    private static final String ORGANIZATION_JSON = "ORGANIZATION_JSON";

    // Visibility constants
    private static final int GONE = View.GONE;
    private static final int VISIBLE = View.VISIBLE;

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

                // Proceed to next activity, sending the info obtained
                String organizationJson = baseResponse.getResponse();
                Intent signInActivity = new Intent(EnterOrganizationIdActivity.this, SignInActivity.class);
                signInActivity.putExtra(ORGANIZATION_JSON,organizationJson);
                startActivity(signInActivity);
            }

            @Override
            public void onErrorResponse(NetworkError error) {
                Log.d(TAG, "The method onErrorResponse was executed with error code " + error.getErrorCode());
                if (error.getErrorCode() == 0) {
                    Log.d(TAG, "ERROR: NO NETWORK CONNECTION");
                    // Make organization id form invisible, display no network connection error layout
                    noNetworkConnectionErrorLayout.setVisibility(VISIBLE);
                    enterOrganizationIdView.setVisibility(GONE);
                } else if (error.getErrorCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    Log.d(TAG, "ERROR: 404 NOT FOUND");
                    // TODO Display incorrect organization id message
                    // Not in the currently approved design
                }
            }
        };

        setContentView(R.layout.activity_enter_organization_id);
        enterOrganizationIdView = findViewById(R.id.sv_enter_organization_id);
        organizationIdEditText = findViewById(R.id.et_organization_id);
        enterOrganizationIdButton = findViewById(R.id.btn_action_enter_organization_id);
        enterOrganizationIdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterOrganizationId(v);
            }
        });

        retryConnectionButton = findViewById(R.id.retry_connection_button);
        noNetworkConnectionErrorLayout = findViewById(R.id.no_network_connection_error_layout);
        // Implement a method for the button in the no network connectivity layout
        retryConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hide the no network connectivity layout and error messages in case they were visible
                noNetworkConnectionErrorLayout.setVisibility(GONE);
                enterOrganizationIdView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void enterOrganizationId(View view) {
        String organizationId = organizationIdEditText.getText().toString();

        if(!TextUtils.isEmpty(organizationId)) {

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

            // append parameter to URL
            String url = NetworkConstants.GET_ORGANIZATION_URL+organizationId;

            // Create and send request
            ContentRequest getOrganizationRequest = new ContentRequest(url, "", listener, errorListener, new SessionStorage());
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(getOrganizationRequest);
        }
    }
}