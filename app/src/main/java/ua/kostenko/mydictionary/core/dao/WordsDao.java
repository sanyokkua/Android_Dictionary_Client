package ua.kostenko.mydictionary.core.dao;

import java.util.List;

import ua.kostenko.mydictionary.core.domain.Unit;

public interface WordsDao {
    boolean saveWord(Unit unit);

    boolean removeWord(Unit unit);

    Unit findByTranslation(String translation);

    List<Unit> findAll();
}
