package piftik.github.com.weatherproject.update;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import piftik.github.com.weatherproject.BuildConfig;
import piftik.github.com.weatherproject.request.http.HttpClient;
import piftik.github.com.weatherproject.request.http.IHttpClient;

public class ServiceToCheckUpdate extends IntentService {

    private IHttpClient mIClient;
    public static final String ACTION_SHOW_FORCE_UPDATE = "piftik.github.com.weatherproject.Update";

    public ServiceToCheckUpdate() {
        super("WeatherApp");
    }

    @Override
    public void onCreate() {
        mIClient = new HttpClient();
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable final Intent pIntent) {

        assert pIntent != null;
        final int versionApp = pIntent.getIntExtra("VERSION_APP", 0);

        if (isOnline()) {
            try {
                final String response = mIClient.makeHttpRequest(BuildConfig.BASE_URL);

                if (response != null){
                final JSONObject request = new JSONObject(response);
                final JSONArray items = request.getJSONArray("items");
                final JSONObject first = items.getJSONObject(0);
                final int currentVersionApp = first.getInt("current_app_version");

                if (versionApp != currentVersionApp) {
                    sendBroadcast(new Intent(ACTION_SHOW_FORCE_UPDATE));
                }else{
                    stopSelf();
                }}else {
                    stopSelf();
                }
            } catch (final JSONException pE) {
                //ignore
            }
        }
    }

    private boolean isOnline() {
        final ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null) {
            return false;
        }
        final NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo!= null && networkInfo.isConnectedOrConnecting();
    }
}
