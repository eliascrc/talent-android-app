package request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import java.io.UnsupportedEncodingException;

import common.SessionStorage;
import networking.BaseRequest;
import networking.BaseResponse;

/**
 * This class makes a request to the server to recover a html content.
 *
 * @author Renato Mainieri Sáenz.
 */
public class ContentRequest extends BaseRequest<BaseResponse<String>> {

    /**
     * This constructor creates a ContentRequest, specifying the URL, request body, listener, error listener and session storage.
     * The HTTP method is defined to be GET.
     */
    public ContentRequest(String url, String requestBody, Response.Listener<BaseResponse<String>> listener,
                                Response.ErrorListener errorListener, SessionStorage sessionStorage) {
        super(Request.Method.GET, url, requestBody, listener, errorListener, sessionStorage);
    }

    @Override
    protected Response<BaseResponse<String>> parseNetworkResponse(NetworkResponse networkResponse) {
        super.parseNetworkResponse(networkResponse);
        try {
            String html_request = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            BaseResponse<String> baseResponse = new BaseResponse<>();
            baseResponse.setResponse(html_request);
            Response<BaseResponse<String>> response = Response.success(baseResponse,
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
            return response;
        } catch (UnsupportedEncodingException e) {
            VolleyError volleyError = new ParseError(e);
            Response<BaseResponse<String>> response = Response.error(volleyError);
            return response;
        }
    }
}
