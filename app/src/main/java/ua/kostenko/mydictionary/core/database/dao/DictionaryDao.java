package ua.kostenko.mydictionary.core.database.dao;

import ua.kostenko.mydictionary.core.database.domain.Dictionary;
import ua.kostenko.mydictionary.core.database.domain.Unit;

public interface DictionaryDao {
    Dictionary loadDictionary(String id);

    void addUnit(Dictionary dictionary, Unit unit);

    void removeUnit(Dictionary dictionary, Unit unit);
}
