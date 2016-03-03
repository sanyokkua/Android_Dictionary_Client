package ua.kostenko.mydictionary.core.local.database.domain;

import android.support.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "bookmarks")
public class Bookmark {
    public static final String FIELD_NAME = "name";
    public static final String FIELD_URL = "url";
    public static final String FIELD_PICTURE = "picture";

    @DatabaseField(canBeNull = false, id = true, columnName = FIELD_URL)
    private String url;
    @DatabaseField(canBeNull = false, columnName = FIELD_NAME)
    private String name;
    @DatabaseField(canBeNull = true, columnName = FIELD_PICTURE)
    private String picture;

    public Bookmark() {
    }

    public Bookmark(@NonNull final String url, @NonNull final String name, @NonNull final String picture) {
        this.url = url;
        this.name = name;
        this.picture = picture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull final String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull final String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(@NonNull final String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object anotherObject) {
        if (this == anotherObject) {
            return true;
        }
        if (anotherObject == null || getClass() != anotherObject.getClass()) {
            return false;
        }
        Bookmark bookmark = (Bookmark) anotherObject;
        return url.equals(bookmark.url);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }
}
