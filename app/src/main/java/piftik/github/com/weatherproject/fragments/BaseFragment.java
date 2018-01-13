package piftik.github.com.weatherproject.fragments;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {
    public abstract String getTitle();

    public abstract boolean whatAFragment();

    public double whatALat() {
        return 0;
    }

    public double whatALong() {
        return 0;
    }

}
