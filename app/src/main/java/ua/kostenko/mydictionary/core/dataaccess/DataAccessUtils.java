package ua.kostenko.mydictionary.core.dataaccess;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.io.File;

public final class DataAccessUtils {
    private Context appContext;

    public DataAccessUtils(@NonNull final Context appContext) {
        this.appContext = appContext;
    }

    public File getFileByPath(@NonNull final String path) {
        validatePath(path);
        return new File(path);
    }

    private void validatePath(@NonNull final String path) {
        if (Strings.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path can't be Null or Empty");
        }
        checkExistenceOfFile(new File(path));
    }

    private void checkExistenceOfFile(@NonNull final File file) {
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("Path: " + file.getAbsolutePath()
                    + " - is incorrect and this file isn't exists");
        }
    }
}
