package request;


import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Date;

import common.SessionStorage;
import model.User;
import networking.BaseRequest;
import networking.BaseResponse;

/**
 * This class makes a request to the server to know if the current mobile app user is authenticated.
 *
 * @author Elias Calderon
 */
public class AuthenticatedRequest extends BaseRequest<BaseResponse<User>> {

    /**
     * This constructor creates a AuthenticatedRequest, specifying the URL, request body, listener, error listener and session storage.
     * The HTTP method is defined to be GET.
     */
    public AuthenticatedRequest(String url, String requestBody, Response.Listener<BaseResponse<User>> listener,
                                Response.ErrorListener errorListener, SessionStorage sessionStorage) {
        super(Method.GET, url, requestBody, listener, errorListener, sessionStorage);
    }

    /**
     * Parses the user that returns the web service via a json.
     */
    @Override
    public Response<BaseResponse<User>> parseNetworkResponse(NetworkResponse networkResponse) {
        super.parseNetworkResponse(networkResponse);
        try {
            // Authenticated web service always returns the information of an authenticated user
            String json = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));
            Type baseResponseType = new TypeToken<User>() {}.getType();

            GsonBuilder gsonBuilder = new GsonBuilder();

            // For millisecond Timestamps
            gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });

            User user = gsonBuilder.create().fromJson(json, baseResponseType);

            // Get the Status code from the NetworkResponse (volley class)
            int statusCode = networkResponse.statusCode;

            BaseResponse<User> baseResponse = new BaseResponse<>();
            baseResponse.setHttpStatusCode(statusCode);
            baseResponse.setHttpHeaders(networkResponse.headers);
            baseResponse.setResponse(user);

            Response<BaseResponse<User>> response = Response.success(baseResponse,
                    HttpHeaderParser.parseCacheHeaders(networkResponse));
            return response;

        } catch (UnsupportedEncodingException e) {
            VolleyError volleyError = new ParseError(e);
            Response<BaseResponse<User>> response = Response.error(volleyError);
            return response;
        }
    }
}