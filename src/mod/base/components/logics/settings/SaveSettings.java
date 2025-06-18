package mod.base.components.logics.settings;

import com.google.gson.annotations.SerializedName;

public class SaveSettings {
    @SerializedName("save_in_archive")
    public final boolean savingInArchive;
    public SaveSettings() {
        savingInArchive = true;
    }
    public static SaveSettings testSettings (SaveSettings ss) {
        return ss == null ? new SaveSettings() : ss;
    }
}
