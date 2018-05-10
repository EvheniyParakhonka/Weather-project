package piftik.github.com.weatherproject.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import piftik.github.com.weatherproject.fragments.BaseFragment;
import piftik.github.com.weatherproject.fragments.CityChoseScreenFragment;
import piftik.github.com.weatherproject.fragments.WeatherListFragment;

public class AdapterViewPager extends FragmentStatePagerAdapter implements CityChoseScreenFragment.OnNewPageCityChosseAddListnener {
    private final List<BaseFragment> mFragments = new ArrayList<>();
    private final ViewPager mViewPager;

    public AdapterViewPager(final FragmentManager pFragmentManager, final ViewPager pViewPager) {
        super(pFragmentManager);

        mViewPager = pViewPager;
    }

    @Override
    public Fragment getItem(final int pPosition) {
        return mFragments.get(pPosition);
    }

    @Override
    public int getItemPosition(final Object pObject) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(final int pPosition) {
        return mFragments.get(pPosition).getTitle();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(final BaseFragment pFragment) {
        mFragments.add(pFragment);
        notifyDataSetChanged();
    }

    private void replace(final BaseFragment pFragment, final int pPosition, final String pCityForTittle) {
        mFragments.set(pPosition, pFragment);
        notifyDataSetChanged();
    }

    public void removeItemFromFragment(final int pPosition) {
        mFragments.remove(pPosition);
        notifyDataSetChanged();
    }

    @Override
    public void onNewPageCityChosseAdd(final String pCityId, final double pLatitude, final double pLongitude) {
        final WeatherListFragment fragment = WeatherListFragment.newInstance(pCityId, pLatitude, pLongitude);
        replace(fragment, mViewPager.getCurrentItem(), pCityId);
    }

    public boolean whatAFragment(final int pPosition) {
        return mFragments.get(pPosition).whatAFragment();
    }

    public String getCity(final int pPosition) {
        return mFragments.get(pPosition).getTitle();
    }

    public float getLat(final int pPosition) {
        return (float) mFragments.get(pPosition).whatALatitude();
    }

    public float getLong(final int pPosition) {
        return (float) mFragments.get(pPosition).whatALongitude();
    }
}
