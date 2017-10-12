package piftik.github.com.weatherproject.request;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import piftik.github.com.weatherproject.IForecastLoader;
import piftik.github.com.weatherproject.Weather;
import piftik.github.com.weatherproject.request.http.HttpClient;
import piftik.github.com.weatherproject.request.http.IHttpClient;
import piftik.github.com.weatherproject.request.parser.IJsonParser;
import piftik.github.com.weatherproject.request.parser.JsonParser;

public class RequestFromOpenWeather extends Fragment implements IForecastLoader {
    private static final String TAG = RequestFromOpenWeather.class.getSimpleName();
    private List<IForecastLOaderListener> mListeners = new ArrayList<>();
    private WeatherAsyncTask mWeatherAsyncTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void addListener(IForecastLOaderListener pListener) {
        mListeners.add(pListener);
    }

    @Override
    public void removeListener(IForecastLOaderListener pListener) {
        mListeners.remove(pListener);

        if (mListeners.isEmpty()) {
            if (mWeatherAsyncTask != null && mWeatherAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
                mWeatherAsyncTask.cancel(true);
            }
        }
    }

    public String createURLRequest(String pCityId) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.openweathermap.org")
                .appendPath("data")
                .appendPath("2.5")
                .appendPath("forecast")
                .appendQueryParameter("q", pCityId)
                .appendQueryParameter("APPID", "c47e6bde2548624a496fb7fc2b7efce2");
        return builder.build().toString();
    }

    @Override
    public void getForecast(String pCityId) {
        if (mWeatherAsyncTask != null && mWeatherAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            return;
        }

        mWeatherAsyncTask = new WeatherAsyncTask();
        mWeatherAsyncTask.execute(pCityId);
    }


    private class WeatherAsyncTask extends AsyncTask<String, Void, ArrayList<Weather>> {

        @Override
        protected ArrayList<Weather> doInBackground(String... urls) {

            IHttpClient iHttpClient = new HttpClient();
            IJsonParser iJsonParse = new JsonParser();
            String url = createURLRequest(urls[0]);
            ArrayList<Weather> jsonRespons = null;

            try {
                InputStream inputStream = iHttpClient.makeHttpRequst(url);
                jsonRespons = iJsonParse.extractWeatherFromJson(inputStream);
            } catch (IOException pE) {
                Log.e(TAG, "Erorr in BackGroundTask");
            } catch (Exception pE) {
                pE.printStackTrace();
            }

            return jsonRespons;
        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(ArrayList<Weather> pWeathers) {

            if (mListeners != null && !mListeners.isEmpty() && pWeathers != null) {
                for (IForecastLOaderListener listener : mListeners) {
                    listener.onSuccess(pWeathers);
                }
            }
        }
    }
}
