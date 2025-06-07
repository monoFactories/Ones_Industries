package core.graphics.actions;

import javafx.scene.Node;
import core.graphics.objects.NodeParameter;

import java.util.Map;

public interface RequiredMap {
    Map<Node, NodeParameter> getNodeMap();
}
