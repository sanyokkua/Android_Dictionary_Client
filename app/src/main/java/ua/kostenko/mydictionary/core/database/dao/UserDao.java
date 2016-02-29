package ua.kostenko.mydictionary.core.database.dao;

import ua.kostenko.mydictionary.core.database.domain.User;

public interface UserDao {
    User loadUser(String login);

    boolean saveUser(User user);

    User login(String email, String password);
}
