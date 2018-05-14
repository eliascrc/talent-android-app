package cr.talent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import common.SessionStorage;
import networking.BaseResponse;
import networking.NetworkConstants;
import networking.NetworkError;
import request.ContentRequest;
import request.ServiceCallback;

/**
 * This class displays Talent’s active Terms of Service information.
 *
 * @author Renato Mainieri Sáenz.
 */
public class TermsOfServiceActivity extends AppCompatActivity {

    private String htmlCode;
    private View contentTemplate;
    private View noNetworkConnectionErrorLayout;
    private ServiceCallback serviceCallback;
    private WebView webView;
    private TextView contentTitle;
    private TextView contactUs;

    // Constant TAG, for the DEBUG log messages
    private static final String TAG = "TermsOfServiceActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
                Log.d(TAG, "The method onSuccessResponse receive:\n" + baseResponse.getResponse());
                htmlCode = baseResponse.getResponse();
                htmlCode = htmlCode.replace("h1", "h3");
                webView.loadData(htmlCode, "text/html; charset=UTF-8", null);
            }

            @Override
            public void onErrorResponse(NetworkError error) {
                Log.d(TAG, "The method onErrorResponse was executed.");
                if (error.getErrorCode() == 0) {
                    Log.d(TAG, "ERROR: NO NETWORK CONNECTION");
                    contentTemplate.setVisibility(View.GONE);
                    noNetworkConnectionErrorLayout.setVisibility(View.VISIBLE);
                }
            }

            public BaseResponse<String> getListener() {
                return listener;
            }
        };


        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_template);
        contentTemplate = findViewById(R.id.content_template_layout);
        noNetworkConnectionErrorLayout = findViewById(R.id.no_network_connection_error_layout);
        webView = (WebView) findViewById(R.id.content_web_view);
        contentTitle = (TextView) findViewById(R.id.content_title);
        contentTitle.setText(R.string.title_activity_terms_of_service);
        contactUs = (TextView) findViewById(R.id.contact_us);
        htmlCode = "";
        getSupportActionBar().hide();

        Button mRetryConnectionButton = (Button) findViewById(R.id.retry_connection_button);
        mRetryConnectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noNetworkConnectionErrorLayout.setVisibility(View.GONE);
                contentTemplate.setVisibility(View.VISIBLE);
                requestContent();
            }
        });

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                String email = getResources().getString(R.string.contact_us_email);
                emailIntent.setData(Uri.parse("mailto:" + email));
                startActivity(emailIntent);
            }
        });

        requestContent();
    }

    /**
     * Makes the request to the page that contains the terms of service of Talent !.
     * Checks also if there is a network connection error.
     */
    private void requestContent() {
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
        ContentRequest contentRequest =
                new ContentRequest(NetworkConstants.termsOfServiceURL, "", listener, errorListener, new SessionStorage());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(contentRequest);
    }
}
