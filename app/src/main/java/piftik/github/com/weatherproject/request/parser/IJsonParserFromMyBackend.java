package piftik.github.com.weatherproject.request.parser;

import java.util.ArrayList;

import piftik.github.com.weatherproject.Weather;

public interface IJsonParserFromMyBackend {

    ArrayList<Weather> extractWeatherFromJsonMyBackend(String pInputStream);

}