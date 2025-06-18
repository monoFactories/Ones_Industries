package mod.base.modification.graphics.components;

import mod.base.components.constants.SpecialConstant;
import core.gameActions.Debug;
import core.graphics.graphichandlers.GraphicProcessor;
import core.graphics.objects.MiniPanel;
import core.graphics.objects.NodeParameter;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.util.HashSet;
import java.util.List;

public class SoundMiniPanel extends MiniPanel {
    public SoundMiniPanel(String id) {
        super(id);
    }

    public SoundMiniPanel(String id, AnchorPane customPane) {
        super(id, customPane);
    }

    public SoundMiniPanel(String id, AnchorPane customPane, boolean isRelative, double width, double height, boolean canMoved, double x, double y) {
        super(id, customPane, isRelative, width, height, canMoved, x, y);
    }

    public SoundMiniPanel(String id, AnchorPane customPane, double width, double height, double x, double y) {
        super(id, customPane, width, height, x, y);
    }

    public SoundMiniPanel(String id, boolean isRelative, double width, double height, boolean canMoved, double x, double y) {
        super(id, isRelative, width, height, canMoved, x, y);
    }

    public SoundMiniPanel(String id, double width, double height, double x, double y) {
        super(id, width, height, x, y);
    }

    @Override
    public void addNode(Node node, NodeParameter parameter) {
        if (node != null && parameter != null) {
            Debug.debug(() -> "add new node in sounds graphic component [node = \"" + node + "\", parameter = \"" + parameter + "\"");
            String id = node.getId();
            if (id != null) {
                String[] parseID = id.split(";");
                if (new HashSet<>(List.of(parseID)).contains(SpecialConstant.INTERFACE.ID_ENABLED_SOUND)) {
                    Debug.debug("Node: " + node + " on add in sound graphic component");
                    SoundsGraphicComponent.setSoundsHandlers(node);
                }
            }
            super.addNode(node, parameter);
        }
    }

    @Override
    public void back() {
        GraphicProcessor.Controller.delete(getId());
    }
}
