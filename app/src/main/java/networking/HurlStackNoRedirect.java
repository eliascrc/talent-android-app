package networking;

import com.android.volley.toolbox.HurlStack;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HurlStackNoRedirect extends HurlStack {

    @Override
    protected  HttpURLConnection createConnection (URL url)throws IOException {
        HttpURLConnection connection = super.createConnection(url);
        connection.setInstanceFollowRedirects(false);

        return connection;
    }
}
