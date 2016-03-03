package ua.kostenko.mydictionary.core.local.database.dao;

import android.support.annotation.NonNull;

import java.util.List;

import ua.kostenko.mydictionary.core.local.database.domain.Bookmark;

public interface BookmarkDao {
    boolean save(@NonNull final Bookmark bookmark);

    boolean remove(@NonNull final Bookmark bookmark);

    List<Bookmark> findAll();
}
