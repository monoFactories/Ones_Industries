package game_logic.managers;

import game_logic.managers.controls.CurrentControl;

import java.util.concurrent.ConcurrentHashMap;

public class ControlsChanger {
    public final ConcurrentHashMap<String, CurrentControl> changeList;

    public ControlsChanger() {
        this.changeList = new ConcurrentHashMap<>();
    }
    public void add(String s1, String s2, CurrentControl cc) {
        add(s1 + ":" + s2, cc);
    }
    public void add(String s1, CurrentControl cc) {
        if (s1 != null) {
            String[] s3 = s1.split(":", 2);
            if (s3.length >= 2) {
                changeList.put(s1, cc);
            }
        }
    }
}
