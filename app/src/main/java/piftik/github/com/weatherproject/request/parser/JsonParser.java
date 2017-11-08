package piftik.github.com.weatherproject.request.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import piftik.github.com.weatherproject.Weather;
import piftik.github.com.weatherproject.utils.Constants;
import piftik.github.com.weatherproject.utils.Converter;

public class JsonParser implements IJsonParser {

    private static final String TAG = JsonParser.class.getSimpleName();

    public JsonParser() {
    }

    @Override
    public ArrayList<Weather> extractWeatherFromJson(final String pJsonRequest, final String pCityId) {
        final ArrayList<Weather> forecasts = new ArrayList<>();

//        String jsonResponse = null;
//
//        if (TextUtils.isEmpty(jsonResponse)) {
//            return null;
//        }
        try {
            final JSONObject baseJsonResponse = new JSONObject(pJsonRequest);
            final JSONArray featureArray = baseJsonResponse.getJSONArray("list");
            final JSONObject placeProperitis = baseJsonResponse.getJSONObject(Constants.CITY_TO_PARSING);

            for (int i = 0; i < featureArray.length(); i++) {
                final JSONObject firstProperitis = featureArray.getJSONObject(i);
                final JSONObject temp = firstProperitis.getJSONObject("temp");
                final JSONArray weather = firstProperitis.getJSONArray("weather");
                final JSONObject mainWeather = weather.getJSONObject(0);

                final double tempToDayMax = temp.getDouble("max");
                final double tempToDayMin = temp.getDouble("min");
                final long data = firstProperitis.getLong("dt");
                final String weatherMain = mainWeather.getString("main");


                final String placeOfCit = placeProperitis.getString("name");
                final String country = placeProperitis.getString(Constants.COUNTRY_TO_PARSING);

                forecasts.add(new Weather(Converter.convertUnixTimeToDays(data), weatherMain, Converter.convertTemperatureToCelsius(tempToDayMin),
                        Converter.convertTemperatureToCelsius(tempToDayMax), country, pCityId));
            }
        } catch (final JSONException pException) {
            Log.e(TAG, "Error with parsing " + pException);
        }
        return forecasts;
    }
}
