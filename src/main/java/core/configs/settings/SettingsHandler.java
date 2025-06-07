package core.configs.settings;

import core.gameActions.Debug;
import core.gameActions.FileManager;
import core.graphics.graphichandlers.GraphicProcessor;
import game_logic.managers.controls.ControlModManager;

import java.io.File;

public class SettingsHandler {

    public static Settings settings;

    private static void exec (Settings s) {
        Debug.debug("applying settings");
        s = Settings.check(s);
        GraphicProcessor.Controller.useSettings(s.getGraphicSettings());
    }
    public static void read () {
        File settingsJson = FileManager.concatToDirectory("dat", "settings.json");
        if (FileManager.existOrCreate(settingsJson)) {
            Settings s = Settings.readSettings(settingsJson);
            if (s != null) {
                settings = s;
                System.out.println(s.getInfo().getLastLanguage());
            } else
                settings = Settings.getStandard();
        } else
            settings = Settings.getStandard();
    }
    public static void write () {
        settings.setControls(ControlModManager.controls);
        testSettings();
        File save = FileManager.concatToDirectory("dat", "settings.json");
        Settings.writeSettings(save, settings);
    }
    public static void standardExec () {
        //System.out.println("running standard exec");
        //settings = Settings.check(settings);
        exec(settings);
    }
    public static void setLastLanguage (String s) {
        testSettings();
        settings.getInfo().setLastLanguage(s);
    }
    private static void testSettings () {
        settings = Settings.check(settings);
    }
}
