package core.configs.settings;

import com.google.gson.annotations.SerializedName;

public class InfoSettings {

    @SerializedName("language")
    private String lastLanguage;
    @SerializedName("debug")
    private boolean debuggingOn;
    @SerializedName("debugSaving")
    private boolean debugSaving;

    public InfoSettings(String lastLanguage) {
        this.lastLanguage = lastLanguage;
    }
    public InfoSettings () {
        lastLanguage = "english";
        this.debuggingOn = false;
    }
    public String getLastLanguage() {
        return lastLanguage;
    }

    public void setLastLanguage(String lastLanguage) {
        this.lastLanguage = lastLanguage;
    }

    public boolean isDebugging () {
        return debuggingOn;
    }

    public boolean isDebugSaving() {
        return debugSaving;
    }

    @Override
    public String toString() {
        return "infoSetting: {\n" + "\tlastLanguage: " + lastLanguage + ",\n\tdebuggingMode: " + debuggingOn + ",\n\tdebugSaving: " + debugSaving + "\n}";
    }

    public static InfoSettings check (InfoSettings info) {
        if (info != null) {
            if (info.lastLanguage == null) {
                info.setLastLanguage("English");
                return info;
            }
            return info;
        } else
            return new InfoSettings();
    }
}
