package networking;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;


import java.util.Map;
import java.util.HashMap;

import common.SessionStorage;

/**
 * This class makes a network request so that common configuration like retry policy is consolidated.
 *
 * @author Renato Mainieri Sáenz.
 */

public abstract class BaseRequest<T> extends JsonRequest<T> {

    public static final int DEFAULT_TIMEOUT_MS = 2500;
    public static final int DEFAULT_MAX_RETRIES = 0;
    public static final float DEFAULT_BACKOFF = 1f;
    private SessionStorage sessionStorage;
    private Response.ErrorListener errorListener;
    protected Response.Listener<T> listener;

    public BaseRequest(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        this.setRetryPolicy(defaultRetryPolicy);
        this.listener = listener;
        this.errorListener = errorListener;
    }

    public BaseRequest(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener, SessionStorage sessionStorage) {
        super(method, url, requestBody, listener, errorListener);
        DefaultRetryPolicy defaultRetryPolicy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        this.setRetryPolicy(defaultRetryPolicy);
        this.listener = listener;
        this.errorListener = errorListener;
        this.sessionStorage = new SessionStorage();
    }

    public SessionStorage getSessionStorage() {
        return sessionStorage;
    }

    public void setSessionStorage(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }
    @Override
    public Map<String, String> getHeaders(){
        Map<String,String> params = new HashMap<String, String>();
        params.put("Origin", "Android");
        params.put("Cookie", sessionStorage.getCookieValue());
        return params;
    }

    /**
     * Encapsulates a parsed response for delivery.
     *
     * @param response, contains the response payload.
     * @return a response and cache metadata or an error, such as in the case of a parse failure.
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String headers = response.headers.get("Set-Cookie");
        if (headers != null) {
            headers = headers.substring(0, headers.indexOf(";"));
            this.sessionStorage.setCookieValue(headers);
        }
        return null;

    }

    /**
     * Create an augmented error with additional information.
     *
     * @param volleyError, error retrieved from the network.
     * @return a more specific error than the parameter.
     */
    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return volleyError;
    }


}
