package piftik.github.com.weatherproject.request.http;

import java.io.InputStream;

public interface IHttpClient {
    InputStream makeHttpRequest(String pUrl);

}
