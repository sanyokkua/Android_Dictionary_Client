package ua.kostenko.mydictionary.core.database.dao.implementation;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ua.kostenko.mydictionary.core.database.dao.UserDao;
import ua.kostenko.mydictionary.core.database.domain.User;

public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao {

    protected UserDaoImpl(ConnectionSource connectionSource, Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public User loadUser(Long id) {
        return null;
    }

    @Override
    public User updateUser(User currentUser) {
        return null;
    }
}
