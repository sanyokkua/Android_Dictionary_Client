package ua.kostenko.mydictionary.core.enums;

public enum Languages {
    ENGLISH("eng"), RUSSIAN("rus");

    private String code;

    Languages(String code) {
        this.code = code;
    }

    public String getLangCode() {
        return this.code;
    }
}
