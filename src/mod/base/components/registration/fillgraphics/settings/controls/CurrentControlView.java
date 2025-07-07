package mod.base.components.registration.fillgraphics.settings.controls;

import game_logic.managers.controls.CurrentControl;
import game_logic.repositories.Identifier;

public class CurrentControlView {
    private Identifier id; // globalID
    private String name;
    private CurrentControl control;

    public CurrentControlView(CurrentControl control, Identifier id, String name) {
        this.control = control;
        this.id = id;
        this.name = name == null ? "null" : name;
    }

    public CurrentControl getControl() {
        return control;
    }
    public void setControl(CurrentControl control) {
        this.control = control;
    }

    public Identifier getId() {
        return id;
    }
    public void setId(Identifier id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
