package core.configs.settings;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.util.concurrent.ConcurrentHashMap;

public class ModControls {
    private final ConcurrentHashMap<MouseButton, String> mouseActions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<KeyCode, String> keyActions = new ConcurrentHashMap<>();
    public int getCount () {
        return mouseActions.size() + keyActions.size();
    }

}
