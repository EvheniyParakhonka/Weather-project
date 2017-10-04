package piftik.github.com.weatherproject.model;

import java.util.ArrayList;

public interface IForecastLoader {
    void addListener(final IForecastLOaderListener pListener);

    void removeListener(final IForecastLOaderListener pListener);

    void getForecast(final String pCityId);

//    void getForecastForDay(final long pCityId, final int pDays);

    class Impl {
        public static IForecastLoader getInstance() {
            return new RequestFromOpenWeather();
        }

    }

    interface IForecastLOaderListener {
        void onSuccess(ArrayList<Weather> pForecast);

        void onError(final int errorCode);
    }
}
