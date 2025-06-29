package mod.base.modification.graphics.utils;

import core.graphics.objects.GraphicComponent;
import core.graphics.objects.NodeParameter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class ButtonSwitchFactory {

    private final ConcurrentHashMap<Button, ButtonStorage> storage = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Button, ButtonEntry> buttonParameters = new ConcurrentHashMap<>();
    private AtomicReference<Button> selected = new AtomicReference<>(null);

    public ButtonStorage addSwitcher (Button btn, NodeParameter parameter, String standardStyle, String activeStyle) {
        if (btn == null)
            return null;
        // create buttonStorage
        final ButtonSwitchFactory thisFactory = this;
        ButtonStorage btnStorage = new ButtonStorage() {
            @Override
            public Button getButton() {
                return btn;
            }

            @Override
            public ButtonSwitchFactory getParent() {
                return thisFactory;
            }
        };
        // buttonEntry
        ButtonEntry be = new ButtonEntry(btn, parameter != null, parameter, standardStyle, activeStyle);
        storage.put(btn, btnStorage);
        buttonParameters.put(btn, be);
        // handlers
        EventHandler<ActionEvent> lastHandler = btn.getOnAction();
        if (lastHandler != null) {
            btn.setOnAction(ae -> {
                lastHandler.handle(ae);
                select(btn);
            });
        } else
            btn.setOnAction(ae -> select(btn));
        return btnStorage;
    }
    public ButtonStorage addSwitcher (Button btn, NodeParameter parameter) {
        return addSwitcher(btn, parameter, null, null);
    }
    public ButtonStorage addSwitcher (Button btn) {
        return addSwitcher(btn, null);
    }

    private void select (Button select) {
        if (select == null)
            return;
        if (selected.get() != select) {
            ButtonStorage buttonStorage = storage.get(select);
            ButtonEntry buttonEntry = buttonParameters.get(select);
            if (buttonStorage != null && buttonEntry != null) {
                selected.set(select);
                storage.forEach((e, s) -> s.entriesList.forEach(ne -> ne.node.setVisible(false)));
                buttonStorage.entriesList.forEach(ne -> ne.node.setVisible(true));
                // button style
                buttonParameters.forEach((button, param) -> {
                    String standard = param.standardStyle();
                    String active = param.activeStyle();

                    // Удаляем активный стиль
                    if (active != null && !active.isEmpty()) {
                        button.getStyleClass().remove(active);
                    }
                    // Удаляем старый стандартный стиль, если нужно
                    // (если стандартный стиль не меняется - можно не удалять)
                    if (standard != null && !standard.isEmpty()) {
                        if (!button.getStyleClass().contains(standard)) {
                            button.getStyleClass().add(standard);
                        }
                    }
                });
                String activeStyle = buttonEntry.activeStyle();
                if (activeStyle != null && !activeStyle.isEmpty()) {
                    if (!select.getStyleClass().contains(activeStyle)) {
                        select.getStyleClass().add(activeStyle);
                    }
                }
            }
        }
    }
    public void addOnComponent (GraphicComponent graphicComponent, Button firstSelect) {
        if (graphicComponent != null) {
            storage.forEach((button, buttonStorage) -> {
                buttonStorage.entriesList.forEach(nodeEntry -> {
                    if (nodeEntry.needAdd())
                        graphicComponent.addNode(nodeEntry.node, nodeEntry.paramIfNeed);
                });
                ButtonEntry buttonEntry = buttonParameters.get(button);
                if (buttonEntry != null && buttonEntry.needAdd())
                    graphicComponent.addNode (button, buttonEntry.paramIfNeed);
                if (firstSelect != null)
                    select(firstSelect);
            });
        }
    }
    public abstract static class ButtonStorage {
        private final List<NodeEntry> entriesList = new ArrayList<>();
        public abstract Button getButton ();
        public abstract ButtonSwitchFactory getParent();
        public final ButtonStorage addNode (Node node, NodeParameter parameter) {
            if (node != null) {
                node.setVisible(false);
                entriesList.add(new NodeEntry(node, parameter != null, parameter));
            }
            return this;
        }
        public final ButtonStorage addNode (Node node) {
            return addNode(node, null);
        }
    }
    /// if needAdd = true -> node will be added in new GraphicComponent. But if needAdd = false -> nothing, perhaps the node has already been completed, it doesn't matter.
    public record NodeEntry (Node node, boolean needAdd, NodeParameter paramIfNeed) {
    }
    public record ButtonEntry (Button btn, boolean needAdd, NodeParameter paramIfNeed, String standardStyle, String activeStyle) {
    }
}
