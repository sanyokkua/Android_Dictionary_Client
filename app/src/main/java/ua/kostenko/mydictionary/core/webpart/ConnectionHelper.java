package ua.kostenko.mydictionary.core.webpart;

import android.support.annotation.NonNull;

public interface ConnectionHelper {
    String SERVER_ADDRESS = "http://localhost:8080";

    String doGet(@NonNull final String address, final String params);

    String doPost(@NonNull final String address, final String params);

    String doPut(@NonNull final String address, final String params);
}
