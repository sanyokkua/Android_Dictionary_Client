package ua.kostenko.mydictionary.core.local.parsing;

import android.support.annotation.NonNull;

import java.util.List;

public interface Parser<T> {
    List<T> parse(@NonNull final String text);
}
