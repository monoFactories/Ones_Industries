package core.management;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public final class ControlEntry {
    public static final ControlEntry EMPTY = new ControlEntry((KeyCode) null);

    private final MouseButton mouseButton;
    private final KeyCode keyCode;
    private final boolean isMouse;

    public ControlEntry(MouseButton btn) {
        this.mouseButton = btn;
        this.keyCode = null;
        this.isMouse = true;
    }
    public ControlEntry(KeyCode key) {
        this.mouseButton = null;
        this.keyCode = key;
        this.isMouse = false;
    }
    public MouseButton getBtn() {
        return mouseButton;
    }

    public KeyCode getKey() {
        return keyCode;
    }
    public boolean isMouse() {
        return isMouse;
    }
}