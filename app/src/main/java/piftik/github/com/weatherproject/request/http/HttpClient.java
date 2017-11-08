package piftik.github.com.weatherproject.request.http;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import piftik.github.com.weatherproject.utils.Constants;

public class HttpClient implements IHttpClient {

    private static final String TAG = HttpClient.class.getSimpleName();
    private final IReadFromStream mIReadFromStream = new ReadFromStream();
    private String mResponse;

    @Override
    public String makeHttpRequest(final String pUrl)  {


        final URL url;
        try {
            url = new URL(pUrl);
        } catch (final MalformedURLException pException) {
            Log.e(TAG, pException.toString());

            return null;
        }

        HttpURLConnection urlConnection = null;
        final InputStream inputStream;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(Constants.READ_TIMEOUT /* milliseconds */);
            urlConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == Constants.RESPONSE_CODE_SUCCESS) {
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
