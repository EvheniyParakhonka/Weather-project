package piftik.github.com.weatherproject.models.dbHelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import piftik.github.com.weatherproject.models.TableQueryGenerator;
import piftik.github.com.weatherproject.models.Weather;

final class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "weather";

    private static final int DATABASE_VERSION = 1;

    private static DBHelper instance;

    static DBHelper getInstance(final Context pContext) {
        if (instance == null) {
            instance = new DBHelper(pContext);
        }
        return instance;
    }

    private DBHelper(final Context pContext) {
        super(pContext, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase pSQLiteDatabase) {
        pSQLiteDatabase.execSQL(TableQueryGenerator.getTableCreateQuery(Weather.class));
    }

    @Override
    public void onUpgrade(final SQLiteDatabase pDatabase, final int pOldVersion, final int pNewVersion) {

    }
}
