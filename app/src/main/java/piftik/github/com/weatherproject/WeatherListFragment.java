package piftik.github.com.weatherproject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import piftik.github.com.weatherproject.recycler.WeatherAdapter;

import static android.content.ContentValues.TAG;

public class WeatherListFragment extends VisibleFragment {

    private RecyclerView mWeatherRecyclerView;
    private IForecastLoader mForecastLoader;
    private MyIForecastLOaderListener mListener;
    private Handler mUiHandler;
    private View mMProgress;
    private String mCityID;

//    Code style
    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mForecastLoader = IForecastLoader.Impl.getInstance();
        mListener = new MyIForecastLOaderListener();

        mUiHandler = new Handler(Looper.getMainLooper());
        final Bundle bundle = getArguments();
        if (bundle != null) {
            mCityID = bundle.getString("CITY_ID");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mForecastLoader.addListener(mListener);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_weather_list, container, false);

        mWeatherRecyclerView = (RecyclerView) view.findViewById(R.id.weather_recycler_view);
        mWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMProgress = view.findViewById(R.id.progress);
        mMProgress.setVisibility(View.VISIBLE);
        getWeatherAsync(mCityID);

        return view;
    }

    private class MyIForecastLOaderListener implements IForecastLoader.IForecastLOaderListener {

        @Override
        public void onSuccess(final ArrayList<Weather> pWeathers) {

            mMProgress.setVisibility(View.GONE);
            if (pWeathers != null && !pWeathers.isEmpty()) {
                for (final Weather weather : pWeathers) {
                    Log.d(TAG, "onSuccess: " + weather.getWeatherMain());
                    final WeatherAdapter weatherAdapter = new WeatherAdapter(WeatherListFragment.this, pWeathers);
                    mWeatherRecyclerView.setAdapter(weatherAdapter);

                }
            }
        }

        @Override
        public void onError(final int errorCode) {
            mUiHandler.post(new Runnable() {

                @Override
                public void run() {
                    showEmptyView(errorCode);
                }
            });
        }

    }

    private void getWeatherAsync(final String pStingId) {
        mForecastLoader.getForecast(pStingId);
    }

    @Override
    public void onStop() {
        super.onStop();

        mForecastLoader.removeListener(mListener);
    }

    private void showEmptyView(final int errorCode) {
        mMProgress.setVisibility(View.GONE);
        Toast.makeText(getContext(), "empty " + errorCode, Toast.LENGTH_LONG).show();
    }
}
