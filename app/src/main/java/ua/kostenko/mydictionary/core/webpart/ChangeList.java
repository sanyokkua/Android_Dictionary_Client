package ua.kostenko.mydictionary.core.webpart;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import ua.kostenko.mydictionary.core.local.database.domain.Unit;
import ua.kostenko.mydictionary.core.webpart.enums.Action;

public final class ChangeList {
    private Map<Unit, Action> changes;

    public ChangeList() {
        changes = new LinkedHashMap<>();
    }

    public void addChange(@NonNull final Unit unit, @NonNull final Action action) {
        changes.put(unit, action);
    }

    public void removeChangeIfExists(@NonNull final Unit unit) {
        Object unitFromMap = changes.get(unit);
        if (unitFromMap != null) {
            changes.remove(unit);
        }
    }

    public Map<Unit, Action> getAllChanges() {
        return Collections.unmodifiableMap(changes);
    }
}
