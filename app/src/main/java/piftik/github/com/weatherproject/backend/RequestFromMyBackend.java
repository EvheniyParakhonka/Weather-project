package piftik.github.com.weatherproject.backend;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import piftik.github.com.weatherproject.BuildConfig;
import piftik.github.com.weatherproject.IForecastLoader;
import piftik.github.com.weatherproject.Weather;
import piftik.github.com.weatherproject.request.RequestFromOpenWeather;
import piftik.github.com.weatherproject.request.http.HttpClient;
import piftik.github.com.weatherproject.request.http.IHttpClient;
import piftik.github.com.weatherproject.request.parser.GsonParserFromMyBackend;
import piftik.github.com.weatherproject.request.parser.IGsonParserFromMyBackend;

public class RequestFromMyBackend extends Fragment implements IForecastLoader {

    private static final String TAG = RequestFromMyBackend.class.getSimpleName();
    private AsyncEndpoint mAsyncEndpoint;
    private final List<IForecastLOaderListener> mListeners = new ArrayList<>();

    @Override
    public void addListener(final IForecastLOaderListener pListener) {
        mListeners.add(pListener);
    }

    @Override
    public void removeListener(final IForecastLOaderListener pListener) {
        mListeners.remove(pListener);

        if (mListeners.isEmpty()) {
            if (mAsyncEndpoint != null && mAsyncEndpoint.getStatus() == AsyncTask.Status.RUNNING) {
                mAsyncEndpoint.cancel(true);
            }
        }
    }

    @Override
    public void getForecast(final String pCityId) {
        if (mAsyncEndpoint != null && mAsyncEndpoint.getStatus() == AsyncTask.Status.RUNNING) {
            return;
        }

        mAsyncEndpoint = new AsyncEndpoint();
        mAsyncEndpoint.execute(pCityId);
    }

    private class AsyncEndpoint extends AsyncTask<String, Object, ArrayList<Weather>> {

        private String createURLRequest(final String pCityId) {

            return BuildConfig.BASE_URL + pCityId;
        }

        @Override
        protected ArrayList<Weather> doInBackground(final String... pCityId) {
            final IHttpClient iHttpClient = new HttpClient();
            final IGsonParserFromMyBackend iGsonParser = new GsonParserFromMyBackend();
            final String url = createURLRequest(pCityId[0]);
//            List<Weather> weathers = new ArrayList<>();
            ArrayList<Weather> jsonResponse = null;
            try {

                final String inputStream = iHttpClient.makeHttpRequest(url);
                if (inputStream == null) {
                    final RequestFromOpenWeather requestFromOpenWeather = new RequestFromOpenWeather();
                    jsonResponse =(ArrayList<Weather>) requestFromOpenWeather.createAsync(pCityId[0]);
                } else {
                    jsonResponse = iGsonParser.extractWeatherFromJsonMyBackend(inputStream);
                }
            } catch (final Exception pE) {
                Log.e(TAG, pE.toString());
            }
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(final ArrayList<Weather> pWeathers) {
           showWeather(pWeathers);
        }

    }
    public void showWeather (final ArrayList<Weather> pWeathers){
        if (!mListeners.isEmpty() && pWeathers != null){
            for (final IForecastLOaderListener listener : mListeners){
                listener.onSuccess(pWeathers);
            }
        }
    }

}
