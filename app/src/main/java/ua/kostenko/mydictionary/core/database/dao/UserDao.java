package ua.kostenko.mydictionary.core.database.dao;

import ua.kostenko.mydictionary.core.database.domain.User;

public interface UserDao {
    User loadUser(Long id);

    User updateUser(User currentUser);
}
