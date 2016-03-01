package ua.kostenko.mydictionary.core.database.dao;

import android.support.annotation.NonNull;

import java.util.List;

import ua.kostenko.mydictionary.core.database.domain.Unit;

public interface UnitDao {
    boolean saveUnit(@NonNull final Unit unit);

    boolean removeUnit(@NonNull final Unit unit);

    Unit findBySource(@NonNull final String source);

    Unit findByTranslation(@NonNull final String translation);

    List<Unit> findAll();

}
