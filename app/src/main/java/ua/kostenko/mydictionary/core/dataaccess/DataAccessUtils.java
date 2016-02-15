package ua.kostenko.mydictionary.core.dataaccess;

import android.content.Context;

import com.google.common.base.Strings;

import java.io.File;
import java.util.List;

public class DataAccessUtils {
    private Context appContext;

    public DataAccessUtils(Context appContext) {
        this.appContext = appContext;
    }

    public File openFileByPath(String path) {
        validatePath(path);
        throw new RuntimeException();//TODO
    }

    public void writeToFileByPath(String path, List<Strings> data) {
        validatePath(path);

        throw new RuntimeException();//TODO
    }

    private void validatePath(String path) {
        if (Strings.isNullOrEmpty(path)) {
            throw new IllegalArgumentException("Path can't be Null or Empty");
        }
        checkExistanceOfFile(new File(path));
    }

    private void checkExistanceOfFile(File file) {
        if (!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("Path: " + file.getAbsolutePath()
                    + " - is incorrect and this file isn't exists");
        }
    }
}
