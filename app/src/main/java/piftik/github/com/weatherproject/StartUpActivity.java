package piftik.github.com.weatherproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import piftik.github.com.weatherproject.update.ServiceToCheckUpdate;

public class StartUpActivity extends FragmentActivity {


    @Override
    protected void onCreate(@Nullable final Bundle pSavedInstanceState) {
        super.onCreate(pSavedInstanceState);
        final Intent intent = new Intent(this, ServiceToCheckUpdate.class);
        intent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        intent.putExtra("VERSION_APP", BuildConfig.VERSION_CODE);
        startService(intent);

        final Intent startUpIntent = new Intent(this, WeatherListMainActivity.class);
        startUpIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startUpIntent);

    }
}
