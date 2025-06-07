package game_logic.managers.controls;

import core.management.ControlEntry;
import core.moding.data.ControlRegister;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class CurrentControl {
    private boolean isMouse;
    private KeyCode key;
    private MouseButton btn;

    public CurrentControl(CurrentControl cr) {
        this.isMouse = cr.isMouse;
        this.key = cr.key;
        this.btn = cr.btn;
    }

    public CurrentControl(MouseButton btn) {
        this(true, btn, null);
    }

    public CurrentControl(KeyCode key) {
        this(false, null, key);
    }

    public static CurrentControl fromControlVariable(ControlRegister.ControlVariable var1) {
        ControlEntry e = var1.getControlEntry();
        return new CurrentControl(e.isMouse(), e.getBtn(), e.getKey());
    }

    private CurrentControl(boolean isMouse, MouseButton btn, KeyCode key) {
        this.btn = btn;
        this.isMouse = isMouse;
        this.key = key;
    }

    public void set(MouseButton btn) {
        isMouse = true;
        this.btn = btn;
        this.key = null;
    }

    public void set(KeyCode key) {
        isMouse = false;
        this.key = key;
        this.btn = null;
    }

    public boolean isMouse() {
        return isMouse;
    }

    public MouseButton getBtn() {
        return btn;
    }

    public KeyCode getKey() {
        return key;
    }

    public Object get() {
        return isMouse ? btn : key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrentControl that)) return false;
        return isMouse == that.isMouse && key == that.key && btn == that.btn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        if (isMouse) {
            hash = 0x80000000;
            if (btn != null)
                hash += (btn.hashCode() >> 1);
        } else {
            if (key != null)
                hash += (key.hashCode() >> 1);
        }
        return hash;
    }

    @Override
    public String toString() {
        return "Current Control [" + (isMouse ? "mouse = " + btn : "keycode = " + key) + "]";
    }
}
