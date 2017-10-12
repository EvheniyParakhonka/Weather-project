package piftik.github.com.weatherproject;

import android.support.v4.app.Fragment;

import piftik.github.com.weatherproject.CityChoseScreen;
import piftik.github.com.weatherproject.SingleListFragment;

public class WeatherListMain extends SingleListFragment {


    @Override
    protected Fragment createFragment() {
        return new CityChoseScreen();
    }
}
