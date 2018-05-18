package common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * This class helps encode the parameters of a POST request in application/x-www-form-urlencoded format
 *
 * @author Fabián Roberto Leandro.
 */

public final class ParameterEncoder {

    private ParameterEncoder(){}

    // Receives a hashmap and encodes its key,value pairs to be sent in an
    // http request in application/x-www-form-urlencoded format
    public static String encodeHashmap(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first) {
                first = false;
            } else {
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
