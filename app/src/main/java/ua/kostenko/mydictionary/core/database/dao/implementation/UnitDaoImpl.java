package ua.kostenko.mydictionary.core.database.dao.implementation;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

import ua.kostenko.mydictionary.core.database.dao.UnitDao;
import ua.kostenko.mydictionary.core.database.domain.Unit;

public class UnitDaoImpl extends BaseDaoImpl<Unit, String> implements UnitDao {

    protected UnitDaoImpl(ConnectionSource connectionSource, Class<Unit> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public boolean saveWord(Unit unit) {
        return false;
    }

    @Override
    public boolean removeWord(Unit unit) {
        return false;
    }

    @Override
    public Unit findByTranslation(String translation) {
        return null;
    }

    @Override
    public List<Unit> findAll() {
        return null;
    }
}
