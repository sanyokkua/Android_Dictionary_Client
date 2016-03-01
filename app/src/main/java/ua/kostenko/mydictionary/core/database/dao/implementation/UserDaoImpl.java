package ua.kostenko.mydictionary.core.database.dao.implementation;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.common.base.Preconditions;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import ua.kostenko.mydictionary.core.database.dao.UserDao;
import ua.kostenko.mydictionary.core.database.domain.User;

public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao {
    private static final String TAG = UserDaoImpl.class.getSimpleName();

    protected UserDaoImpl(ConnectionSource connectionSource, Class<User> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public User loadUser(@NonNull final String login) {
        Preconditions.checkNotNull(login);
        QueryBuilder<User, Integer> queryBuilder = queryBuilder();
        User userFromDb = null;
        try {
            queryBuilder.where().eq(User.FIELD_LOGIN, login);
            userFromDb = queryBuilder.queryForFirst();
        } catch (SQLException e) {
            Log.e(TAG, "Error while query for loadUser", e);
        }
        return userFromDb;
    }

    @Override
    public boolean saveUser(@NonNull final User user) {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(user.getLogin());
        User loadedUser = loadUser(user.getLogin());
        if (loadedUser == null) {
            createUser(user);
        } else {
            updateUser(user);
        }
        return false;
    }

    private boolean createUser(@NonNull final User user) {
        boolean resultOfOperation = false;
        try {
            int numberOfUpdatedRows = create(user);
            resultOfOperation = DaoUtils.validateCorrectNumberOfRows(numberOfUpdatedRows);
        } catch (SQLException e) {
            Log.e(TAG, "Error while creating user");
        }
        return resultOfOperation;
    }


    private boolean updateUser(@NonNull final User user) {
        boolean resultOfOperation = false;
        try {
            int numberOfUpdatedRows = update(user);
            resultOfOperation = DaoUtils.validateCorrectNumberOfRows(numberOfUpdatedRows);
        } catch (SQLException e) {
            Log.e(TAG, "Error while updating user");
        }
        return resultOfOperation;
    }

    @Override
    public User login(@NonNull final String email, @NonNull final String password) { //TODO: implement method
        throw new RuntimeException("Not implemented yet");
    }
}
