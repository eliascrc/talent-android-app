package networking;

import android.os.Bundle;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;

import common.SessionStorage;

/**
 * This class makes a network request so that common configuration like retry policy is consolidated.
 *
 * @author Renato Mainieri Sáenz.
 */

public abstract class BaseRequest<T> extends JsonRequest<T> {

    private static final int DEFAULT_TIMEOUT_MS = 2500;
    private static final int DEFAULT_MAX_RETRIES = 0;
    private static final float DEFAULT_BACKOFF = 1f;
    private SessionStorage sessionStorage;

    public BaseRequest(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        this.sessionStorage = new SessionStorage();
    }

    public static int getDefaultTimeout() {
        return DEFAULT_TIMEOUT_MS;
    }

    public static int getDefaultMaxRetries() {
        return DEFAULT_MAX_RETRIES;
    }

    public static float getDefaultBackoff() {
        return DEFAULT_BACKOFF;
    }

    public SessionStorage getSessionStorage() {
        return sessionStorage;
    }

    public void setSessionStorage(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    /**
     * Restore and save a old state of the sessionStorage cookieValue.
     *
     * @param savedInstanceState, the old state to restore of the sessionStorage cookieValue.
     */
    public void restoreSessionStorageState(Bundle savedInstanceState) {
        this.sessionStorage.restoreState(savedInstanceState);
    }

    /**
     * Save a new state in the sessionStorage cookieValue.
     *
     * @param outState, the new state to save in the sessionStorage cookieValue.
     */
    public void saveSessionStorageState(Bundle outState) {
        this.sessionStorage.saveState(outState);
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
            headers = headers.split("=")[1];
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
        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            return new VolleyError(new String(volleyError.networkResponse.data));
        }
        return volleyError;
    }
}
