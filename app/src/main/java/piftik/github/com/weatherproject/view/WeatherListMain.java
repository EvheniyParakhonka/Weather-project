package piftik.github.com.weatherproject.view;

import android.support.v4.app.Fragment;

import piftik.github.com.weatherproject.controller.WeatherListFragment;

public class WeatherListMain extends SingleListFragment {


    @Override
    protected Fragment createFragment() {
        return new WeatherListFragment();
    }
}
