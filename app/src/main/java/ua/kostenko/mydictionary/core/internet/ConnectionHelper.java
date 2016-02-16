package ua.kostenko.mydictionary.core.internet;

public interface ConnectionHelper {
    String SERVER_ADDRESS = "http://localhost:8080";

    String doGet(String address, String params);

    String doPost(String address, String params);

    String doPut(String address, String params);
}
