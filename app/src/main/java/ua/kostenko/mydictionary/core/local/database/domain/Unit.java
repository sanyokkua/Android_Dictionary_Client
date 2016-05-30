package ua.kostenko.mydictionary.core.local.database.domain;

import android.support.annotation.NonNull;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "units")
public class Unit {
    public static final String FIELD_SOURCE = "source";
    public static final String FIELD_TRANSLATION = "translation";
    public static final String FIELD_TRANSLATION_ADDITIONAL = "translationAdditional";
    public static final String FIELD_USER_TRANSLATION = "userTranslation";
    public static final String FIELD_COUNTER = "counter";
    @DatabaseField(id = true, canBeNull = false, dataType = DataType.STRING, columnName = FIELD_SOURCE)
    private String source;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = FIELD_TRANSLATION)
    private String translations;
    @DatabaseField(canBeNull = true, dataType = DataType.STRING, columnName = FIELD_TRANSLATION_ADDITIONAL)
    private String translationsAdditional;
    @DatabaseField(dataType = DataType.STRING, columnName = FIELD_USER_TRANSLATION)
    private String userTranslation;
    @DatabaseField(dataType = DataType.LONG, columnName = FIELD_COUNTER)
    private long counter;

    public Unit() {
        this.counter = 1;
    }

    public void incrementCounterTo(long value) {
        counter += value;
    }

    public void incrementCounter() {
        counter++;
    }

    public Unit(@NonNull final String source, @NonNull final String translations) {
        this();
        this.source = source;
        this.translations = translations;
    }

    public Unit(@NonNull final String source, @NonNull final String translations, final long counter) {
        this.source = source;
        this.translations = translations;
        this.counter = counter;
    }

    private Unit(@NonNull final String source, @NonNull final String translations, @NonNull final String userTranslation, final long counter) {
        this(source, translations, counter);
        this.userTranslation = userTranslation;
    }

    public Unit(@NonNull final String source, @NonNull final String translations, @NonNull final String translationsAdditional,
                @NonNull final String userTranslation, final long counter) {
        this(source, translations, userTranslation, counter);
        this.translationsAdditional = translationsAdditional;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(final long counter) {
        this.counter = counter;
    }

    public String getTranslations() {
        return translations;
    }

    public void setTranslations(@NonNull final String translations) {
        this.translations = translations;
    }

    public String getUserTranslation() {
        return userTranslation;
    }

    public void setUserTranslation(@NonNull final String userTranslation) {
        this.userTranslation = userTranslation;
    }

    public String getTranslationsAdditional() {
        return translationsAdditional;
    }

    public void setTranslationsAdditional(String translationsAdditional) {
        this.translationsAdditional = translationsAdditional;
    }

    public String getSource() {
        return source;
    }

    public void setSource(@NonNull final String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "source='" + source + '\'' +
                ", translations='" + translations + '\'' +
                ", translationsAdditional='" + translationsAdditional + '\'' +
                ", userTranslation='" + userTranslation + '\'' +
                ", counter=" + counter +
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
        Unit unit = (Unit) anotherObject;
        return source.equals(unit.source) &&
                !(translations != null ? !translations.equals(unit.translations) : unit.translations != null);
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + (translations != null ? translations.hashCode() : 0);
        return result;
    }
}
