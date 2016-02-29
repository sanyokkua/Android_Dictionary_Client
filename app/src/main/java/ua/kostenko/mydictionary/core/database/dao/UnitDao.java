package ua.kostenko.mydictionary.core.database.dao;

import java.util.List;

import ua.kostenko.mydictionary.core.database.domain.Unit;

public interface UnitDao {
    boolean saveWord(Unit unit);

    boolean removeUnit(Unit unit);

    Unit findBySource(String source);

    Unit findByTranslation(String translation);

    List<Unit> findAll();

    boolean synchronize();
}
