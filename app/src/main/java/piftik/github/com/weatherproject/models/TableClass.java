package piftik.github.com.weatherproject.models;

import android.content.ContentValues;
import android.database.Cursor;

import piftik.github.com.weatherproject.models.annotations.types.DBString;

public interface TableClass {

    @DBString
    String NAME = "name";

     TableClass convertFromCursor(Cursor cursor);

    ContentValues convertToContentValues();
}
