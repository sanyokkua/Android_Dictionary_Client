package ua.kostenko.mydictionary.core.database.dao.implementation;

import android.util.Log;

import com.google.common.base.Preconditions;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import ua.kostenko.mydictionary.core.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.database.domain.Unit;

public class UnitDaoImpl extends BaseDaoImpl<Unit, String> implements UnitDao {
    private static final String TAG = UnitDaoImpl.class.getSimpleName();

    protected UnitDaoImpl(ConnectionSource connectionSource, Class<Unit> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public boolean saveWord(final Unit unit) {
        Preconditions.checkNotNull(unit);
        Unit temporaryUnit = findBySource(unit.getSource());
        return temporaryUnit != null ? updateUnit(temporaryUnit) : createUnit(temporaryUnit);
    }

    private boolean createUnit(final Unit newUnit) {
        boolean resultOfOperation;
        try {
            int numbOfRowsUpdated = create(newUnit);
            resultOfOperation = DaoUtils.validateCorrectNumberOfRows(numbOfRowsUpdated);
        } catch (SQLException e) {
            Log.e(TAG, "Error while creating unit", e);
            resultOfOperation = false;
        }
        return resultOfOperation;
    }


    private boolean updateUnit(final Unit existingUnit) {
        boolean resultOfOperation;
        try {
            int numbOfRowsUpdated = update(existingUnit);
            resultOfOperation = DaoUtils.validateCorrectNumberOfRows(numbOfRowsUpdated);
        } catch (SQLException e) {
            Log.e(TAG, "Error while updating unit", e);
            resultOfOperation = false;
        }
        return resultOfOperation;
    }

    @Override
    public boolean removeUnit(Unit unit) {
        Preconditions.checkNotNull(unit);
        boolean resultOfOperation;
        try {
            final int numbOfRowsUpdated = delete(unit);
            resultOfOperation = DaoUtils.validateCorrectNumberOfRows(numbOfRowsUpdated);
        } catch (SQLException e) {
            resultOfOperation = false;
            Log.e(TAG, "Removing of " + unit.toString() + " failed", e);
        }
        return resultOfOperation;
    }

    @Override
    public Unit findBySource(String source) {
        Preconditions.checkNotNull(source);
        return findByQuery(Unit.FIELD_SOURCE, source);
    }

    private Unit findByQuery(String field, String value) {
        QueryBuilder<Unit, String> queryBuilder = queryBuilder();
        Unit resultOfQuery = null;
        try {
            queryBuilder.where().eq(field, value);
            resultOfQuery = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            Log.e(TAG, "Error with query", e);
        }
        return resultOfQuery;
    }

    @Override
    public Unit findByTranslation(String translation) {
        Preconditions.checkNotNull(translation);
        return findByQuery(Unit.FIELD_TRANSLATION, translation);
    }

    @Override
    public List<Unit> findAll() {
        List<Unit> unitList = null;
        try {
            unitList = Collections.unmodifiableList(queryForAll());
        } catch (SQLException e) {
            Log.e(TAG, "Error with queryForAll", e);
        }
        return unitList;
    }

    @Override
    public boolean synchronize() { //TODO: add implementation
        throw new RuntimeException("Not Implemented yet");
    }
}
