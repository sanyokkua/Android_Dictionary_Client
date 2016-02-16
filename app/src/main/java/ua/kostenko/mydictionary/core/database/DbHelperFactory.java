package ua.kostenko.mydictionary.core.database;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DbHelperFactory {
    private static DatabaseHelper dbHelper;

    public static DatabaseHelper getDatabaseHelper() {
        return dbHelper;
    }

    public static void setDatabaseHelper(Context context) {
        dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        dbHelper = null;
    }
}
