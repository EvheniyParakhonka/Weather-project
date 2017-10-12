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
    public ArrayList<Weather> extractWeatherFromJson(InputStream pInputStream) throws Exception {
        ArrayList<Weather> forecasts = new ArrayList<>();
        IReadFromStream iReadFromStream = new ReadFromStream();

        String jsonResponse = null;
        try {
            jsonResponse = iReadFromStream.readFromStream(pInputStream);
        } catch (IOException pE) {
            Log.e(TAG, pE.toString());
        }
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray featureArray = baseJsonResponse.getJSONArray("list");
            JSONObject placeProperitis = baseJsonResponse.getJSONObject("city");

            for (int i = 0; i < featureArray.length(); i++) {
                JSONObject firstProperitis = featureArray.getJSONObject(i);
                JSONObject main = firstProperitis.getJSONObject("main");
                JSONArray weather = firstProperitis.getJSONArray("weather");
                JSONObject mainWeather = weather.getJSONObject(0);


                double tempInKelvin = main.getDouble("temp");
                String dataText = firstProperitis.getString("dt_txt");
                String weatherMain = mainWeather.getString("main");


                String placeOfCit = placeProperitis.getString("name");
                String country = placeProperitis.getString("country");

                forecasts.add(new Weather(dataText, weatherMain, tempInKelvin, country, placeOfCit));
            }
        } catch (JSONException pE) {
            Log.e(TAG, "Error whith parsing " + pE);
        }
        return forecasts;
    }
}
