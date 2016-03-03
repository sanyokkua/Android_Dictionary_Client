package ua.kostenko.mydictionary.core.webpart.services;

import android.support.annotation.NonNull;

import ua.kostenko.mydictionary.core.webpart.enums.Languages;

public interface TranslateService {
    String translate(@NonNull final Languages from, @NonNull final Languages to, @NonNull final String unit);
}
