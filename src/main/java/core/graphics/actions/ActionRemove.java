package core.graphics.actions;

import javafx.scene.Node;

import java.util.*;

public class ActionRemove extends GraphicAction implements RequiredSet {
    private final HashSet<Node> removedList;
    public ActionRemove(String s) {
        super(s, ActionType.REMOVE_NODES);
        removedList = new HashSet<>();
    }
    public ActionRemove(String s, HashSet<Node> removedList) {
        super(s, ActionType.REMOVE_NODES);
        this.removedList = Objects.requireNonNullElseGet(removedList, HashSet::new);
    }
    public ActionRemove(String[] s) {
        super(s, ActionType.REMOVE_NODES);
        removedList = new HashSet<>();
    }
    public ActionRemove(String[] s, HashSet<Node> removedList) {
        super(s, ActionType.REMOVE_NODES);
        this.removedList = Objects.requireNonNullElseGet(removedList, HashSet::new);
    }
    @Override
    public Set<Node> getNodeSet() {
        return removedList;
    }
    public void add (Node... nodes) {
        removedList.addAll(Arrays.stream(nodes).toList());
    }
    public void remove (Node... nodes) {
        Arrays.stream(nodes).toList().forEach(removedList::remove);
    }
}
