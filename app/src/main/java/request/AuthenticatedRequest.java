package request;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import common.SessionStorage;
import networking.BaseRequest;
import networking.BaseResponse;

/**
 * This class makes a request to the server to know if the current mobile app user is authenticated.
 *
 * @author Renato Mainieri Sáenz.
 */
public class AuthenticatedRequest extends BaseRequest<BaseResponse<Object>> {

    /**
     * This constructor creates a AuthenticatedRequest, specifying the URL, request body, listener, error listener and session storage.
     * The HTTP method is defined to be GET.
     */
    public AuthenticatedRequest(String url, String requestBody, Response.Listener<BaseResponse<Object>> listener,
                                Response.ErrorListener errorListener, SessionStorage sessionStorage) {
        super(Method.GET, url, requestBody, listener, errorListener, sessionStorage);
    }

    @Override
    protected Response<BaseResponse<Object>> parseNetworkResponse(NetworkResponse networkResponse) {
        super.parseNetworkResponse(networkResponse);
        try {
            String jsonHeaders = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            Gson gson = new Gson();
            HashMap hashMap = gson.fromJson(jsonHeaders, HashMap.class);
            BaseResponse<Object> baseResponse = new BaseResponse<Object>() {
                @Override
                public void setHttpHeaders(HashMap<String, String> httpHeaders) {
                    super.setHttpHeaders(httpHeaders);
                }
            };
            baseResponse.setHttpHeaders(hashMap);
            Response<BaseResponse<Object>> response = Response.success(baseResponse,
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
            return response;
        } catch (UnsupportedEncodingException e) {
            VolleyError volleyError = new VolleyError(e);
            Response<BaseResponse<Object>> response = Response.error(volleyError);
            return response;
        }
    }
}