package piftik.github.com.weatherproject.model;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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

//    @Override
//    public void getForecastForDay(long pCityId, int pDays) {
//
//    }

    private class WeatherAsyncTask extends AsyncTask<String, Void, ArrayList<Weather>> {

        @Override
        protected ArrayList<Weather> doInBackground(String... urls) {
            //create URL object
            URL url = createUrl(createURLRequest(urls[0]));

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonRespons = "";

            try {
                jsonRespons = makeHttpRequst(url);
            } catch (IOException pE) {
                Log.e(TAG, "Erorr in BackGroundTask");
            }

            return extractWeatherFromJson(jsonRespons);
        }

        private URL createUrl(String pURL) {
            {
                URL url;
                try {
                    url = new URL(pURL);
                } catch (MalformedURLException exception) {
                    if (mListeners != null && !mListeners.isEmpty()) {
                        for (IForecastLOaderListener listener : mListeners) {
                            listener.onError(1);
                        }
                    }

                    return null;
                }
                return url;
            }
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

    private ArrayList<Weather> extractWeatherFromJson(String pJsonRespons) {

        ArrayList<Weather> forecasts = new ArrayList<>();

        if (TextUtils.isEmpty(pJsonRespons)) {
            return null;
        }
        try {
            JSONObject baseJsonResponse = new JSONObject(pJsonRespons);
            JSONArray featureArray = baseJsonResponse.getJSONArray("list");
            JSONObject placeProperitis = baseJsonResponse.getJSONObject("city");

            for (int i = 0; featureArray.length() > 0 && i <= 10; i++) {
                JSONObject firstProperitis = featureArray.getJSONObject(i);
                JSONObject main = firstProperitis.getJSONObject("main");
                JSONArray weather = firstProperitis.getJSONArray("weather");
                JSONObject mainWeather = weather.getJSONObject(0);


                int tempInKelvin = main.getInt("temp");
                String dataText = firstProperitis.getString("dt_txt");
                String weatherMain = mainWeather.getString("main");


                String placeOfCit = placeProperitis.getString("name");
                String country = placeProperitis.getString("country");

                forecasts.add(new Weather(dataText, weatherMain, tempInKelvin, country, placeOfCit));
            }
        } catch (JSONException pE) {
            for (IForecastLOaderListener listener : mListeners) {
                listener.onError(2);
            }

        }
        return forecasts;
    }

    private String makeHttpRequst(URL pUrl) throws IOException {
        String jsonResponse = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) pUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e(TAG, "Eror respown code" + urlConnection.getResponseCode());
            }


        } catch (IOException pE) {
            Log.e(TAG, "Error whith connection");

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }

        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
