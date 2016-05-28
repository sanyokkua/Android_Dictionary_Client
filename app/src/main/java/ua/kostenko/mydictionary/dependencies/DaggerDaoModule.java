package ua.kostenko.mydictionary.dependencies;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.kostenko.mydictionary.core.local.database.DatabaseHelper;
import ua.kostenko.mydictionary.core.local.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.local.database.dao.UserDao;

@Module
public class DaggerDaoModule {
    private static final String TAG = DaggerDaoModule.class.getSimpleName();

    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper(Context context) {
        return OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    @Provides
    @Singleton
    public UnitDao provideUnitDao(DatabaseHelper dbHelper) {
        UnitDao dao = null;
        try {
            dao = dbHelper.getUnitDao();
        } catch (SQLException e) {
            Log.e(TAG, "Error with getting UnitDao in provide method", e);
        }
        return dao;
    }

    @Provides
    @Singleton
    public UserDao provideUserDao(DatabaseHelper dbHelper) {
        UserDao dao = null;
        try {
            dao = dbHelper.getUserDao();
        } catch (Exception e) {
            Log.e(TAG, "Error with getting UserDao in provide method", e);
        }
        return dao;
    }
}
