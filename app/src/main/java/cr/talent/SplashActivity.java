package cr.talent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import common.ParameterEncoder;
import common.SessionStorage;
import common.UserSharedPreference;
import networking.BaseResponse;
import networking.NetworkConstants;
import networking.NetworkError;
import request.EncodedPostRequest;
import request.ServiceCallback;


/**
 * This class starts the splash activity for the Talent! application. When the theme(logo) is displayed it will route the
 * user to the login activity. Notice that the login activity design is not the final design.
 *
 * @author Josué David Cubero Sánchez.
 */
public class SplashActivity extends AppCompatActivity {

    private static final String TOKEN = "token";
    private static final String TAG = "SplashActivity";

    private ServiceCallback serviceCallback;
    private SessionStorage sessionStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        sessionStorage = new SessionStorage();
        String token = UserSharedPreference.getToken(SplashActivity.this);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        Log.d(TAG, token);
        if (token.equals("")){
            Intent startActivity = new Intent(SplashActivity.this, LandingViewActivity.class);
            SplashActivity.this.startActivity(startActivity);
        }
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
                Log.d("SplashActivity", error.networkResponse.allHeaders.toString());
                if (error.networkResponse != null) {
                    networkError.setErrorCode(error.networkResponse.statusCode);
                    networkError.setErrorMessage(error.getMessage());
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

        RequestQueue requestQueue = Volley.newRequestQueue(this, new HurlStack(){
            @Override
            protected HttpURLConnection createConnection (URL url) throws IOException {
            HttpURLConnection connection = super.createConnection(url);
            connection.setInstanceFollowRedirects(false);
            return connection;
        }
        });
        requestQueue.add(signInTokenRequest);

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
                Intent startActivity = new Intent(SplashActivity.this, LoggedInActivity.class);
                SplashActivity.this.startActivity(startActivity);
            }

            @Override
            public void onErrorResponse(NetworkError error) {
                Log.d(TAG, "The method onErrorResponse was executed with error code " + error.getErrorCode());
                Intent startActivity = new Intent(SplashActivity.this, LandingViewActivity.class);
                SplashActivity.this.startActivity(startActivity);
            }
        };
    }
}
