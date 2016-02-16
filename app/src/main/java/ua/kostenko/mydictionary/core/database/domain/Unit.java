package ua.kostenko.mydictionary.core.database.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "unit")
public class Unit {
    @DatabaseField(id = true, canBeNull = false, dataType = DataType.STRING)
    private String unit;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String translations;
    @DatabaseField(dataType = DataType.STRING)
    private String userTranslation;
    @DatabaseField(dataType = DataType.LONG)
    private long counter;

    public Unit() {
        this.counter = 0;
    }

    public Unit(String unit, String translations) {
        this();
        this.unit = unit;
        this.translations = translations;
    }

    public Unit(String unit, String translations, long counter) {
        this.unit = unit;
        this.translations = translations;
        this.counter = counter;
    }

    public Unit(String unit, String translations, String userTranslation, long counter) {
        this(unit, translations, counter);
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
