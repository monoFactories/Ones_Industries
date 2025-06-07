package game_logic.managers.controls;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class ControlContext {
    private final boolean isMouseEvent;
    private final MouseEvent mouseEvent;
    private final KeyEvent keyEvent;
    public ControlContext (MouseEvent mouseEvent) {
        this.isMouseEvent = true;
        this.mouseEvent = mouseEvent;
        keyEvent = null;
    }

    public ControlContext (KeyEvent keyEvent) {
        this.keyEvent = keyEvent;
        mouseEvent = null;
        isMouseEvent = false;
    }

    public boolean isMouseEvent() {
        return isMouseEvent;
    }

    public KeyEvent getKeyEvent() {
        return keyEvent;
    }

    public MouseEvent getMouseEvent() {
        return mouseEvent;
    }
}
