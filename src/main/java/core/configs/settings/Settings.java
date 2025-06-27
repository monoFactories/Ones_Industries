package core.configs.settings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import game_logic.Game;
import game_logic.managers.controls.ControlModManager;
import game_logic.managers.controls.CurrentModsController;

import java.io.*;
import java.util.logging.Level;

public class Settings {
    @SerializedName("graphic")
    private GraphicSettings graphicSettings;
    private SoundSettings sound;
    private InfoSettings info;
    private CurrentModsController controls;

    public Settings(GraphicSettings graphicSettings, InfoSettings info, SoundSettings sound, CurrentModsController currentModsController) {
        this.graphicSettings = graphicSettings;
        this.info = info;
        this.sound = sound;
        this.controls = currentModsController;
    }
    public Settings() {

    }
    public GraphicSettings getGraphicSettings () {
        return graphicSettings == null ? GraphicSettings.getStandard() : graphicSettings;
    }
    public SoundSettings getSound() {
        return sound == null ? SoundSettings.getStandard() : sound;
    }
    public InfoSettings getInfo() {
        return info == null ? new InfoSettings() : info;
    }
    public CurrentModsController getControls() {
        return controls == null ? ControlModManager.controls : controls;
    }


    public void setInfo(InfoSettings info) {
        if (info != null) {
            this.info = info;
        }
    }
    public void setGraphicSettings(GraphicSettings graphicSettings) {
        if (graphicSettings != null) {
            this.graphicSettings = graphicSettings;
        }
    }
    public void setSound (SoundSettings sound) {
        if (sound != null) {
            this.sound = sound;
        }
    }
    public void setControls (CurrentModsController controls) {
        if (controls != null) {
            this.controls = controls;
        }
    }

    @Override
    public String toString() {
        return "{" + graphicSettings + ", \n" + sound + ",\n" + info + ",\n" + controls + "}";
    }

    public static Settings readSettings (File settingsFile) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(CurrentModsController.class, CurrentModsController.CURRENT_MODS_CONTROLS_JSON_ADAPTER)
                .create();
        if (settingsFile != null) {
            try (Reader read = new InputStreamReader(new FileInputStream(settingsFile))) {
                return gson.fromJson(read, Settings.class);
            } catch (IOException | JsonIOException | JsonSyntaxException | NullPointerException io ) {
                Game.log(Level.WARNING, "Couldn't read settings from file :" + settingsFile.getName(), io);
            }
        }
        return Settings.getStandard();
    }
    public static void writeSettings (File settingsFile, Settings settings) {
        if (settingsFile != null && settings != null) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(CurrentModsController.class, CurrentModsController.CURRENT_MODS_CONTROLS_JSON_ADAPTER)
                    .create();
            try (FileWriter writer = new FileWriter(settingsFile)) {
                gson.toJson(settings, writer);
            } catch (IOException e) {
                Game.log(Level.WARNING, "Couldn't write settings to file :" + settingsFile.getName(), e);
            }
        }
    }
    public static Settings getStandard () {
        return new Settings(GraphicSettings.getStandard(), new InfoSettings(), SoundSettings.getStandard(), ControlModManager.controls);
    }
    public static Settings check (Settings e) {
        if (e != null) {
            e.info = InfoSettings.check(e.info);
            e.sound = SoundSettings.check(e.sound);
            e.graphicSettings = GraphicSettings.check(e.graphicSettings);
            //e.controls = ControlsSettings.check(e.controls);
            return e;
        } else
            return getStandard();
    }
}
