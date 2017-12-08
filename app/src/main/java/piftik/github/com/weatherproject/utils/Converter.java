package piftik.github.com.weatherproject.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Converter {

    public static int convertTemperatureToCelsius(final double pKelvinTemperature) {
        return (int) pKelvinTemperature - Constants.KELVIN_DIFF;
    }

    public static String convertUnixTimeToDays(final long pUnixTime) {
        final Date date = new Date(pUnixTime * 1000L); // *1000 is to convert seconds to milliseconds
        final SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd, MM, yyyy");
        return sdf.format(date);
    }
}
