//package piftik.github.com.weatherproject.request.parser;
//
//import com.google.gson.Gson;
//
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.util.ArrayList;
//
//import piftik.github.com.weatherproject.Weather;
//import piftik.github.com.weatherproject.request.http.IReadFromStream;
//import piftik.github.com.weatherproject.request.http.ReadFromStream;
//import piftik.github.com.weatherproject.request.parser.gson_parsing.ResponsGson;
//
//public class GsonParser implements IJsonParser {
//
//    private static final String TAG = GsonParser.class.getSimpleName();
//
//    @Override
//    public ArrayList<Weather> extractWeatherFromJson(final String pInputStream) throws Exception {
//        final ArrayList<Weather> forecasts = new ArrayList<>();
//
//        final String jsonResponse = null;
//
//        final Gson gson = new Gson();
////        final Reader reader = new InputStreamReader(pInputStream);
//        final IReadFromStream reader = new ReadFromStream();
//        reader =reader.readFromStream(pInputStream);
//        final ResponsGson responsGson = gson.fromJson(reader, ResponsGson.class);
//        final String placeOfCit = responsGson.getCity().getName();
//        final String country = responsGson.getCity().getCountry();
//        for (int i = 0; i < responsGson.getList().size(); i++) {
//            final String dataText = responsGson.getList().get(i).getDt_txt();
//            final String weatherMain = responsGson.getList().get(i).getWeather().get(0).getMain();
//            final double tempInKelvin = responsGson.getList().get(i).getMain().getTemp();
//            forecasts.add(new Weather(dataText, weatherMain, tempInKelvin, country, placeOfCit));
//        }
//
//        return forecasts;
//    }
//}
