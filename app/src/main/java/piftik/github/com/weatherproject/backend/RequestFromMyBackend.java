package piftik.github.com.weatherproject.backend;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import piftik.github.com.weatherproject.IForecastLoader;

public class RequestFromMyBackend extends Fragment implements IForecastLoader {

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
}
