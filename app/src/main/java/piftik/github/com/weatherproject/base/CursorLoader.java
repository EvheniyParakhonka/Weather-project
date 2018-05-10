package piftik.github.com.weatherproject.base;

import android.content.Context;
import android.database.Cursor;

import piftik.github.com.weatherproject.App;
import piftik.github.com.weatherproject.models.Weather;
import piftik.github.com.weatherproject.models.dbHelpers.DBHelpersWeather;
import piftik.github.com.weatherproject.utils.ContextHolder;

public class CursorLoader extends android.support.v4.content.CursorLoader {
    private final DBHelpersWeather dbHelperWallet;

    public CursorLoader(final Context context) {
        super(context);
        this.dbHelperWallet = ((DBHelpersWeather) ((App) ContextHolder.getInstance().getContext()).getDbHelper(Weather.class));
    }

    @Override
    public Cursor loadInBackground() {
        return dbHelperWallet.get();
    }
}
