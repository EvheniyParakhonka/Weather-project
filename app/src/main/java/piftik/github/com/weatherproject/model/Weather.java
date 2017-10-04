package piftik.github.com.weatherproject.model;

public class Weather {
    private final String mDate;
    private final String mWeatherMain;
    private final int mTemp;
    private final String mCountry;
    private final String mCity;

    Weather(String pDate, String pWeatherMain, int pTemp, String pCountry, String pCity) {
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

    public int getTemp() {
        return mTemp;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getCity() {
        return mCity;
    }
}
