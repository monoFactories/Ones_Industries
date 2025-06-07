package game_logic.managers.controls;

import game_logic.repositories.Identifier;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class ControlsActionStorage {
    private final CurrentControl forControl;
    private final List<Identifier> idsActions;
    private int counter;

    public ControlsActionStorage (CurrentControl control, List<Identifier> idsActions) {
        this.counter = 0;
        this.forControl = control;
        this.idsActions = new CopyOnWriteArrayList<>(idsActions.stream().filter(Objects::nonNull).toList());
    }
    public Identifier handleClick () {
        if (idsActions.isEmpty())
            return null;
        Identifier res = idsActions.get(counter);
        counter = (counter + 1) % idsActions.size();
        return res;
    }
    public CurrentControl getCurrentControl () {
        return forControl;
    }
}
