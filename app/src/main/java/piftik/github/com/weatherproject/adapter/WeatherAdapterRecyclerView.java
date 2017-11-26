package piftik.github.com.weatherproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import piftik.github.com.weatherproject.R;
import piftik.github.com.weatherproject.models.Weather;
import piftik.github.com.weatherproject.utils.GetSmallImage;

public class WeatherAdapterRecyclerView extends RecyclerView.Adapter<WeatherAdapterRecyclerView.WeatherHolder>  {

    private final Context mContext;
    private final List<Weather> mWeathers;

    @Override
    public WeatherHolder onCreateViewHolder(final ViewGroup pParent, final int pViewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(mContext.getApplicationContext());
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

    public WeatherAdapterRecyclerView(final Context pContext, final List<Weather> pWeathers) {
        mContext = pContext;
        mWeathers = pWeathers;
    }

    static class WeatherHolder extends RecyclerView.ViewHolder {
        private final TextView mDateView;
        private final TextView mTemperatureMin;
        private final TextView mTemperatureMax;
        private final TextView mPlace;
        private final ImageView mSmallImageViewInRecycler;
        private Weather mWeather;

        WeatherHolder(final View pView) {
            super(pView);
            mDateView = (TextView) pView.findViewById(R.id
                .date_time_text_view);
            mTemperatureMin = (TextView) pView.findViewById(R.id.temperature_text_view_min);
            mTemperatureMax = (TextView) pView.findViewById(R.id.temperature_text_view_max);
            mPlace = (TextView) pView.findViewById(R.id.weather_main_text_view);
            mSmallImageViewInRecycler = (ImageView) pView.findViewById(R.id.small_image_weather);

        }

        @SuppressLint("SetTextI18n")
        void bindWeather(final Weather pWeather) {
            mWeather = pWeather;
            mDateView.setText(mWeather.getDate());
            final String tempMin = String.valueOf(mWeather.getTempMin());
            mTemperatureMin.setText(tempMin + " \u00B0" + "C");
            final String tempMax = String.valueOf((mWeather.getTempMax()));
            mTemperatureMax.setText(tempMax + " \u00B0" + "C");
            mPlace.setText(mWeather.getWeatherMain());
            mSmallImageViewInRecycler.setImageResource(
                GetSmallImage.getResousrceIDForWeatherSmallImage(mWeather.getWeatherMain()));
        }
    }
}
