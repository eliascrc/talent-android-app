package request;


import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

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
    public Response<BaseResponse<Object>> parseNetworkResponse(NetworkResponse networkResponse) {
        super.parseNetworkResponse(networkResponse);
        try {
            String json = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            Type baseResponseType = new TypeToken<BaseResponse<Object>>() {}.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();

            // Get the Status code from the NetworkResponse (volley class)
            int statusCode = networkResponse.statusCode;

            BaseResponse<Object> baseResponse = new BaseResponse<>();
            baseResponse.setHttpStatusCode(statusCode);
            baseResponse.setHttpHeaders(networkResponse.headers);
            baseResponse.setResponse(json);
            Response<BaseResponse<Object>> response = Response.success(baseResponse,
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
            return response;

        } catch (UnsupportedEncodingException e) {
            VolleyError volleyError = new ParseError(e);
            Response<BaseResponse<Object>> response = Response.error(volleyError);
            return response;
        }
    }
}