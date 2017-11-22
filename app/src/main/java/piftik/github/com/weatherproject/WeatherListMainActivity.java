package piftik.github.com.weatherproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import piftik.github.com.weatherproject.adapter.ViewPagerAdapter;
import piftik.github.com.weatherproject.update.ServiceToCheckUpdate;

public class WeatherListMainActivity extends FragmentActivity {
        @SuppressLint("StaticFieldLeak")
    private static ViewPager mViewPager;
    private ViewPagerAdapter mPagerAdapter;
    private Fragment mFragment;


    @Override
    protected void onResume() {
        super.onResume();
        final Intent intent = new Intent(this, ServiceToCheckUpdate.class);
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        intent.putExtra("VERSION_APP", BuildConfig.VERSION_CODE);
        startService(intent);
    }


    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.view_pager);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new ViewPagerAdapter(
            getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        addFragmentToViewPager();
    }

    void createFragment(final Fragment pFragment) {
        mFragment = pFragment;
        addFragmentToViewPager();
    }

    public void replace(final String pCityId) {
        final Fragment fragment = WeatherListFragment.newInstance(pCityId);
        getViewPagerAdapter().replace(fragment, visiblePageNowPosition());
    }

    public void remove() {

        getViewPagerAdapter().removeItemFromFragment(visiblePageNowPosition());
    }

    private void addFragmentToViewPager() {

        if (mFragment == null) {
            mPagerAdapter.addFragment(new CityChoseScreenFragment());
        } else {
            getViewPagerAdapter().addFragment(mFragment);
        }
    }

    private int visiblePageNowPosition() {
        return mViewPager.getCurrentItem();
    }

    private ViewPagerAdapter getViewPagerAdapter() {
        return ((ViewPagerAdapter) mViewPager.getAdapter());
    }

}
