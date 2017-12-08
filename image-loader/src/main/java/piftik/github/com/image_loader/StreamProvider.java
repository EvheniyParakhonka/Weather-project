package piftik.github.com.image_loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class StreamProvider implements  IStreamProvider<String> {
    @Override
    public InputStream get(final String pPath) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) (new URL(pPath)).openConnection();
        return connection.getInputStream();
    }
}
