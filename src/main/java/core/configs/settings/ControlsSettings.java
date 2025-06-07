package core.configs.settings;

import game_logic.managers.controls.CurrentModsController;

public class ControlsSettings {
    public final CurrentModsController controls;

    public ControlsSettings () {
        controls = new CurrentModsController();
    }
    public static ControlsSettings getStandard() {
        return new ControlsSettings();
    }
    public static ControlsSettings check (ControlsSettings cs) {
        //ControlsSettings css = getStandard();
        if (cs == null) {
            return getStandard();
        }
        return cs;
    }
}
