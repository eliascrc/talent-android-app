package networking;

import java.util.HashMap;

/**
 * This class is for network responses, contains the HTTP status codes and headers
 *
 * @author Renato Mainieri Sáenz.
 */
public class BaseResponse<T> {

    protected HashMap<String, String> httpHeaders;
    protected String httpStatusCode;
    protected T response;

    public BaseResponse() {
        this.httpHeaders = new HashMap<>();
        this.httpStatusCode = "";
    }

    public HashMap<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HashMap<String, String> httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    /**
     * Return the HTTP header value of a respective HTTP header name.
     *
     * @param httpHeader, is the name number of a HTTP header.
     * @return the value of the HTTP header.
     */
    private String getHttpHeaderValue(String httpHeader) {
        String httpHeaderValue = null;
        if (this.httpHeaders.containsKey(httpHeader)) {
            httpHeaderValue = this.httpHeaders.get(httpHeader);
        }
        return httpHeaderValue;
    }
}
