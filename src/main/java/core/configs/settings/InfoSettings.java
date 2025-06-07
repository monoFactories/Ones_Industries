package core.configs.settings;

public class InfoSettings {

    private String lastLanguage;
    private boolean isDebuggingTrue;

    public InfoSettings(String lastLanguage) {
        this.lastLanguage = lastLanguage;
    }
    public InfoSettings () {
        lastLanguage = "english";
        this.isDebuggingTrue = false;
    }
    public String getLastLanguage() {
        return lastLanguage;
    }

    public void setLastLanguage(String lastLanguage) {
        this.lastLanguage = lastLanguage;
    }

    public boolean isDebugging () {
        return isDebuggingTrue;
    }
    @Override
    public String toString() {
        return "InfoSettings {" +
                "lastLanguage=\"" + lastLanguage + '"' +
                '}';
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
