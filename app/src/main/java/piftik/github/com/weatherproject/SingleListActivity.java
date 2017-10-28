package piftik.github.com.weatherproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import piftik.github.com.weatherproject.update.ServiceToCheckUpdate;

abstract class SingleListActivity extends FragmentActivity {
    protected abstract Fragment createFragment();
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);

        final FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
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
