package piftik.github.com.weatherproject;

import android.app.Application;
import android.support.annotation.Nullable;

import com.facebook.stetho.Stetho;

import piftik.github.com.weatherproject.models.DBHelpersManager;
import piftik.github.com.weatherproject.models.TableClass;
import piftik.github.com.weatherproject.models.dbHelpers.IDBHelperForModel;
import piftik.github.com.weatherproject.utils.ContextHolder;


public class App extends Application {
    private DBHelpersManager dbHelpersManager;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelpersManager = new DBHelpersManager();
        ContextHolder.getInstance().setContext(this);
//        For lock are created datebase
        Stetho.initializeWithDefaults(this);
    }


    @Nullable
    public IDBHelperForModel getDbHelper(final Class<? extends TableClass> clazz) {
        return dbHelpersManager.getDBHelper(clazz);
    }
}
