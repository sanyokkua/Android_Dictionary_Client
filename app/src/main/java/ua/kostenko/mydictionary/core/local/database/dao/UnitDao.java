package ua.kostenko.mydictionary.core.local.database.dao;

import android.support.annotation.NonNull;

import java.util.List;

import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.ui.iterfaces.OnUpdate;

public interface UnitDao {
    boolean saveUnit(@NonNull final Unit unit);

    boolean removeUnit(@NonNull final Unit unit);

    Unit findBySource(@NonNull final String source);

    Unit findByTranslation(@NonNull final String translation);

    List<Unit> findAll();

    void setOnUpdate(@NonNull OnUpdate onUpdate);

}
