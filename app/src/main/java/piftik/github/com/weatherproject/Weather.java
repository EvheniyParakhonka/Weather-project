package piftik.github.com.weatherproject;

public class Weather {

    private final String mDate;
    private final String mWeatherMain;
    private final double mTemp;
    private final String mCountry;
    private final String mCity;

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
