package piftik.github.com.weatherproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;

import piftik.github.com.weatherproject.adapter.AdapterViewPager;

public class WeatherListMainActivity extends BaseActivity {
    private static final String TAG = WeatherListMainActivity.class.getSimpleName();
    private ViewPager mViewPager;
    private AdapterViewPager mPagerAdapter;


    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.view_pager);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new AdapterViewPager(
            getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        Log.d(TAG, "onCreate: Create");
        final CityChoseScreenFragment cityChoseScreenFragment = CityChoseScreenFragment.newInstance();
        cityChoseScreenFragment.setOnNewPageCityChooseAdd(mPagerAdapter);
        mPagerAdapter.addFragment(cityChoseScreenFragment, getString(R.string.for_not_choose_city));
    }

    public void remove() {

        getViewPagerAdapter().removeItemFromFragment(visiblePageNowPosition());
    }


    public  int visiblePageNowPosition() {
        return mViewPager.getCurrentItem();
    }

    private AdapterViewPager getViewPagerAdapter() {
        return ((AdapterViewPager) mViewPager.getAdapter());
    }

}
