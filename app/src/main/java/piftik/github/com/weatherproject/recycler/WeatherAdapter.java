package piftik.github.com.weatherproject.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import piftik.github.com.weatherproject.R;
import piftik.github.com.weatherproject.Weather;
import piftik.github.com.weatherproject.WeatherListFragment;
import piftik.github.com.weatherproject.utils.TemperatureConverter;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder> {

    private final WeatherListFragment mWeatherListFragment;
    private final List<Weather> mWeathers;

    @Override
    public WeatherHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(mWeatherListFragment.getActivity());
        final View view = layoutInflater.inflate(R.layout.weather_fragment, parent, false);
        return new WeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(final WeatherHolder holder, final int position) {
        final Weather weather = mWeathers.get(position);
        holder.bindWeather(weather);
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
        private final TextView mTemperature;
        private final TextView mPlace;
        private Weather mWeather;

        WeatherHolder(final View pView) {
            super(pView);
            mDateView = (TextView) pView.findViewById(R.id.date_time_view);
            mTemperature = (TextView) pView.findViewById(R.id.temperature_view);
            mPlace = (TextView) pView.findViewById(R.id.city_country_view);
        }

        void bindWeather(final Weather pWeather) {
            mWeather = pWeather;
            mDateView.setText(mWeather.getDate());
            final String temp = String.valueOf(TemperatureConverter.convertTemperatureToCelsius(mWeather.getTemp()));
            mTemperature.setText(temp + " \u00B0" + "C");
            mPlace.setText(mWeather.getCity() + ", " + mWeather.getCountry());
        }
    }
}
