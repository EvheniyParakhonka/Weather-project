package piftik.github.com.weatherproject;

public class Weather {

    private String mDate;
    private String mWeatherMain;
    private final double mTemp;
    private String mCountry;
    private String mCity;

    public Weather(final String pDate, final String pWeatherMain, final double pTemp, final String pCountry, final String pCity) {
        mDate = pDate;
        mWeatherMain = pWeatherMain;
        mTemp = pTemp;
        mCountry = pCountry;
        mCity = pCity;
    }

    public String getDate() {
        return mDate;
    }

    public String getWeatherMain() {
        return mWeatherMain;
    }

    public double getTemp() {
        return mTemp;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getCity() {
        return mCity;
    }
}
