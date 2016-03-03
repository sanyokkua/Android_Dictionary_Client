package ua.kostenko.mydictionary.core.webpart.enums;

import android.support.annotation.NonNull;

public enum Action {
    CREATE("create"), DELETE("delete"), UPDATE("update");

    private String action;

    Action(@NonNull String action) {
        this.action = action;
    }

    public String getName() {
        return this.action;
    }
}
