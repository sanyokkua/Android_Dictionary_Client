package ua.kostenko.mydictionary.core.database.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "source")
public class Unit {
    public static final String FIELD_SOURCE = "source";
    public static final String FIELD_TRANSLATION = "translation";
    public static final String FIELD_USER_TRANSLATION = "userTranslation";
    public static final String FIELD_COUNTER = "counter";
    @DatabaseField(id = true, canBeNull = false, dataType = DataType.STRING, columnName = FIELD_SOURCE)
    private String source;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = FIELD_TRANSLATION)
    private String translations;
    @DatabaseField(dataType = DataType.STRING, columnName = FIELD_USER_TRANSLATION)
    private String userTranslation;
    @DatabaseField(dataType = DataType.LONG, columnName = FIELD_COUNTER)
    private long counter;

    public Unit() {
        this.counter = 0;
    }

    public Unit(String source, String translations) {
        this();
        this.source = source;
        this.translations = translations;
    }

    public Unit(String source, String translations, long counter) {
        this.source = source;
        this.translations = translations;
        this.counter = counter;
    }

    public Unit(String source, String translations, String userTranslation, long counter) {
        this(source, translations, counter);
        this.userTranslation = userTranslation;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
    }

    public String getTranslations() {
        return translations;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
    }

    public String getUserTranslation() {
        return userTranslation;
    }

    public void setUserTranslation(String userTranslation) {
        this.userTranslation = userTranslation;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "source='" + source + '\'' +
                ", translations='" + translations + '\'' +
                ", userTranslation='" + userTranslation + '\'' +
                ", counter=" + counter +
                '}';
    }
}
