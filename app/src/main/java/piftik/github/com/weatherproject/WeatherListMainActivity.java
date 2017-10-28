package piftik.github.com.weatherproject;

import android.support.v4.app.Fragment;

public class WeatherListMainActivity extends SingleListActivity {


    @Override
    protected Fragment createFragment() {
        return new CityChoseScreenFragment();
    }
}
