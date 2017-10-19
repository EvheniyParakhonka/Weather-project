package piftik.github.com.weatherproject.backend;

import java.io.InputStream;
import java.util.ArrayList;

import piftik.github.com.weatherproject.Weather;

public interface IGsonParserFromMyBackend {

    ArrayList<Weather> extractWeatherFromJsonMyBackend(InputStream pInputStream) throws Exception ;

}
