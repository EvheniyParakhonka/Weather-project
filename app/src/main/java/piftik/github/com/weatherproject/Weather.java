package piftik.github.com.weatherproject;

public class Weather {
    private final String mDate;
    private final String mWeatherMain;
    private final double mTemp;
    private final String mCountry;
    private final String mCity;

    public Weather(String pDate, String pWeatherMain, double pTemp, String pCountry, String pCity) {
        mDate = pDate;
        mWeatherMain = pWeatherMain;
        mTemp = pTemp;
        mCountry = pCountry;
        mCity = pCity;
    }

    String getDate() {
        return mDate;
    }

    String getWeatherMain() {
        return mWeatherMain;
    }

    double getTemp() {
        return mTemp;
    }

    String getCountry() {
        return mCountry;
    }

    String getCity() {
        return mCity;
    }
}
