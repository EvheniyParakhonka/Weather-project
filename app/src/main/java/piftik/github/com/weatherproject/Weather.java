package piftik.github.com.weatherproject;

import com.google.gson.annotations.SerializedName;

public class Weather {

    private String mDate;
    private String mWeatherMain;
    private double mTemp;
    private String mCountry;
    private String mCity;

    public Weather(String pDate, String pWeatherMain, double pTemp, String pCountry, String pCity) {
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
