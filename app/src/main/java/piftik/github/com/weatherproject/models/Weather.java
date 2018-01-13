package piftik.github.com.weatherproject.models;

import android.content.ContentValues;
import android.database.Cursor;

import piftik.github.com.weatherproject.models.annotations.Table;
import piftik.github.com.weatherproject.models.annotations.types.DBDouble;
import piftik.github.com.weatherproject.models.annotations.types.DBIntegerPrimaryKey;
import piftik.github.com.weatherproject.models.annotations.types.DBString;
import piftik.github.com.weatherproject.utils.CursorUtils;

@Table(name = "weather")
public class Weather implements TableClass {

    @DBIntegerPrimaryKey
    private static final String CITY_ID = "_id";

    @DBString
    private static final String DATE = "date";

    @DBString
    private static final String WEATHER_MAIN = "weatherMain";

    @DBDouble
    private static final String TEMP_MIN = "tempMin";

    @DBDouble
    private static final String TEMP_MAX = "tempMax";

    @DBString
    private static final String COUNTRY = "country";

    @DBString
    private static final String CITY = "city";

    private long mId;
    private String mDate;
    private String mWeatherMain;
    private double mTempMin;
    private double mTempMax;
    private String mCountry;
    private String mCity;

    public Weather() {
    }

    public Weather(final long p_id, final String pDate, final String pWeatherMain, final double pTempMin, final double pTempMax, final String pCountry, final String pCity) {
        mId = p_id;
        mDate = pDate;
        mWeatherMain = pWeatherMain;
        mTempMin = pTempMin;
        mTempMax = pTempMax;
        mCountry = pCountry;
        mCity = pCity;
    }


    @Override
    public Weather convertFromCursor(final Cursor cursor) {

        mId = CursorUtils.getLong(cursor, CITY_ID);
        mDate = CursorUtils.getString(cursor, DATE);
        mWeatherMain = CursorUtils.getString(cursor, WEATHER_MAIN);
        mTempMax = CursorUtils.getDouble(cursor, TEMP_MAX);
        mTempMin = CursorUtils.getDouble(cursor, TEMP_MIN);
        mCountry = CursorUtils.getString(cursor, COUNTRY);
        mCity = CursorUtils.getString(cursor, CITY);
        return this;
    }

    @Override
    public ContentValues convertToContentValues() {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(CITY_ID, mId);
        contentValues.put(DATE, mDate);
        contentValues.put(WEATHER_MAIN, mWeatherMain);
        contentValues.put(TEMP_MAX, mTempMax);
        contentValues.put(TEMP_MIN, mTempMin);
        contentValues.put(COUNTRY, mCountry);
        contentValues.put(CITY, mCity);
        return contentValues;
    }

    public long getId() {
        return mId;
    }

    public void setId(final long pId) {
        mId = pId;
    }

    public String getDate() {
        return mDate;
    }

    public String getWeatherMain() {
        return mWeatherMain;
    }

    public double getTempMin() {
        return mTempMin;
    }

    public double getTempMax() {
        return mTempMax;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getCity() {
        return mCity;
    }
}
