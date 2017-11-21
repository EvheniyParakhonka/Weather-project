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
    private  static ViewPager mViewPager;
    private ViewPagerAdapter mPagerAdapter;
    Fragment mFragment;


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

    protected void createFragment(final Fragment pFragment) {
        mFragment = pFragment;
        addFragmentToViewPager();
    }


    public void addFragmentToViewPager() {

        if (mFragment == null) {
            mPagerAdapter.addFragment(new WeatherListFragment());
        } else {
            ((ViewPagerAdapter) mViewPager.getAdapter()).addFragment(mFragment);

        }
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Intent intent = new Intent(this, ServiceToCheckUpdate.class);
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        intent.putExtra("VERSION_APP", BuildConfig.VERSION_CODE);
        startService(intent);
    }

}
