package piftik.github.com.weatherproject.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import piftik.github.com.weatherproject.R;
import piftik.github.com.weatherproject.WeatherListFragment;
import piftik.github.com.weatherproject.models.Weather;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder> {

    private final WeatherListFragment mWeatherListFragment;
    private final List<Weather> mWeathers;

    @Override
    public WeatherHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(mWeatherListFragment.getActivity());
        final View view = layoutInflater.inflate(R.layout.weather_fragment, pParent, false);
        return new WeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(final WeatherHolder pHolder, final int pPosition) {
        final Weather weather = mWeathers.get(pPosition);
        pHolder.bindWeather(weather);
    }

    @Override
    public int getItemCount() {
        return mWeathers.size();
    }

    //TODO Create interface and put into parameters or context
    public WeatherAdapter(final WeatherListFragment pWeatherListFragment, final List<Weather> pWeathers) {
        mWeatherListFragment = pWeatherListFragment;
        mWeathers = pWeathers;
    }

    static class WeatherHolder extends RecyclerView.ViewHolder {
        private final TextView mDateView;
        private final TextView mTemperatureMin;
        private final TextView mTemperatureMax;
        private final TextView mPlace;
        private Weather mWeather;

        WeatherHolder(final View pView) {
            super(pView);
            mDateView = (TextView) pView.findViewById(R.id
                .date_time_text_view);
            mTemperatureMin = (TextView) pView.findViewById(R.id.temperature_text_view_min);
            mTemperatureMax = (TextView) pView.findViewById(R.id.temperature_text_view_max);
            mPlace = (TextView) pView.findViewById(R.id.weather_main_text_view);

        }

        @SuppressLint("SetTextI18n")
        void bindWeather(final Weather pWeather) {
            mWeather = pWeather;
            mDateView.setText(mWeather.getDate());
            final String tempMin = String.valueOf(mWeather.getTempMin());
            mTemperatureMin.setText(tempMin + " \u00B0" + "C");
            final String tempMax = String.valueOf((mWeather.getTempMax()));
            mTemperatureMax.setText(tempMax + " \u00B0" + "C");
            mPlace.setText(mWeather.getCity() + ", " + mWeather.getCountry());
        }
    }
}
