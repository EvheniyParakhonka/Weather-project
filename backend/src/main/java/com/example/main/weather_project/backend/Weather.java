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

    private static class Days {

        private String mTempMin;
        private String mTempMax;
        private String mDate;
        private String mWeatherMain;

        public Days() {
        }

        Days( final String pTempMin, final String pTempMax, final String mDate, final String mWeatherMain) {
            this.mTempMin = pTempMin;
            this.mTempMax = pTempMax;
            this.mDate = mDate;
            this.mWeatherMain = mWeatherMain;
        }

        public void setDate(final String mDate) {
            this.mDate = mDate;
        }

        public void setWeatherMain(final String mWeatherMain) {
            this.mWeatherMain = mWeatherMain;
        }

        public String getTempMin() {
            return mTempMin;
        }

        public void setTempMin(final String pTempMin) {
            mTempMin = pTempMin;
        }

        public String getTempMax() {
            return mTempMax;
        }

        public void setTempMax(final String pTempMax) {
            mTempMax = pTempMax;
        }

        public String getDate() {
            return mDate;
        }

        public String getWeatherMain() {
            return mWeatherMain;
        }
    }
}



