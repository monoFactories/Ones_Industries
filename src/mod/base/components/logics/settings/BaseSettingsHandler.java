package mod.base.components.logics.settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import mod.base.modification.Base;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.logging.Level;

public class BaseSettingsHandler {
    public static final String pathToSettings = "settings/setting.json";
    private static BaseSettings base;
    public static void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            String s = gson.toJson(BaseSettings.testAll(base));
            Base.files.writeStringsResource(pathToSettings, List.of(s));
        } catch (Exception e) {
            Base.getMod().debug(Level.WARNING, "could not save settings", e);
        }
    }
    public static void load() {
        Gson gson = new Gson();
        BaseSettings bs = null;
        try(Reader r = new InputStreamReader(Base.files.getInputStreamResource(pathToSettings))) {
            bs = gson.fromJson(r, BaseSettings.class);
        } catch (Exception e) {
            Base.getMod().debug(Level.WARNING, "could not read settings", e);
        }
        setBaseSettings(bs);
    }

    public static BaseSettings getBaseSettings() {
        return BaseSettings.testAll(base);
    }

    public static void setBaseSettings(BaseSettings base) {
        BaseSettingsHandler.base = BaseSettings.testAll(base);
    }
}
