package piftik.github.com.weatherproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = ViewPagerAdapter.class.getSimpleName();
    private List<Fragment> mFragments = new ArrayList<>();
    private FragmentManager mFragmentManager;
    private Fragment mFragmentToReplace;
    private ViewGroup mViewGroup;
    private int mPosition;




    public ViewPagerAdapter(final FragmentManager pFragmentManager) {
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
            return POSITION_NONE;
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


    public void replace(final Fragment pFragment, final int pPosition) {
        mPosition = pPosition;
        mFragmentToReplace = pFragment;
        notifyDataSetChanged();
    }

    public void removeItemFromFragment(final int pPosition) {
        mFragments.remove(pPosition);
        notifyDataSetChanged();

    }
}

