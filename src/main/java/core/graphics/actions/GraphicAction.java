package core.graphics.actions;

import java.util.Objects;

public class GraphicAction {
    private final String[] targets;
    protected final ActionType type;
    public GraphicAction (String[] s, ActionType at) {
        Objects.requireNonNull(s, "targets can not be null");
        targets = s;
        if (at != null)
            type = at;
        else type = ActionType.NONE;
    }
    public GraphicAction (String s, ActionType at) {
        Objects.requireNonNull(s, "target can not be null");
        targets = new String[]{s};
        if (at != null)
            type = at;
        else type = ActionType.NONE;
    }

    public String[] getTargets() {
        return targets;
    }

    public ActionType getType() {
        return type;
    }
}
