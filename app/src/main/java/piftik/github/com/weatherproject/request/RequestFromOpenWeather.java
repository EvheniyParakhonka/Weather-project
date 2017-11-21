package piftik.github.com.weatherproject.request;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import piftik.github.com.weatherproject.models.Weather;
import piftik.github.com.weatherproject.backend.PostToMyBackend;
import piftik.github.com.weatherproject.request.http.HttpClient;
import piftik.github.com.weatherproject.request.http.IHttpClient;
import piftik.github.com.weatherproject.request.parser.IJsonParser;
import piftik.github.com.weatherproject.request.parser.JsonParser;

public class RequestFromOpenWeather extends Fragment {

    private static final String TAG = RequestFromOpenWeather.class.getSimpleName();

    public List<Weather> getWeatherFromOpenWeather(final String pCityID) {
        final IHttpClient iHttpClient = new HttpClient();
        final IJsonParser iJsonParse = new JsonParser();
        final String url = createURLRequestForOpenWeather(pCityID);
        ArrayList<Weather> jsonResponse = null;

        try {
            final String inputStream = iHttpClient.makeHttpRequest(url);
            jsonResponse = iJsonParse.extractWeatherFromJson(inputStream, pCityID);
        } catch (final Exception pException) {
            Log.e(TAG, pException.toString());
        }
        final PostToMyBackend postToMyBackend = new PostToMyBackend();
        postToMyBackend.execute(jsonResponse);

        return jsonResponse;
    }

    private String createURLRequestForOpenWeather(final String pCityId) {
        final Uri.Builder builder = new Uri.Builder();
//        http://api.openweathermap.org/data/2.5/forecast?q=Hrodno,BY&APPID=c47e6bde2548624a496fb7fc2b7efce2
        builder.scheme("https")
                .authority("api.openweathermap.org")
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("forecast")
                .appendPath("daily")
                .appendQueryParameter("q", pCityId)
                .appendQueryParameter("APPID", "c47e6bde2548624a496fb7fc2b7efce2");
        return builder.build().toString();
    }
}
