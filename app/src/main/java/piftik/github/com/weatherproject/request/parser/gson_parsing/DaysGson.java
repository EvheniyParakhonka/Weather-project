package piftik.github.com.weatherproject.request.parser.gson_parsing;

import java.util.List;

public class DaysGson {
    private final long dt;
    private final String dt_txt;
    private List<WeatherMain> weather;
    private TempGson main;

    public DaysGson(long pDt, String pDt_txt, List<WeatherMain> pWeather, TempGson pMain) {
        dt = pDt;
        dt_txt = pDt_txt;
        weather = pWeather;
        main = pMain;
    }

    public TempGson getMain() {
        return main;
    }

    public void setMain(TempGson pMain) {
        main = pMain;
    }

    public long getDt() {
        return dt;
    }



    public String getDt_txt() {
        return dt_txt;
    }

    public List<WeatherMain> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherMain> pWeather) {
        weather = pWeather;
    }
}
