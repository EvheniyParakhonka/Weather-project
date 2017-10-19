package piftik.github.com.weatherproject.backend;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import piftik.github.com.weatherproject.Weather;

public class PostToMyBackend extends AsyncTask<ArrayList<Weather>, Void, ArrayList> {

    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;
    private static final String TAG = PostToMyBackend.class.getSimpleName();

    public String createURLRequest() {
        final Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("weatherproject-183212.appspot.com")
                .appendPath("_ah")
                .appendPath("api")
                .appendPath("weatherApi")
                .appendPath("v1")
                .appendPath("weather");

        return builder.build().toString();
    }

    public void postConnect( ArrayList<Weather> pWeathers) {
        try {

            final URL urlAddress = new URL(createURLRequest());
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

    public String createPostJson(List<Weather> pWeathers) {

        JSONObject first = new JSONObject();
        try {
            first.put("city", pWeathers.get(0).getCity());
            first.put("country", pWeathers.get(0).getCountry());
            JSONArray jsonArray = new JSONArray();

            for (int i = 0; i < pWeathers.size(); i++) {
                jsonArray.put(new JSONObject().put("date", pWeathers.get(i).getDate()).
                        put("weatherMain", pWeathers.get(i).getWeatherMain()).
                        put("temp", pWeathers.get(i).getTemp()));
            }
            first.put("list", jsonArray);
        } catch (JSONException pE) {
            Log.e(TAG, "json exception");
        }
        return first.toString();
    }

    @SafeVarargs
    @Override
    protected final ArrayList<Weather> doInBackground(ArrayList<Weather>... pArrayLists) {
        try {
            postConnect( pArrayLists[0]);
        } catch (final Exception pE) {
            Log.e(TAG, pE.toString());
        }
        return pArrayLists[0];
    }

    @Override
    protected void onPostExecute(ArrayList pArrayList) {

    }
}
