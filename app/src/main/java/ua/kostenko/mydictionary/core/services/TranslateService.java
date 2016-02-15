package ua.kostenko.mydictionary.core.services;

import ua.kostenko.mydictionary.core.enums.Languages;

public interface TranslateService {
    String translate(Languages from, Languages to, String unit);
}
