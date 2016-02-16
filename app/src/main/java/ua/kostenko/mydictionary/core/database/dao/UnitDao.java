package ua.kostenko.mydictionary.core.database.dao;

import java.util.List;

import ua.kostenko.mydictionary.core.database.domain.Unit;

public interface UnitDao {
    boolean saveWord(Unit unit);

    boolean removeWord(Unit unit);

    Unit findByTranslation(String translation);

    List<Unit> findAll();
}
