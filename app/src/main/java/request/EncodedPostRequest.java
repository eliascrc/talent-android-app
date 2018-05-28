package request;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import common.SessionStorage;
import networking.BaseRequest;
import networking.BaseResponse;

/**
 * This class makes a request to the server to log in a user.
 *
 * @author Fabián Roberto Leandro
 */

public class EncodedPostRequest extends BaseRequest<BaseResponse<String>> {
    /**
     * This constructor creates a SignInRequest, specifying the URL, request body, listener, error listener and session storage.
     * The HTTP method is defined to be POST.
     */
    public EncodedPostRequest(String url, String requestBody, Response.Listener<BaseResponse<String>> listener,
                              Response.ErrorListener errorListener, SessionStorage sessionStorage) {
        super(Request.Method.POST, url, requestBody, listener, errorListener, sessionStorage);
    }

    // BaseRequest extends JsonRequest, which returns the json content type
    // Overwrite this method to send the content type needed for our sign in implementation
    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=UTF-8";
    }

    @Override
    protected Response<BaseResponse<String>> parseNetworkResponse(NetworkResponse networkResponse) {
        super.parseNetworkResponse(networkResponse);
        // The login webservice does not return any content, simply handle the http status code

        // Get the Status code from the NetworkResponse (volley class)
        int statusCode = networkResponse.statusCode;

        // Make new BaseResponse<String> and set the status code
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setHttpStatusCode(statusCode);


        // Create new Response from our BaseResponse and return it
        Response<BaseResponse<String>> response = Response.success(baseResponse,
                HttpHeaderParser.parseCacheHeaders(networkResponse));
        return response;
    }
}
