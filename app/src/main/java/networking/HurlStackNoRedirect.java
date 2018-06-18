package networking;

import com.android.volley.toolbox.HurlStack;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class is used to created HttpURLConnection that will not automatically follow redirects so
 * that we can handle them with a different code. It extends HurlStack.
 *
 * @author Otto Mena Kikut.
 *
 */
public class HurlStackNoRedirect extends HurlStack {

    @Override
    protected  HttpURLConnection createConnection (URL url)throws IOException {
        HttpURLConnection connection = super.createConnection(url);
        connection.setInstanceFollowRedirects(false);

        return connection;
    }
}
