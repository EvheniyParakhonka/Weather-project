package piftik.github.com.weatherproject.request.parser;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import piftik.github.com.weatherproject.Weather;
import piftik.github.com.weatherproject.request.parser.GsonParsing.ResponsGson;

public class GsonParser implements IJsonParser {
    private static final String TAG = GsonParser.class.getSimpleName();

    @Override
    public ArrayList<Weather> extractWeatherFromJson(InputStream pInputStream) throws Exception {
        ArrayList<Weather> forecasts = new ArrayList<>();

        String jsonResponse = null;


        Gson gson = new Gson();
        Reader reader = new InputStreamReader(pInputStream);
        ResponsGson responsGson= gson.fromJson(reader, ResponsGson.class);
        String placeOfCit = responsGson.getCity().getName();
        String country = responsGson.getCity().getCountry();
        for (int i = 0; i < responsGson.getList().size(); i ++) {
            String dataText = responsGson.getList().get(i).getDt_txt();
            String weatherMain = responsGson.getList().get(i).getWeather().get(0).getMain();
            double tempInKelvin = responsGson.getList().get(i).getMain().getTemp();
            forecasts.add(new Weather(dataText, weatherMain, tempInKelvin, country, placeOfCit));
        }

        return forecasts;
    }
}
