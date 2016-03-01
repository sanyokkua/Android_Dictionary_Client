package ua.kostenko.mydictionary.core.parsing;

import android.support.annotation.NonNull;

public interface Parser<T> {
    T parse(@NonNull final String text); // TODO: I think it's return type should be changed to list of words (strings)
}
