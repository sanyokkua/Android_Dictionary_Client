package ua.kostenko.mydictionary.core.database.dao;

import java.util.List;

import ua.kostenko.mydictionary.core.database.domain.Bookmark;

public interface BookmarkDao {
    boolean save(Bookmark bookmark);

    boolean remove(Bookmark bookmark);

    List<Bookmark> findAll();
}
