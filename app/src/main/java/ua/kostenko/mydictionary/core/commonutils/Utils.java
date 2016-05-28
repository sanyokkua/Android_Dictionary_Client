package ua.kostenko.mydictionary.core.commonutils;

import java.util.Collection;

public class Utils {

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNotNull(Object object) {
        return object != null;
    }

    public static boolean isEmpty(CharSequence string) {
        return isNull(string) || string.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence string) {
        return isNotNull(string) && string.length() > 0;
    }

    public static boolean isEmpty(Collection collection) {
        return isNull(collection) || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection collection) {
        return isNotNull(collection) && collection.size() > 0;
    }

    public static void checkNotNull(Object argument) {
        checkNotNull(argument, "Argument is null");
    }

    public static void checkNotNull(Object argument, String message) {
        if (isNull(argument)) {
            throw new IllegalArgumentException(message);
        }
    }
}
