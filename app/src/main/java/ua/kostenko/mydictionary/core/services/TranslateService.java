package ua.kostenko.mydictionary.core.services;

import android.support.annotation.NonNull;

import ua.kostenko.mydictionary.core.enums.Languages;

public interface TranslateService {
    String translate(@NonNull final Languages from, @NonNull final Languages to, @NonNull final String unit);
}
