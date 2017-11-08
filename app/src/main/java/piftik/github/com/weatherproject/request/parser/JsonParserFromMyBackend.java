package piftik.github.com.weatherproject.request.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import piftik.github.com.weatherproject.Weather;
import piftik.github.com.weatherproject.utils.Constants;
import piftik.github.com.weatherproject.utils.Converter;

public class JsonParserFromMyBackend implements IJsonParserFromMyBackend {

    private static final String TAG = JsonParserFromMyBackend.class.getSimpleName();

    public ArrayList<Weather> extractWeatherFromJsonMyBackend(final String jsonResponse) {

        final ArrayList<Weather> forecasts = new ArrayList<>();

        try {
            final JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            final JSONArray featureArray = baseJsonResponse.getJSONArray("list");
            final String city = baseJsonResponse.getString(Constants.CITY_TO_PARSING);
            final String country = baseJsonResponse.getString(Constants.COUNTRY_TO_PARSING);

            for (int i = 0; i < featureArray.length(); i++) {
                final JSONObject firstProperitis = featureArray.getJSONObject(i);
                final String date = firstProperitis.getString("date");
                final double tempMin = firstProperitis.getDouble("tempMin");
                final double tempMax = firstProperitis.getDouble("tempMax");
                final String weatherMain = firstProperitis.getString("weatherMain");
                forecasts.add(new Weather(date, weatherMain, tempMin, tempMax,
                        country, city));
            }
        } catch (final JSONException pE) {
            Log.e(TAG, "Error with parsing " + pE);
        }
        return forecasts;
    }

}
