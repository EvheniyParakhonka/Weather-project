package piftik.github.com.weatherproject.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import piftik.github.com.weatherproject.update.ServiceToCheckUpdate;
import piftik.github.com.weatherproject.update.UpdateDialogFragment;
import piftik.github.com.weatherproject.utils.Constants;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        final IntentFilter filter = new IntentFilter
            (ServiceToCheckUpdate.ACTION_SHOW_FORCE_UPDATE);
        registerReceiver(mUpdateDialogReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mUpdateDialogReceiver);
    }
    private final BroadcastReceiver mUpdateDialogReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(final Context context, final Intent intent) {
            final FragmentManager manager = getSupportFragmentManager();
            final UpdateDialogFragment dialog = new UpdateDialogFragment();
            dialog.show(manager, Constants.UPDATE_DIALOG);

        }
    };
}
