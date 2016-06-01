package ua.kostenko.mydictionary.core.commonutils;

import android.util.Log;

import java.io.Closeable;
import java.io.IOException;

import static ua.kostenko.mydictionary.core.commonutils.Utils.isNotNull;

public class IOUtils {
    private static final String TAG = IOUtils.class.getSimpleName();

    public static void closeQuietly(Closeable closeable) {
        if (isNotNull(closeable)) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.i(TAG, "Closeable object thrown exception due closing and it had ignored", e);
            }
        } else {
            Log.i(TAG, "Closeable object is null so had not been closed");
        }
    }
}
