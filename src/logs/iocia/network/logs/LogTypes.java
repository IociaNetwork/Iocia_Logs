package iocia.network.logs;

public enum LogTypes {

    GENERAL("[GENERAL] : "),
    SUCCESS("[SUCCESS] : "),
    WARNING("[WARNING] : "),
    SEVERE("[SEVERE]  : "),
    DEBUG("[DEBUG]   : ");

    private String message;

    LogTypes(String message) {
        this.message = message;
    }

    public String get() {
        return message;
    }

}
