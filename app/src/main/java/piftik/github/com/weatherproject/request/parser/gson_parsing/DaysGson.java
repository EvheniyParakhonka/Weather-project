package piftik.github.com.weatherproject.request.parser.gson_parsing;

import java.util.List;

class DaysGson {
    private final long dt;
    private final String dt_txt;
    private List<WeatherMain> weather;
    private TempGson main;

    public DaysGson(final long pDt, final String pDt_txt, final List<WeatherMain> pWeather, final TempGson pMain) {
        dt = pDt;
        dt_txt = pDt_txt;
        weather = pWeather;
        main = pMain;
    }

    public TempGson getMain() {
        return main;
    }

    public void setMain(final TempGson pMain) {
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

    public void setWeather(final List<WeatherMain> pWeather) {
        weather = pWeather;
    }
}
