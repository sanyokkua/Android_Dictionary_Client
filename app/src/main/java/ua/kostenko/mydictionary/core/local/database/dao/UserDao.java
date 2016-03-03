package ua.kostenko.mydictionary.core.local.database.dao;

import android.support.annotation.NonNull;

import ua.kostenko.mydictionary.core.local.database.domain.User;

public interface UserDao {
    User loadUser(@NonNull final String login);

    boolean saveUser(@NonNull final User user);
}
