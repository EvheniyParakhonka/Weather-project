package piftik.github.com.weatherproject.request;

import java.util.ArrayList;

import piftik.github.com.weatherproject.backend.RequestFromMyBackend;
import piftik.github.com.weatherproject.models.Weather;

public interface IForecastLoader {
//    O is too big
    void addListener(final IForecastLOaderListener pListener);

    void removeListener(final IForecastLOaderListener pListener);

    void getForecast(final String pCityId,final double pLatitude, final double pLongitude);

//    void getForecastForDay(final long pCityId, final int pDays);

    final class Impl {
       public static IForecastLoader getInstance() {
            return new RequestFromMyBackend();
        }

    }

    interface IForecastLOaderListener {
        void onSuccess(ArrayList<Weather> pForecast);

        void onError(final int pErrorCode);
    }
}
