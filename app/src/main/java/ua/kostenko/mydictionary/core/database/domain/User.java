package ua.kostenko.mydictionary.core.database.domain;

import android.support.annotation.NonNull;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class User {
    public static final String FIELD_LOGIN = "login";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_DICTIONARY_ID = "dictionaryId";
    public static final String FIELD_DICTIONARY_REVISION = "dictionaryRevision";

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = FIELD_LOGIN, id = true)
    private String login;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = FIELD_EMAIL)
    private String email;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = FIELD_PASSWORD)
    private String password;
    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = FIELD_DICTIONARY_ID)
    private String dictionaryId;
    @DatabaseField(canBeNull = false, dataType = DataType.LONG, columnName = FIELD_DICTIONARY_REVISION)
    private Long dictionaryRevision;

    public User() {
    }

    public User(@NonNull final String login, @NonNull final String email, @NonNull final String password,
                @NonNull final String dictionaryId, @NonNull final Long dictionaryRevision) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.dictionaryId = dictionaryId;
        this.dictionaryRevision = dictionaryRevision;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull final String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull final String password) {
        this.password = password;
    }

    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(@NonNull final String dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public Long getDictionaryRevision() {
        return dictionaryRevision;
    }

    public void setDictionaryRevision(@NonNull final Long dictionaryRevision) {
        this.dictionaryRevision = dictionaryRevision;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", dictionaryRevision=" + dictionaryRevision +
                '}';
    }
}
