package ua.kostenko.mydictionary.core.database.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "dictionary")
public class Dictionary {
    @DatabaseField(id = true, canBeNull = false, dataType = DataType.STRING)
    private String id;
    @DatabaseField(canBeNull = true)
    private List<String> words;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
