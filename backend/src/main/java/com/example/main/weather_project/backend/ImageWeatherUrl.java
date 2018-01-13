package com.example.main.weather_project.backend;

import java.util.ArrayList;
import java.util.List;

public class ImageWeatherUrl {
    private List<String> rain = new ArrayList<>();
    public  void main(final String[] args){
        rain.add("http://www.weatherwizkids.com/wp-content/uploads/2015/04/rain1.jpg");
        rain.add("http://www.weatherwizkids.com/wp-content/uploads/2015/02/rain21.jpg");
        rain.add("http://ontariospca.ca/blog/wp-content/uploads/2016/04/Photo-by-Tomasz-Sienicki-.jpeg");
        rain.add("https://cdn.pixabay.com/photo/2017/11/01/08/16/rain-2907366_960_720.jpg");
        rain.add("https://cdn.pixabay.com/photo/2015/06/01/08/54/landscape-793026_960_720.jpg");
    }

    public List<String> getRain() {
        return rain;
    }
}
