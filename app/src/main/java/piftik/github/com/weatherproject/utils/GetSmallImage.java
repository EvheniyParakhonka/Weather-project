package piftik.github.com.weatherproject.utils;

import piftik.github.com.weatherproject.R;

public final class GetSmallImage {
    public static int getResousrceIDForWeatherSmallImage(final String pWeatherMain) {
        int image = 0;

        switch (pWeatherMain) {

            case "Snow":
                image = R.drawable.snow;
                break;


            case "Rain":
                image = R.drawable.drizzle;
                break;

            case "Clear":
                image = R.drawable.sunny;
                break;

            case "Clouds":
                image = R.drawable.cloudy;
                break;

        }
        return image;
    }
}
