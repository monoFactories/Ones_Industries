package core.graphics.actions;

import javafx.scene.Node;
import core.graphics.objects.NodeParameter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ActionAdd extends GraphicAction implements RequiredMap {
    private final Map<Node, NodeParameter> tableNodes;
    public ActionAdd(String s) {
        super(s, ActionType.ADD_NODES);
        tableNodes = new HashMap<>();
    }
    public ActionAdd(String s, Map<Node, NodeParameter> tableNodes) {
        super(s, ActionType.ADD_NODES);
        this.tableNodes = Objects.requireNonNullElseGet(tableNodes, HashMap::new);
    }
    public ActionAdd(String[] s) {
        super(s, ActionType.ADD_NODES);
        tableNodes = new HashMap<>();
    }
    public ActionAdd(String[] s, Map<Node, NodeParameter> tableNodes) {
        super(s, ActionType.ADD_NODES);
        this.tableNodes = Objects.requireNonNullElseGet(tableNodes, HashMap::new);
    }
    public void addKV (Node node, NodeParameter parameter) {
        if (node != null && parameter != null)
            tableNodes.put(node, parameter);
    }
    public void deleteKV (Node node) {
        if (node != null)
            tableNodes.remove(node);
    }
    @Override
    public Map<Node, NodeParameter> getNodeMap() {
        return tableNodes;
    }
}
