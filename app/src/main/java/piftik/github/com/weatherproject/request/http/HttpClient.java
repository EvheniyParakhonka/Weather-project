package piftik.github.com.weatherproject.request.http;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HttpClient implements IHttpClient {
    private static final String TAG = HttpClient.class.getSimpleName();

    @Override
    public InputStream makeHttpRequst(String pUrl)  {



        URL mUrl;
        try {
            mUrl = new URL(pUrl);
        } catch (MalformedURLException exception) {
            Log.e(TAG, exception.toString());

            return null;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) mUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();


            } else {
                Log.e(TAG, "Eror respown code" + urlConnection.getResponseCode());
            }


        } catch (IOException pE) {
            Log.e(TAG, "Error whith connection " + pE);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }


        }
        return inputStream;
    }
}
