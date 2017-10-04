package piftik.github.com.weatherproject.utils;

public class TemperatureConverter {
    public static int convertTemperatureToCelsius(int kelvinTemperature) {
        return kelvinTemperature - 273;
    }
}
