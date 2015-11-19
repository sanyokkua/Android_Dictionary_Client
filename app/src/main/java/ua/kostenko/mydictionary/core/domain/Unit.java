package ua.kostenko.mydictionary.core.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "unit")
public class Unit {
    @DatabaseField(id = true)
    private String unit;
    @DatabaseField
    private String translations;
    @DatabaseField
    private long count;

    public Unit() {
    }

    public Unit(String unit, String translations, long count) {
        this.unit = unit;
        this.translations = translations;
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getTranslations() {
        return translations;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
