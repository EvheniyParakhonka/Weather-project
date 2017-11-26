package piftik.github.com.weatherproject.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import piftik.github.com.weatherproject.CityChoseScreenFragment;
import piftik.github.com.weatherproject.R;
import piftik.github.com.weatherproject.WeatherListFragment;

public class AdapterViewPager extends FragmentStatePagerAdapter implements WeatherListFragment.OnNewLocationSelectedListnener, CityChoseScreenFragment.OnNewPageCityChosseAddListnener {
    private static final String TAG = AdapterViewPager.class.getSimpleName();
    private List<Fragment> mFragments = new ArrayList<>();
    private List<String> mTitels = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private Fragment mFragmentToReplace;
    private int mPosition;
    private String mCityForTittle;
    private ViewPager mViewPager;


    public AdapterViewPager(final FragmentManager pFragmentManager) {
        super(pFragmentManager);
    }

    @Override
    public Fragment getItem(final int pPosition) {
        return mFragments.get(pPosition);
    }

    @Override
    public int getItemPosition(final Object pObject) {

        if (pObject.equals(mFragmentToReplace)) {
            return super.getItemPosition(pObject);
        } else {
            mFragments.set(mPosition, mFragmentToReplace);
            mTitels.set(mPosition, mCityForTittle);
            return POSITION_NONE;
        }
    }

    @Override
    public Object instantiateItem(final ViewGroup pContainer, final int pPosition) {
        mViewPager = (ViewPager) pContainer;
        mPosition = pPosition;
        return super.instantiateItem(pContainer, pPosition);
    }

    @Override
    public CharSequence getPageTitle(final int pPosition) {
        return mTitels.get(pPosition);
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    //Add fragment to right of end
    public void addFragment(final Fragment pFragment, final String pCityForTittle) {
        mTitels.add(pCityForTittle);
        addFragment(pFragment, mFragments.size());
    }

    // Add fragment at "position" to "views".

    private void addFragment(final Fragment pFragment, final int pPosition) {
        mFragments.add(pPosition, pFragment);
        notifyDataSetChanged();
    }


    private void replace(final Fragment pFragment, final int pPosition, final String pCityForTittle) {
        mPosition = pPosition;
        mCityForTittle = pCityForTittle;
        mFragmentToReplace = pFragment;
        notifyDataSetChanged();
    }

    public void removeItemFromFragment(final int pPosition) {
        mFragments.remove(pPosition);
        mTitels.remove(pPosition);
        notifyDataSetChanged();

    }

    @Override
    public void onNewLocationSelected(final Context pContext) {
        final CityChoseScreenFragment cityChoseScreenFragment = CityChoseScreenFragment.newInstance();
        cityChoseScreenFragment.setOnNewPageCityChooseAdd(this);
        addFragment(cityChoseScreenFragment, pContext.getString(R.string.for_not_choose_city));
        mViewPager.setCurrentItem(mPosition);
    }


    @Override
    public void onNewPageCityChosseAdd(final String pCityId) {
        final WeatherListFragment fragment = WeatherListFragment.newInstance(pCityId);
        fragment.setOnNewLocationSelectedListnener(this);
        replace(fragment,mViewPager.getCurrentItem() , pCityId);
    }
}

