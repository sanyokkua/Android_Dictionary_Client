package ua.kostenko.mydictionary.core.local.dataaccess;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.inject.Inject;

import ua.kostenko.mydictionary.App;
import ua.kostenko.mydictionary.core.commonutils.IOUtils;

import static ua.kostenko.mydictionary.core.commonutils.Utils.isNotNull;

public final class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();
    private static final String REGULAR_EXPRESSION = "\\W|[1234567890_-]+";
    @Inject DataAccessUtils dataAccessUtils;

    public FileUtils() {
        App.getAppComponent().inject(this);
    }

    public String readFile(@NonNull final String filePath) {
        final File currentFile = dataAccessUtils.getFileByPath(filePath);
        BufferedReader bufferedReader = null;
        String textFromFile = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(currentFile));
            textFromFile = readText(bufferedReader);
            Log.d(TAG, String.format("Text from file: %s", textFromFile));
        } catch (IOException e) {
            Log.e(TAG, "Error with reading file", e);
        } finally {
            IOUtils.closeQuietly(bufferedReader);
        }
        return textFromFile;
    }

    private String readText(@NonNull final BufferedReader bufferedReader) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (isNotNull((line = bufferedReader.readLine()))) {
            String[] splitedLine = line.trim().split(REGULAR_EXPRESSION);
            for (String element : splitedLine) {
                stringBuilder.append(element);
                stringBuilder.append('\n');
            }
        }
        return stringBuilder.toString();
    }
}
