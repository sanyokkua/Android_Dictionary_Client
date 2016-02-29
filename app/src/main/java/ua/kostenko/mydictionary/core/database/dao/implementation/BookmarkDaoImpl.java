package ua.kostenko.mydictionary.core.database.dao.implementation;

import android.util.Log;

import com.google.common.base.Preconditions;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import ua.kostenko.mydictionary.core.database.dao.BookmarkDao;
import ua.kostenko.mydictionary.core.database.domain.Bookmark;

public class BookmarkDaoImpl extends BaseDaoImpl<Bookmark, String> implements BookmarkDao {
    private static final String TAG = BookmarkDaoImpl.class.getSimpleName();

    protected BookmarkDaoImpl(Class<Bookmark> dataClass) throws SQLException {
        super(dataClass);
    }

    protected BookmarkDaoImpl(ConnectionSource connectionSource, Class<Bookmark> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    protected BookmarkDaoImpl(ConnectionSource connectionSource, DatabaseTableConfig<Bookmark> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    public boolean save(Bookmark bookmark) {
        Preconditions.checkNotNull(bookmark);
        Bookmark temporaryBookmark = findByQuery(Bookmark.FIELD_URL, bookmark.getUrl());
        return temporaryBookmark != null ? updateBookmark(temporaryBookmark) : createBookmark(temporaryBookmark);
    }

    private Bookmark findByQuery(String field, String value) {
        QueryBuilder<Bookmark, String> queryBuilder = queryBuilder();
        Bookmark resultOfQuery = null;
        try {
            queryBuilder.where().eq(field, value);
            resultOfQuery = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            Log.e(TAG, "Error with query", e);
        }
        return resultOfQuery;
    }

    private boolean updateBookmark(final Bookmark existingBookmark) {
        boolean resultOfOperation;
        try {
            int numbOfRowsUpdated = update(existingBookmark);
            resultOfOperation = DaoUtils.validateCorrectNumberOfRows(numbOfRowsUpdated);
        } catch (SQLException e) {
            Log.e(TAG, "Error while updating unit", e);
            resultOfOperation = false;
        }
        return resultOfOperation;
    }

    private boolean createBookmark(final Bookmark newBookmark) {
        boolean resultOfOperation;
        try {
            int numbOfRowsUpdated = create(newBookmark);
            resultOfOperation = DaoUtils.validateCorrectNumberOfRows(numbOfRowsUpdated);
        } catch (SQLException e) {
            Log.e(TAG, "Error while creating unit", e);
            resultOfOperation = false;
        }
        return resultOfOperation;
    }

    @Override
    public boolean remove(Bookmark bookmark) {
        Preconditions.checkNotNull(bookmark);
        boolean resultOfOperation;
        try {
            final int numbOfRowsUpdated = delete(bookmark);
            resultOfOperation = DaoUtils.validateCorrectNumberOfRows(numbOfRowsUpdated);
        } catch (SQLException e) {
            resultOfOperation = false;
            Log.e(TAG, "Removing of " + bookmark.toString() + " failed", e);
        }
        return resultOfOperation;
    }

    @Override
    public List<Bookmark> findAll() {
        List<Bookmark> unitList = null;
        try {
            unitList = Collections.unmodifiableList(queryForAll());
        } catch (SQLException e) {
            Log.e(TAG, "Error with queryForAll", e);
        }
        return unitList;
    }
}
