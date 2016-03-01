package ua.kostenko.mydictionary.core.database.dao;

import android.support.annotation.NonNull;

import ua.kostenko.mydictionary.core.database.domain.User;

public interface UserDao {
    User loadUser(@NonNull final String login);

    boolean saveUser(@NonNull final User user);

    User login(@NonNull final String email, @NonNull final String password);
}
