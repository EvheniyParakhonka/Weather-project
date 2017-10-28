package piftik.github.com.weatherproject.backend;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import piftik.github.com.weatherproject.BuildConfig;
import piftik.github.com.weatherproject.Weather;

public class PostToMyBackend extends AsyncTask<ArrayList<Weather>, Void, Void> {

    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;
    private static final String TAG = PostToMyBackend.class.getSimpleName();

    private void postConnect(final List<Weather> pWeathers) {
        try {

            final URL urlAddress = new URL(BuildConfig.BASE_URL);
            final HttpURLConnection httpURLConnection = (HttpURLConnection) urlAddress.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
            outputStreamWriter.write(createPostJson(pWeathers));
            outputStreamWriter.flush();
            outputStreamWriter.close();

            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            final StringBuilder stringBuilder = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            bufferedReader.close();
            httpURLConnection.disconnect();

        } catch (final Exception e) {
            e.getMessage();
        }

    }

    private String createPostJson(final List<Weather> pWeathers) {

        final JSONObject first = new JSONObject();
        try {
            first.put("city", pWeathers.get(0).getCity());
            first.put("country", pWeathers.get(0).getCountry());
            final JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < pWeathers.size(); i++) {
                jsonArray.put(new JSONObject().put("date", pWeathers.get(i).getDate()).
                        put("weatherMain", pWeathers.get(i).getWeatherMain()).
                        put("temp", pWeathers.get(i).getTemp()));
            }
            first.put("list", jsonArray);
        } catch (final JSONException pE) {
            Log.e(TAG, "json exception");
        }
        return first.toString();
    }

    @SafeVarargs
    @Override
    protected final Void doInBackground(final ArrayList<Weather>... pArrayLists) {
        try {
            postConnect(pArrayLists[0]);
        } catch (final Exception pE) {
            Log.e(TAG, pE.toString());
        }

        return null;
    }

}
