package piftik.github.com.weatherproject;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WeatherListMain extends  {


    @Override
    protected Fragment createFragment() {
        return new WeatherListFragment();
    }
}
