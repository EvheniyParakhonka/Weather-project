package piftik.github.com.weatherproject;

public class Weather {

    private final String mDate;
    private final String mWeatherMain;
    private final double mTempMin;
    private final double mTempMax;
    private final String mCountry;
    private final String mCity;

    public Weather(final String pDate, final String pWeatherMain, final double pTempMin, final double pTempMax, final String pCountry, final String pCity) {
        mDate = pDate;
        mWeatherMain = pWeatherMain;
        mTempMin = pTempMin;
        mTempMax = pTempMax;
        mCountry = pCountry;
        mCity = pCity;
    }

    public String getDate() {
        return mDate;
    }

    public String getWeatherMain() {
        return mWeatherMain;
    }

    public double getTempMin() {
        return mTempMin;
    }

    public double getTempMax() {
        return mTempMax;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getCity() {
        return mCity;
    }
}
