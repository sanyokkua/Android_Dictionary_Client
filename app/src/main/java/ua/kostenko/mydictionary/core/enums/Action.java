package ua.kostenko.mydictionary.core.enums;

public enum Action {
    CREATE("create"), DELETE("delete"), UPDATE("update");

    private String action;

    Action(String action) {
        this.action = action;
    }

    public String getName() {
        return this.action;
    }
}
