package mod.base.components.logics.settings;

import com.google.gson.annotations.SerializedName;

public class BaseSettings {
    @SerializedName("save_settings")
    private SaveSettings save;
    private BaseSettings() {
        save = new SaveSettings();
    }

    public SaveSettings getSave() {
        return SaveSettings.testSettings(save);
    }

    public void setSave(SaveSettings save) {
        this.save = SaveSettings.testSettings(save);
    }
    public static BaseSettings testAll (BaseSettings bs) {
        if (bs == null)
            bs = new BaseSettings();
        bs.save = SaveSettings.testSettings(bs.save);
        return bs;
    }
}
