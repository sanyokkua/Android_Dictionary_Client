package ua.kostenko.mydictionary.core.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import ua.kostenko.mydictionary.core.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.database.domain.Unit;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DB_NAME = "units.db";
    private static final int DB_VERSION = 1;

    private UnitDao unitDao;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Unit.class);
        } catch (SQLException e) {
            Log.e(TAG, "Error with table creation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Unit.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "Error with table upgrade", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();
        unitDao = null;
    }

    public UnitDao getUnitDao() {
        if (unitDao == null) {
            //TODO: create dao
            throw new RuntimeException("Not implemented");
        }
        return unitDao;
    }
}
