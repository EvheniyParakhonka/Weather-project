package piftik.github.com.weatherproject.utils;

import android.annotation.SuppressLint;
import android.content.Context;

public final class ContextHolder {
    @SuppressLint("StaticFieldLeak")
    private static ContextHolder instance;

    private Context mContext;

    private ContextHolder() {
    }

    public static ContextHolder getInstance() {
        if (instance == null) {
            instance = new ContextHolder();
        }
        return instance;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(final Context pContext) {
        mContext = pContext;
    }
}
