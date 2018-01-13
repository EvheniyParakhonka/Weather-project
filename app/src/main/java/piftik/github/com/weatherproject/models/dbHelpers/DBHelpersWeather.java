package piftik.github.com.weatherproject.models.dbHelpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import piftik.github.com.weatherproject.models.TableQueryGenerator;
import piftik.github.com.weatherproject.models.Weather;
import piftik.github.com.weatherproject.utils.ContextHolder;

public class DBHelpersWeather implements IDBHelperForModel<Weather> {
    private final DBHelper dbHelper;

    public DBHelpersWeather() {
        this.dbHelper = DBHelper.getInstance(ContextHolder.getInstance().getContext());
    }

    @Override
    public long add(final Weather pWeather) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        final ContentValues values = pWeather.convertToContentValues();
        final long id;
        try {
            db.beginTransaction();
            id = db.insertWithOnConflict(TableQueryGenerator.getTableName(Weather.class), null, values, SQLiteDatabase.CONFLICT_REPLACE);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return id;
    }

    @Override
    public Weather get(final long pId) {
        final String selectQuery = "SELECT * FROM " + TableQueryGenerator.getTableName(Weather.class) + " WHERE _id = " + pId;
        final SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                return new Weather().convertFromCursor(cursor);
            } else {
                return null;
            }
        } catch (final Exception pE) {
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    @Override
    public int delete(final long id) {
        return 0;
    }

}
