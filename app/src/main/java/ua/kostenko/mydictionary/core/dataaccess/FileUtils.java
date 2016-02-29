package ua.kostenko.mydictionary.core.dataaccess;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FileUtils { //TODO: finish realization
    private static final String TAG = FileUtils.class.getSimpleName();
    private final DataAccessUtils dataAccessUtils;
    private final Context appContext;

    public FileUtils(final Context context) {
        appContext = context;
        dataAccessUtils = new DataAccessUtils(appContext);
    }

    public String readFile(String filePath) {
        final File currentFile = dataAccessUtils.openFileByPath(filePath);
        BufferedReader bufferedReader = null;
        String textFromFile = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(currentFile));
            textFromFile = readText(bufferedReader);
        } catch (IOException e) {
            Log.e(TAG, "Error with reading file", e);
        } finally {
            closeReader(bufferedReader);
        }
        return textFromFile;
    }

    private String readText(Reader bufferedReader) {

        return null;
    }

    public static void closeReader(final Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                Log.e(TAG, "Error with closing reader", e);
            }
        }
    }
}
