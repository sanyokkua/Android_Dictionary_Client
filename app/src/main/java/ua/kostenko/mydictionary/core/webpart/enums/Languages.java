package ua.kostenko.mydictionary.core.webpart.enums;

import android.support.annotation.NonNull;

public enum Languages {
    ENGLISH("en"), RUSSIAN("ru");

    private String code;

    Languages(@NonNull String code) {
        this.code = code;
    }

    public String getLangCode() {
        return this.code;
    }
}
