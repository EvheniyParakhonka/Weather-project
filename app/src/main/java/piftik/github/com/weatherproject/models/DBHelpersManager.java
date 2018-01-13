package piftik.github.com.weatherproject.models;

import piftik.github.com.weatherproject.models.dbHelpers.DBHelpersWeather;
import piftik.github.com.weatherproject.models.dbHelpers.IDBHelperForModel;

public class DBHelpersManager {
    private DBHelpersWeather dbHelperWeather;

    public IDBHelperForModel getDBHelper(final Class<? extends TableClass> clazz) {

        if (dbHelperWeather == null) {
            dbHelperWeather = new DBHelpersWeather();
        }
        return dbHelperWeather;
    }
}
