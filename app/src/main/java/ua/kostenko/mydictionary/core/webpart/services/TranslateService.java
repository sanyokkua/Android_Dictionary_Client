package ua.kostenko.mydictionary.core.webpart.services;

import android.support.annotation.NonNull;

import ua.kostenko.mydictionary.core.webpart.enums.Languages;

public interface TranslateService<T> {
    void translate(@NonNull final Languages from, @NonNull final Languages to, @NonNull final String text, OnResultCallback<T> onResultCallback);
    T translateSync(@NonNull final Languages from, @NonNull final Languages to, @NonNull final String text);
}
