package piftik.github.com.weatherproject.request.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import piftik.github.com.weatherproject.Weather;

public class GsonParserFromMyBackend implements IGsonParserFromMyBackend {

    private static final String TAG = GsonParserFromMyBackend.class.getSimpleName();

    public ArrayList<Weather> extractWeatherFromJsonMyBackend(final String jsonResponse) throws Exception {

        final ArrayList<Weather> forecasts = new ArrayList<>();
//        final IReadFromStream iReadFromStream = new ReadFromStream();

////        String jsonResponse = null;
//        try {
////            jsonResponse = iReadFromStream.readFromStream(pInputStream);
//            if (TextUtils.isEmpty(jsonResponse)) {
//                return null;
//            }
//        } catch (final IOException pE) {
//            Log.e(TAG, pE.toString());
//        }

        try {
            final JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            final JSONArray featureArray = baseJsonResponse.getJSONArray("list");
            final String city = baseJsonResponse.getString("city");
            final String country = baseJsonResponse.getString("country");
            for (int i = 0; i < featureArray.length(); i++) {
                final JSONObject firstProperitis = featureArray.getJSONObject(i);
                final String date = firstProperitis.getString("date");
                final double tempInKelvin = firstProperitis.getDouble("temp");
                final String weatherMain = firstProperitis.getString("weatherMain");
                forecasts.add(new Weather(date, weatherMain, tempInKelvin, country, city));
            }
        } catch (final JSONException pE) {
            Log.e(TAG, "Error with parsing " + pE);
        }
        return forecasts;
    }

}
