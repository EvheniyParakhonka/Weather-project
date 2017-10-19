package com.example.main.weather_project.backend;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.List;

@Entity
public class Weather {

    @Id
    String mCity;
    private String mCountry;
    private List<Days> mList;

    public Weather() {
    }

    public Weather(String mCity, String mCountry, List<Days> mList) {
        this.mCity = mCity;
        this.mCountry = mCountry;
        this.mList = mList;
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
    }

    public String getCity() {
        return mCity;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setList(List<Days> mList) {
        this.mList = mList;
    }

    public List<Days> getList() {
        return mList;
    }

    public static class Days {

        private double mTemp;
        private String mDate;
        private String mWeatherMain;

        public Days() {
        }

        public Days(double mTemp, String mDate, String mWeatherMain) {
            this.mTemp = mTemp;
            this.mDate = mDate;
            this.mWeatherMain = mWeatherMain;
        }

        public void setDate(String mDate) {
            this.mDate = mDate;
        }

        public void setWeatherMain(String mWeatherMain) {
            this.mWeatherMain = mWeatherMain;
        }

        public void setTemp(double mTemp) {
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



