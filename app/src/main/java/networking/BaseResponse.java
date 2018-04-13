package networking;

import java.util.HashMap;

/**
 * This class is for network responses, contains the HTTP status codes and headers
 *
 * @author Renato Mainieri Sáenz.
 */
public abstract class BaseResponse<T> {

    private HashMap<String, String> httpHeaders;
    private String httpStatusCode;

    public BaseResponse() {
        this.httpHeaders = new HashMap<>();
        this.fillHttpHeaders();
    }

    /**
     * Fill the list of HTTP headers, with their names and empty values.
     */
    private void fillHttpHeaders() {
        this.httpHeaders.put("Accept", "");
        this.httpHeaders.put("Accept-Charset", "");
        this.httpHeaders.put("Accept-Encoding", "");
        this.httpHeaders.put("Accept-Language", "");
        this.httpHeaders.put("Accept-Ranges", "");
        this.httpHeaders.put("Access-Control-Allow-Credentials", "");
        this.httpHeaders.put("Access-Control-Allow-Headers", "");
        this.httpHeaders.put("Access-Control-Allow-Methods", "");
        this.httpHeaders.put("Access-Control-Allow-Origin", "");
        this.httpHeaders.put("Access-Control-Expose-Header", "");
        this.httpHeaders.put("Access-Control-Max-Age", "");
        this.httpHeaders.put("Access-Control-Request-Headers", "");
        this.httpHeaders.put("Access-Control-Request-Method", "");
        this.httpHeaders.put("Age", "");
        this.httpHeaders.put("Allow", "");
        this.httpHeaders.put("Authorization", "");
        this.httpHeaders.put("Cache-Control", "");
        this.httpHeaders.put("Connection", "");
        this.httpHeaders.put("Content-Disposition", "");
        this.httpHeaders.put("Content-Encoding", "");
        this.httpHeaders.put("Content-Language", "");
        this.httpHeaders.put("Content-Length", "");
        this.httpHeaders.put("Content-Location", "");
        this.httpHeaders.put("Content-Range", "");
        this.httpHeaders.put("Content-Security-Policy", "");
        this.httpHeaders.put("Content-Security-Policy-Report-Only", "");
        this.httpHeaders.put("Content-Type", "");
        this.httpHeaders.put("Cookie", "");
        this.httpHeaders.put("Cookie2", "");
        this.httpHeaders.put("DNT", "");
        this.httpHeaders.put("Date", "");
        this.httpHeaders.put("ETag", "");
        this.httpHeaders.put("Expect", "");
        this.httpHeaders.put("Expect-CT", "");
        this.httpHeaders.put("Expires", "");
        this.httpHeaders.put("Forwarded", "");
        this.httpHeaders.put("From", "");
        this.httpHeaders.put("Host", "");
        this.httpHeaders.put("If-Match", "");
        this.httpHeaders.put("If-Modified-Since", "");
        this.httpHeaders.put("If-None-Match", "");
        this.httpHeaders.put("If-Range", "");
        this.httpHeaders.put("If-Unmodified-Since", "");
        this.httpHeaders.put("Keep-Alive", "");
        this.httpHeaders.put("Large-Allocation", "");
        this.httpHeaders.put("Last-Modified", "");
        this.httpHeaders.put("Location", "");
        this.httpHeaders.put("Origin", "");
        this.httpHeaders.put("Pragma", "");
        this.httpHeaders.put("Proxy-Authenticate", "");
        this.httpHeaders.put("Proxy-Authorization", "");
        this.httpHeaders.put("Public-Key-Pins", "");
        this.httpHeaders.put("Public-Key-Pins-Report-Only", "");
        this.httpHeaders.put("Range", "");
        this.httpHeaders.put("Referer", "");
        this.httpHeaders.put("Referrer-Policy", "");
        this.httpHeaders.put("Retry-After", "");
        this.httpHeaders.put("Server", "");
        this.httpHeaders.put("Set-Cookie", "");
        this.httpHeaders.put("Set-Cookie2", "");
        this.httpHeaders.put("SourceMap", "");
        this.httpHeaders.put("Strict-Transport-Security", "");
        this.httpHeaders.put("TE", "");
        this.httpHeaders.put("Timing-Allow-Origin", "");
        this.httpHeaders.put("TK", "");
        this.httpHeaders.put("Trailer", "");
        this.httpHeaders.put("Transfer-Encoding", "");
        this.httpHeaders.put("Upgrade-Insecure-Requests", "");
        this.httpHeaders.put("User-Agent", "");
        this.httpHeaders.put("Vary", "");
        this.httpHeaders.put("Via", "");
        this.httpHeaders.put("WWW-Authenticate", "");
        this.httpHeaders.put("Warning", "");
        this.httpHeaders.put("X-Content-Type-Options", "");
        this.httpHeaders.put("X-DNS-Prefetch-Control", "");
        this.httpHeaders.put("X-Forwarded-For", "");
        this.httpHeaders.put("X-Forwarded-Host", "");
        this.httpHeaders.put("X-Forwarded-Proto", "");
        this.httpHeaders.put("X-Frame-Option", "");
        this.httpHeaders.put("X-XSS-Protection", "");
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(String httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
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

    /**
     * Assign the value to an existing HTTP header.
     *
     * @param httpHeader, the HTTP header to which the value will be assigned.
     * @param value,      the value to be assigned.
     */
    private void setHttpHeaderValue(String httpHeader, String value) {
        if (this.httpHeaders.containsKey(httpHeader)) {
            this.httpHeaders.put(httpHeader, value);
        }
    }
}
