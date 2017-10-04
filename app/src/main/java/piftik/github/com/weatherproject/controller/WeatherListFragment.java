package piftik.github.com.weatherproject.controller;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import piftik.github.com.weatherproject.R;
import piftik.github.com.weatherproject.utils.TemperatureConverter;
import piftik.github.com.weatherproject.model.IForecastLoader;
import piftik.github.com.weatherproject.model.Weather;

public class WeatherListFragment extends Fragment {
    private RecyclerView mWeatherRecyclerView;
    private IForecastLoader mForecastLoader;
    private MyIForecastLOaderListener mListener;
    private Handler mUiHandler;
    private View mMProgress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mForecastLoader = IForecastLoader.Impl.getInstance();
        mListener = new MyIForecastLOaderListener();
        mForecastLoader.addListener(mListener);
        mUiHandler = new Handler(Looper.getMainLooper());


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_list, container, false);

        mWeatherRecyclerView = (RecyclerView) view.findViewById(R.id.weather_recycler_view);
        mWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMProgress = view.findViewById(R.id.progress);
        mMProgress.setVisibility(View.VISIBLE);
        getWeatherAsync("Grodno");
        return view;
    }


    private class WeatherHolder extends RecyclerView.ViewHolder {
        private TextView mDateView;
        private TextView mTemperature;
        private TextView mPlace;
        private Weather mWeather;

        WeatherHolder(View pView) {
            super(pView);
            mDateView = (TextView) pView.findViewById(R.id.date_time_view);
            mTemperature = (TextView) pView.findViewById(R.id.temperature_view);
            mPlace = (TextView) pView.findViewById(R.id.city_country_view);
        }

        void bindWeather(Weather pWeather) {
            mWeather = pWeather;
            mDateView.setText(mWeather.getDate());
            String temp = String.valueOf(TemperatureConverter.convertTemperatureToCelsius(mWeather.getTemp()));
            mTemperature.setText(temp + " \u00B0" + "C");
            mPlace.setText(mWeather.getCity() + ", " + mWeather.getCountry());
        }
    }

    private class WeatherAdapter extends RecyclerView.Adapter<WeatherHolder> {
        private List<Weather> mWeathers;

        @Override
        public WeatherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.weather_fragment, parent, false);
            return new WeatherHolder(view);
        }

        @Override
        public void onBindViewHolder(WeatherHolder holder, int position) {
            Weather weather = mWeathers.get(position);
            holder.bindWeather(weather);
        }

        @Override
        public int getItemCount() {
            return mWeathers.size();
        }

        WeatherAdapter(List<Weather> pWeathers) {
            mWeathers = pWeathers;
        }
    }

    private class MyIForecastLOaderListener implements IForecastLoader.IForecastLOaderListener {


        @Override
        public void onSuccess(ArrayList<Weather> pWeathers) {

            mMProgress.setVisibility(View.GONE);
            if (pWeathers != null && !pWeathers.isEmpty()) {
                for (final Weather weather : pWeathers) {
                    Log.d("MainActivity", "onSuccess: " + weather.getWeatherMain());
                    WeatherAdapter weatherAdapter = new WeatherAdapter(pWeathers);
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

    public void getWeatherAsync(final String pStingId) {
        mForecastLoader.getForecast(pStingId);
    }

    @Override
    public void onStop() {
        super.onStop();

        mForecastLoader.removeListener(mListener);
    }

    private void showEmptyView(int errorCOde) {
        mMProgress.setVisibility(View.GONE);
        Toast.makeText(getContext(), "empty " + errorCOde, Toast.LENGTH_LONG).show();
    }
}
