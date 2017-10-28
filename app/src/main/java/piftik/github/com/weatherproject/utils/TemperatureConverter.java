package piftik.github.com.weatherproject.utils;

public final class TemperatureConverter {

    public static int convertTemperatureToCelsius(final double kelvinTemperature) {
        return (int)kelvinTemperature - Constants.TEMP_DIFF;
    }
}
