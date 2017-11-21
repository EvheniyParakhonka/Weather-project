package piftik.github.com.weatherproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = ViewPagerAdapter.class.getSimpleName();
    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private Fragment.SavedState mSavedState;

    public ViewPagerAdapter(final FragmentManager pFragmentManager, final List<Fragment> pFragments) {
        super(pFragmentManager);
        this.mFragmentManager = pFragmentManager;
        this.mFragments = pFragments;
    }

    public ViewPagerAdapter(final FragmentManager pFragmentManager) {
        super(pFragmentManager);
    }


    @Override
    public Fragment getItem(final int pPosition) {
        return mFragments.get(pPosition);
    }

    @Override
    public int getItemPosition(Object object) {
        int index = mFragments.indexOf(object);
        if (index == -1) {
            return POSITION_NONE;
        } else {
            return index;
        }
    }

    @Override
    public int getCount() {
        return mFragments != null ? mFragments.size() : 0;
    }

    //Add fragment to right of end
    public void addFragment(final Fragment pFragment) {
        addFragment(pFragment, mFragments.size());
    }

    //-----------------------------------------------------------------------------
    // Add fragment at "position" to "views".

    private void addFragment(final Fragment pFragment, final int pPosition) {
        mFragments.add(pPosition, pFragment);
        notifyDataSetChanged();
    }

    public void removeFragments() {
        try {
            final List<Fragment> newList = new ArrayList<>();
            final Fragment general = mFragments.get(0);
            newList.add(general);
            this.mFragments.clear();
            this.mFragments = new ArrayList<>();
            this.mFragments = newList;
            notifyDataSetChanged();
        } catch (final Exception pE) {
            Log.e(TAG, "removeFragments: " + pE);
        }
    }

    public void removeItemFromFragment(final ViewGroup pViewGroup, final int pPosition, final Object pObject) {
        try {
            try {
                pViewGroup.removeViewAt(pPosition);
                ((ViewPager) pViewGroup).setCurrentItem(pPosition - 1);
            } catch (final Exception pE) {
                Log.e(TAG, "removeItemFromFragment: " + pE);
            }
            notifyDataSetChanged();
        } catch (final Exception pE) {
            Log.e(TAG, "removeItemFromFragment: " + pE);
        }
    }
}

