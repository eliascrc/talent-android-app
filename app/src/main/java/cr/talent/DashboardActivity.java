package cr.talent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;



import java.io.IOException;


import java.net.HttpURLConnection;
import java.net.URL;


import javax.ejb.EJB;

import common.SessionStorage;
import common.UserSharedPreference;
import model.User;
import networking.BaseResponse;
import networking.NetworkConstants;
import networking.NetworkError;
import request.AuthenticatedRequest;
import request.ServiceCallback;

/**
 * This class only exists to let us know that we are logged in, and to show an implementation of
 * the logout feature.
 *
 * @author Otto Mena Kikut.
 *
 */
public class DashboardActivity extends AppCompatActivity {

    // Constant TAG, for the DEBUG log messages
    private static final String TAG = "LoggedInActivity";

    private Button logoutButton;
    private TextView contactUs;
    private TextView aboutUs;

    private ServiceCallback serviceCallback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_logged_in);
        aboutUs = findViewById(R.id.logged_in_about_us);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutUsActivity = new Intent(DashboardActivity.this, AboutUsActivity.class);
                DashboardActivity.this.startActivity(aboutUsActivity);
            }
        });
        contactUs = findViewById(R.id.logged_in_contact_us);
        contactUs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent contactUsActivity = new Intent(DashboardActivity.this, ContactUsActivity.class);
                DashboardActivity.this.startActivity(contactUsActivity);
            }
        });
        logoutButton = findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut(v);
            }
        });
        serviceCallback = new ServiceCallback<BaseResponse<User>,NetworkError>() {
            public BaseResponse<User> listener;


            @Override
            public void onPreExecute(BaseResponse<User> listener) {
                Log.d(TAG, "The method onPreExecute was executed.");
                this.listener = listener;
            }

            @Override
            public void onSuccessResponse(BaseResponse<User> baseResponse) {
                Log.d(TAG, "The method onSuccessResponse was executed.");
                Log.d(TAG, "The method onSuccessResponse received the " + baseResponse.getHttpStatusCode()+" HTTP status code.");
                // Proceed to next activity with a logged in user
                UserSharedPreference.removeToken(DashboardActivity.this);
                Intent loggedInActivity = new Intent(DashboardActivity.this, LandingViewActivity.class);
                DashboardActivity.this.startActivity(loggedInActivity);
            }

            @Override
            public void onErrorResponse(NetworkError error) {
                int errorCode = error.getErrorCode();
                if (errorCode == 0) {
                    Log.d(TAG, "ERROR: NO NETWORK CONNECTION");
                }
                else if (errorCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    Log.d(TAG, "ERROR: 401 UNAUTHORIZED");
                    // Display invalid credentials error message
                }

            }
        };
    }

    private void logOut(View view) {

            // Instantiate listeners to send to the request instance
        final Response.Listener<BaseResponse<User>> listener = new Response.Listener<BaseResponse<User>>() {
            @Override
            public void onResponse(BaseResponse<User> response) {
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


            // Create and send request
            AuthenticatedRequest logoutRequest = new AuthenticatedRequest(NetworkConstants.LOGOUT_URL,"",
                    listener, errorListener, SessionStorage.getInstance());
            RequestQueue requestQueue = Volley.newRequestQueue(this, new HurlStack() {
                @Override
                protected HttpURLConnection createConnection(URL url) throws IOException {
                    HttpURLConnection connection = super.createConnection(url);
                    connection.setInstanceFollowRedirects(false);

                    return connection;
                }
            });
            requestQueue.add(logoutRequest);
    }
}
