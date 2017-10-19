package piftik.github.com.weatherproject.backend;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.util.ArrayList;

import piftik.github.com.weatherproject.request.RequestFromOpenWeather;
import piftik.github.com.weatherproject.request.http.HttpClient;
import piftik.github.com.weatherproject.request.http.IHttpClient;



public class AsyncEndpoint extends AsyncTask<String, Object, ArrayList<piftik.github.com.weatherproject.Weather>> {
    private static final String TAG = AsyncEndpoint.class.getSimpleName();
    public String createURLRequest(final String pCityId) {
        final Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("weatherproject-183212.appspot.com")
                .appendPath("_ah")
                .appendPath("api")
                .appendPath("weatherApi")
                .appendPath("v1")
                .appendPath("weather")
                .appendPath(pCityId);
        return builder.build().toString();
    }

    @Override
    protected ArrayList<piftik.github.com.weatherproject.Weather> doInBackground(final String... pCityId) {
        final IHttpClient iHttpClient = new HttpClient();
        IGsonParserFromMyBackend iGsonParser = new GsonParserFromMyBackend();
        final String url = createURLRequest(pCityId[0]);
        ArrayList<piftik.github.com.weatherproject.Weather> jsonResponse = null;
        try {

            final InputStream inputStream = iHttpClient.makeHttpRequest(url);
            if (inputStream == null) {
                final RequestFromOpenWeather requestFromOpenWeather = new RequestFromOpenWeather();
                requestFromOpenWeather.createAsync(pCityId[0]);
            }else {
                jsonResponse = iGsonParser.extractWeatherFromJsonMyBackend(inputStream);
            }
        } catch (final Exception pE) {
            Log.e(TAG, pE.toString());
        }
        return jsonResponse;
    }
}

