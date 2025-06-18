package mod.base.components.worlds.graphics.offline.files;

import com.google.gson.annotations.SerializedName;

public record LevelDescription(@SerializedName("name") String name, @SerializedName("mods") String[] lastMods) {
    public static void init() {
    }
}
