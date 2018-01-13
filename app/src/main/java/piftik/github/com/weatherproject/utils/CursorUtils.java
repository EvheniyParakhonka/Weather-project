package piftik.github.com.weatherproject.utils;

import android.database.Cursor;

public final class CursorUtils {
    public static double getDouble(final Cursor cursor, final String key) {
        return cursor.getDouble(cursor.getColumnIndex(key));
    }

    public static String getString(final Cursor cursor, final String key) {
        return cursor.getString(cursor.getColumnIndex(key));
    }

    public static Long getLong(final Cursor cursor, final String key) {
        return cursor.getLong(cursor.getColumnIndex(key));
    }

    public static Integer getInteger(final Cursor cursor, final String key) {
        return cursor.getInt(cursor.getColumnIndex(key));
    }
}
