package networking;


import java.util.Map;
import common.SessionStorage;


/**
 * This class is for network responses, contains the HTTP status codes and headers
 *
 * @author Renato Mainieri Sáenz.
 */
public class BaseResponse<T> {

    private Map<String, String> httpHeaders;
    private int httpStatusCode;
    protected T response;

    private SessionStorage sessionStorage;

    private static final String COOKIE_HEADER_KEY = "Set-Cookie";
    private static final String SEMICOLON = ";";

    public BaseResponse(SessionStorage sessionStorage) {
        this.httpStatusCode = 0;
        this.sessionStorage = sessionStorage;
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(Map<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public void setCookie(){
        if (this.httpHeaders.containsKey(COOKIE_HEADER_KEY)) {
            String cookie = this.getHttpHeaderValue(COOKIE_HEADER_KEY);
            cookie = cookie.substring(0, cookie.indexOf(SEMICOLON));
            sessionStorage.setCookieValue(cookie);
        }
    }

    public SessionStorage getSessionStorage() {
        return sessionStorage;
    }

    public void setSessionStorage(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    /**
     * Return the HTTP header value of a respective HTTP header name.
     *
     * @param httpHeader, is the name number of a HTTP header.
     * @return the value of the HTTP header.
     */
    public String getHttpHeaderValue(String httpHeader) {
        String httpHeaderValue = null;
        if (this.httpHeaders.containsKey(httpHeader)) {
            httpHeaderValue = this.httpHeaders.get(httpHeader);
        }
        return httpHeaderValue;
    }
}
