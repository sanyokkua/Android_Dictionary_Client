package ua.kostenko.mydictionary.core.database.domain;

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

    public Bookmark(String url, String name, String picture) {
        this.url = url;
        this.name = name;
        this.picture = picture;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
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
}
