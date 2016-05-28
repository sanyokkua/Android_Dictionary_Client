package ua.kostenko.mydictionary.core.local.dataaccess;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.File;

import javax.inject.Inject;

import ua.kostenko.mydictionary.App;

import static ua.kostenko.mydictionary.core.commonutils.Utils.isEmpty;

public final class DataAccessUtils {
    private static final String TAG = DataAccessUtils.class.getSimpleName();
    @Inject Context appContext;

    public DataAccessUtils() {
        App.getAppComponent().inject(this);
    }

    public File getFileByPath(@NonNull final String path) {
        validatePath(path);
        return new File(path);
    }

    private void validatePath(@NonNull final String path) {
        if (isEmpty(path)) {
            throw new IllegalArgumentException("Path can't be Null or Empty");
        }
        checkExistenceOfFile(new File(path));
    }

    private void checkExistenceOfFile(@NonNull final File file) {
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException(String.format("Path: %s  - is incorrect and this file isn't exists",
                    file.getAbsolutePath()));
        }
    }
}
