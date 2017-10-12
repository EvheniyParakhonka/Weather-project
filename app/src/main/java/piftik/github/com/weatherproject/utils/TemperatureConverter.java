package piftik.github.com.weatherproject.utils;

public class TemperatureConverter {
    public static double convertTemperatureToCelsius(double kelvinTemperature) {
        return kelvinTemperature - 273;
    }
}
