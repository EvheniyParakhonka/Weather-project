package com.example.main.weather_project.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.List;

@Entity
public class Weather {

    @Id
    private String mCity;
    private String mCountry;
    private List<Days> mList;
    private final int current_app_version = 1;

    public Weather() {
    }

    public Weather(final String mCity, final String mCountry, final List<Days> mList) {
        this.mCity = mCity;
        this.mCountry = mCountry;
        this.mList = mList;
    }

    public int getCurrent_app_version() {
        return current_app_version;
    }

    public void setCity(final String mCity) {
        this.mCity = mCity;
    }

    public String getCity() {
        return mCity;
    }

    public void setCountry(final String mCountry) {
        this.mCountry = mCountry;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setList(final List<Days> mList) {
        this.mList = mList;
    }

    public List<Days> getList() {
        return mList;
    }

    static class Days {

        private double mTemp;
        private String mDate;
        private String mWeatherMain;

        public Days() {
        }

        Days(final double mTemp, final String mDate, final String mWeatherMain) {
            this.mTemp = mTemp;
            this.mDate = mDate;
            this.mWeatherMain = mWeatherMain;
        }

        public void setDate(final String mDate) {
            this.mDate = mDate;
        }

        public void setWeatherMain(final String mWeatherMain) {
            this.mWeatherMain = mWeatherMain;
        }

        public void setTemp(final double mTemp) {
            this.mTemp = mTemp;
        }

        public double getTemp() {
            return mTemp;
        }

        public String getDate() {
            return mDate;
        }

        public String getWeatherMain() {
            return mWeatherMain;
        }
    }
}



