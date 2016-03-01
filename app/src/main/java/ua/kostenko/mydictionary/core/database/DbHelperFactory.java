package ua.kostenko.mydictionary.core.database;

import android.content.Context;
import android.support.annotation.NonNull;

import com.j256.ormlite.android.apptools.OpenHelperManager;

public class DbHelperFactory {
    private static DatabaseHelper dbHelper;

    @NonNull
    public static DatabaseHelper getDatabaseHelper() {
        return dbHelper;
    }

    public static void setDatabaseHelper(@NonNull final Context context) {
        dbHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }

    public static void releaseHelper() {
        OpenHelperManager.releaseHelper();
        dbHelper = null;
    }
}
