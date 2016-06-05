package ua.kostenko.mydictionary.core.local.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import ua.kostenko.mydictionary.core.local.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.local.database.dao.UserDao;
import ua.kostenko.mydictionary.core.local.database.dao.implementation.UnitDaoImpl;
import ua.kostenko.mydictionary.core.local.database.dao.implementation.UserDaoImpl;
import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.core.local.database.domain.User;
import ua.kostenko.mydictionary.ui.iterfaces.OnUpdate;

public final class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();
    private static final String DB_NAME = "app.db";
    private static final int DB_VERSION = 1;
    private UnitDao unitDao;
    private UserDao userDao;
    private List<Unit> units;
    private boolean isNeedToUpdate;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(@NonNull final SQLiteDatabase database, @NonNull final ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Unit.class);
        } catch (SQLException e) {
            Log.e(TAG, "Error with table creation", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(@NonNull final SQLiteDatabase database, @NonNull final ConnectionSource connectionSource,
                          final int oldVersion, final int newVersion) {
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
        userDao = null;
    }

    @NonNull
    public UnitDao getUnitDao() throws SQLException {
        if (unitDao == null) {
            unitDao = new UnitDaoImpl(getConnectionSource(), Unit.class);
            isNeedToUpdate = true;
            unitDao.setOnUpdate(new OnUpdate() {
                @Override public void update() {
                    if (isNeedToUpdate) {
                        units = unitDao.findAll();
                    }
                }
            });
        }
        return unitDao;
    }

    @NonNull
    public UserDao getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = new UserDaoImpl(getConnectionSource(), User.class);
        }
        return userDao;
    }

    public List<Unit> getUnits() {
        if (units == null) {
            try {
                units = getUnitDao().findAll();
            } catch (SQLException e) {
                Log.e(TAG, "Error in getting unit dao", e);
                throw new RuntimeException(e);
            }
        }
        return Collections.unmodifiableList(units);
    }

    public boolean isNeedToUpdate() {
        return isNeedToUpdate;
    }

    public void setNeedToUpdate(boolean needToUpdate) {
        isNeedToUpdate = needToUpdate;
    }
}
