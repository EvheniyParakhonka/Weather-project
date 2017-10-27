package piftik.github.com.weatherproject.request;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import piftik.github.com.weatherproject.Weather;
import piftik.github.com.weatherproject.backend.PostToMyBackend;
import piftik.github.com.weatherproject.request.http.HttpClient;
import piftik.github.com.weatherproject.request.http.IHttpClient;
import piftik.github.com.weatherproject.request.parser.IJsonParser;
import piftik.github.com.weatherproject.request.parser.JsonParser;

public class RequestFromOpenWeather extends Fragment {

    private static final String TAG = RequestFromOpenWeather.class.getSimpleName();

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void createAsync(final String pCityID) {
        final WeatherAsyncTask weatherAsyncTask = new WeatherAsyncTask();
        weatherAsyncTask.execute(pCityID);
    }

    public String createURLRequestForOpenWeather(final String pCityId) {
        final Uri.Builder builder = new Uri.Builder();
//        http://api.openweathermap.org/data/2.5/forecast?q=Hrodno,BY&APPID=c47e6bde2548624a496fb7fc2b7efce2
        builder.scheme("https")
                .authority("api.openweathermap.org")
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("forecast")
                .appendQueryParameter("q", pCityId)
                .appendQueryParameter("APPID", "c47e6bde2548624a496fb7fc2b7efce2");
        return builder.build().toString();
    }

    private class WeatherAsyncTask extends AsyncTask<String, Void, ArrayList<Weather>> {

        @Override
        protected ArrayList<Weather> doInBackground(final String... urls) {

            final IHttpClient iHttpClient = new HttpClient();
            final IJsonParser iJsonParse = new JsonParser();
            final String url = createURLRequestForOpenWeather(urls[0]);
            ArrayList<Weather> jsonResponse = null;

            try {
                final String inputStream = iHttpClient.makeHttpRequest(url);
                jsonResponse = iJsonParse.extractWeatherFromJson(inputStream);
            } catch (final IOException pE) {
                Log.e(TAG, "Error in BackGroundTask");
            } catch (final Exception pE) {
                Log.e(TAG, pE.toString());
            }

            return jsonResponse;
        }

        @Override
        protected void onPostExecute(final ArrayList<Weather> pWeathers) {
            final PostToMyBackend postToMyBackend = new PostToMyBackend();
            postToMyBackend.execute(pWeathers);
        }
    }
}
