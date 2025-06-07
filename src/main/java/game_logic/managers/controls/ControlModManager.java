package game_logic.managers.controls;

import core.configs.settings.SettingsHandler;

public class ControlModManager {

    public static final CurrentModsController controls = new CurrentModsController();

    public static void save() {
        SettingsHandler.settings.setControls(controls);
    }
    public static void load () {
        controls.append(SettingsHandler.settings.getControls());
    }
}
