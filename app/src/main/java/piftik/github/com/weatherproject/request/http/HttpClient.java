package piftik.github.com.weatherproject.request.http;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpClient implements IHttpClient {

    private static final String TAG = HttpClient.class.getSimpleName();
    private static final int READ_TIMEOUT = 40000;
    private static final int CONNECT_TIMEOUT = 15000;
    private static final int RESPONSE_CODE_SUCCESS = 200;
    private final IReadFromStream mIReadFromStream = new ReadFromStream();
    private String mResponse;

    @Override
    public String makeHttpRequest(final String pUrl)  {


        final URL mUrl;
        try {
            mUrl = new URL(pUrl);
        } catch (final MalformedURLException exception) {
            Log.e(TAG, exception.toString());

            return null;
        }

        HttpURLConnection urlConnection = null;
        final InputStream inputStream;
        try {
            urlConnection = (HttpURLConnection) mUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(READ_TIMEOUT /* milliseconds */);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == RESPONSE_CODE_SUCCESS) {
                inputStream = urlConnection.getInputStream();
                mResponse = mIReadFromStream.readFromStream(inputStream);

            } else {
                Log.e(TAG, "Error response code" + urlConnection.getResponseCode());
            }


        } catch (final IOException pE) {
            Log.e(TAG, "Error with connection " + pE);

        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return mResponse;
    }
}
