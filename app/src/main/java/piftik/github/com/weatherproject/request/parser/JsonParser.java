package piftik.github.com.weatherproject.request.parser;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import piftik.github.com.weatherproject.Weather;
import piftik.github.com.weatherproject.request.http.IReadFromStream;
import piftik.github.com.weatherproject.request.http.ReadFromStream;


public class JsonParser implements IJsonParser {

    private static final String TAG = JsonParser.class.getSimpleName();

    public JsonParser() {
    }

    @Override
    public ArrayList<Weather> extractWeatherFromJson(final InputStream pInputStream) throws Exception {
        final ArrayList<Weather> forecasts = new ArrayList<>();
        final IReadFromStream iReadFromStream = new ReadFromStream();

        String jsonResponse = null;
        try {
            jsonResponse = iReadFromStream.readFromStream(pInputStream);
        } catch (final IOException pE) {
            Log.e(TAG, pE.toString());
        }
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        try {
            final JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            final JSONArray featureArray = baseJsonResponse.getJSONArray("list");
            final JSONObject placeProperitis = baseJsonResponse.getJSONObject("city");

            for (int i = 0; i < featureArray.length(); i++) {
                final JSONObject firstProperitis = featureArray.getJSONObject(i);
                final JSONObject main = firstProperitis.getJSONObject("main");
                final JSONArray weather = firstProperitis.getJSONArray("weather");
                final JSONObject mainWeather = weather.getJSONObject(0);


                final double tempInKelvin = main.getDouble("temp");
                final String dataText = firstProperitis.getString("dt_txt");
                final String weatherMain = mainWeather.getString("main");


                final String placeOfCit = placeProperitis.getString("name");
                final String country = placeProperitis.getString("country");

                forecasts.add(new Weather(dataText, weatherMain, tempInKelvin, country, placeOfCit));
            }
        } catch (final JSONException pE) {
            Log.e(TAG, "Error with parsing " + pE);
        }finally {
            if (pInputStream != null) {
                pInputStream.close();
            }
        }
        return forecasts;
    }
}
