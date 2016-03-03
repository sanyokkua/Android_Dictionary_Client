package ua.kostenko.mydictionary.core.local.dataaccess;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public final class FileUtils { //TODO: finish realization
    private static final String TAG = FileUtils.class.getSimpleName();
    private final DataAccessUtils dataAccessUtils;

    // TODO: need injection of context
    private final Context appContext;

    public FileUtils(@NonNull final Context context) {
        appContext = context;
        dataAccessUtils = new DataAccessUtils(appContext);
    }

    public String readFile(@NonNull final String filePath) {
        final File currentFile = dataAccessUtils.getFileByPath(filePath);
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

    private String readText(@NonNull final BufferedReader bufferedReader) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }

    public static void closeReader(@Nullable final Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                Log.e(TAG, "Error with closing reader", e);
            }
        }
    }
}
