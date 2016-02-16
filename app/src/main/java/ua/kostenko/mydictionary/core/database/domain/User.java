package ua.kostenko.mydictionary.core.database.domain;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class User {
    @DatabaseField(id = true, canBeNull = false, dataType = DataType.LONG)
    private Long id;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String login;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String email;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String password;
    @DatabaseField(canBeNull = true, dataType = DataType.STRING)
    private String dictionaryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }
}
