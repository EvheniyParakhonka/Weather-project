package piftik.github.com.weatherproject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import piftik.github.com.weatherproject.adapter.AdapterViewPager;

public class WeatherListMainActivity extends BaseActivity {
    private static final String TAG = WeatherListMainActivity.class.getSimpleName();
    private ViewPager mViewPager;
    private AdapterViewPager mViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        setContentView(R.layout.view_pager);

        actionBarCustom(getSupportActionBar());
        initViewPager();

        if (pSavedInstanceState == null) {
            setFirstFragmentToViewPager();
        }

    }

    private void setFirstFragmentToViewPager() {
        final CityChoseScreenFragment cityChoseScreenFragment = CityChoseScreenFragment.newInstance();
        cityChoseScreenFragment.setOnNewPageCityChooseAdd(mViewPagerAdapter);
        mViewPagerAdapter.addFragment(cityChoseScreenFragment, getString(R.string.for_not_choose_city));
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPagerAdapter = new AdapterViewPager(
            getSupportFragmentManager(), mViewPager);
        mViewPager.setAdapter(mViewPagerAdapter);
    }

    public void remove() {

        getViewPagerAdapter().removeItemFromFragment(visiblePageNowPosition());
    }


    public int visiblePageNowPosition() {
        return mViewPager.getCurrentItem();
    }

    private AdapterViewPager getViewPagerAdapter() {
        return ((AdapterViewPager) mViewPager.getAdapter());
    }

    protected void actionBarCustom(final ActionBar mSupportActionBar) {

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
                    getViewPagerAdapter().addFragment(cityChoseScreenFragment, getString(R.string.for_not_choose_city));

                    if (mViewPager != null) {
                        mViewPager.setCurrentItem(getViewPagerAdapter().getCount());
                    }
                }
            });
        }
    }
}
