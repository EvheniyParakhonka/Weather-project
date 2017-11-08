package piftik.github.com.weatherproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import piftik.github.com.weatherproject.update.ServiceToCheckUpdate;
import piftik.github.com.weatherproject.update.UpdateDialogFragment;
import piftik.github.com.weatherproject.utils.Constants;

public abstract class VisibleFragment extends Fragment {



    @Override
    public void onStart() {
        super.onStart();
        final IntentFilter filter = new IntentFilter
                (ServiceToCheckUpdate.ACTION_SHOW_FORCE_UPDATE);
        getActivity().registerReceiver(mUpdateDialogReceiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mUpdateDialogReceiver);
    }

    private final BroadcastReceiver mUpdateDialogReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            final FragmentManager manager = getFragmentManager();
            final UpdateDialogFragment dialog = new UpdateDialogFragment();
            dialog.show(manager, Constants.UPDATE_DIALOG);

        }
    };

}
