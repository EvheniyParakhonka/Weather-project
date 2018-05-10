package piftik.github.com.weatherproject.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import piftik.github.com.weatherproject.R;
import piftik.github.com.weatherproject.adapters.AdapterViewPager;
import piftik.github.com.weatherproject.fragments.CityChoseScreenFragment;
import piftik.github.com.weatherproject.fragments.WeatherListFragment;
import piftik.github.com.weatherproject.utils.Constants;

public class WeatherListMainActivity extends BaseActivity {
    private static final String TAG = WeatherListMainActivity.class.getSimpleName();

    private ViewPager mViewPager;
    private AdapterViewPager mViewPagerAdapter;
    private SharedPreferences mPref;

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.view_pager);

        actionBarCustom(getSupportActionBar());
        initViewPager();
        loadFromSharedPreferens(pSavedInstanceState);

    }

    private void setFirstFragmentToViewPager() {
        final CityChoseScreenFragment cityChoseScreenFragment = CityChoseScreenFragment.newInstance();
        cityChoseScreenFragment.setOnNewPageCityChooseAdd(mViewPagerAdapter);
        mViewPagerAdapter.addFragment(cityChoseScreenFragment);
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPagerAdapter = new AdapterViewPager(
            getSupportFragmentManager(), mViewPager);
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    private void saveToSharedPreferens() {
        mPref = getPreferences(MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") final SharedPreferences.Editor edit = mPref.edit();
        final int countPageInViewPager = getViewPagerAdapter().getCount();
//        TODO refact pref to bd
//        TODO BD work in asyncTask
//        TODO Work in OnSaveInstateState
        if (countPageInViewPager > 0) {
            edit.putInt(Constants.KEY_SHPREF_COUNT_PAGE, countPageInViewPager);
            for (int i = 0; i < countPageInViewPager; i++) {
                final boolean whatAFragment = getViewPagerAdapter().whatAFragment(i);
                final String cityName = getViewPagerAdapter().getCity(i);
                final float lat = getViewPagerAdapter().getLat(i);
                final float lon = getViewPagerAdapter().getLong(i);
                edit.putBoolean(Constants.KEY_WHAT_A_FRAGMENT_IN_PAGE + i, whatAFragment);
                edit.putString(Constants.KEY_CITY_NAME + i, cityName);
                edit.putFloat(Constants.KEY_CITY_LATIT + i, lat);
                edit.putFloat(Constants.KEY_CITY_LONG + i, lon);
            }
        }
        edit.apply();
    }

    private void loadFromSharedPreferens(@Nullable final Bundle pSavedInstanceState) {
        mPref = getPreferences(MODE_PRIVATE);
        final int count = mPref.getInt(Constants.KEY_SHPREF_COUNT_PAGE, 0);

        if (count == 0) {
            if (pSavedInstanceState == null) {
                setFirstFragmentToViewPager();
            }
        } else {
            for (int i = 0; i < count; i++) {
                final boolean isWeatherFragment = mPref.getBoolean(Constants.KEY_WHAT_A_FRAGMENT_IN_PAGE + i, false);

                if (isWeatherFragment) {
                    final String city = mPref.getString(Constants.KEY_CITY_NAME + i, null);
                    final double lon = (double) mPref.getFloat(Constants.KEY_CITY_LONG + i, 0);
                    final double lat = (double) mPref.getFloat(Constants.KEY_CITY_LATIT + i, 0);
                    getViewPagerAdapter().addFragment(WeatherListFragment.newInstance(city, lat, lon));
                } else {
                    final CityChoseScreenFragment cityChoseScreenFragment = CityChoseScreenFragment.newInstance();
                    cityChoseScreenFragment.setOnNewPageCityChooseAdd(mViewPagerAdapter);
                    getViewPagerAdapter().addFragment(cityChoseScreenFragment);
                }
            }
        }
    }

    private void remove() {

        if (getViewPagerAdapter().getCount() > 1) {
            getViewPagerAdapter().removeItemFromFragment(visiblePageNowPosition());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveToSharedPreferens();
    }

    private int visiblePageNowPosition() {
        return mViewPager.getCurrentItem();
    }

    private AdapterViewPager getViewPagerAdapter() {
        return ((AdapterViewPager) mViewPager.getAdapter());
    }

    private void actionBarCustom(final ActionBar mSupportActionBar) {

        if (mSupportActionBar != null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(this);
            @SuppressLint("InflateParams") final View cuustomViewActionBar = layoutInflater.inflate(R.layout.custom_action_bar_layout, null);
            mSupportActionBar.setCustomView(cuustomViewActionBar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mSupportActionBar.setDisplayShowCustomEnabled(true);
            mSupportActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

            final ImageButton addImageButton = (ImageButton) cuustomViewActionBar.findViewById(R.id.action_bar_add_image_button);
            final ImageButton removeImageButton = (ImageButton) cuustomViewActionBar.findViewById(R.id.action_bar_delete_image_button);

            addImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View pV) {

                    final CityChoseScreenFragment cityChoseScreenFragment = CityChoseScreenFragment.newInstance();
                    cityChoseScreenFragment.setOnNewPageCityChooseAdd(mViewPagerAdapter);
                    getViewPagerAdapter().addFragment(cityChoseScreenFragment);

                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(getViewPagerAdapter().getCount());
                    }
                }
            });

            removeImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View pView) {
                    remove();
                }
            });
        }
    }
}
