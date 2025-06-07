package core.graphics.objects;

import core.configs.languages.LabelDescriptor;
import game_logic.repositories.LanguageRepository;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import game_logic.Game;
import core.graphics.actions.*;
import core.graphics.graphichandlers.GraphicProcessor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.logging.Level;

public class GraphicComponent {

    private final String id;

    private final Pane content;

    private final ConcurrentHashMap<Node, NodeParameter> allComponents;
    private final ConcurrentHashMap<Labeled, String> labeledContent;

    private final List<Node> nonUpdatedNodes;

    public GraphicComponent(String id) {
        if (id == null)
            throw new NullPointerException("id is null");
        this.id = id;
        this.content = new AnchorPane();
        this.allComponents = new ConcurrentHashMap<>();
        nonUpdatedNodes = new ArrayList<>();
        labeledContent = new ConcurrentHashMap<>();
    }
    public GraphicComponent(String id, AnchorPane customPane) {
        content = Objects.requireNonNullElseGet(customPane, AnchorPane::new);
        if (id == null)
            throw new NullPointerException("id is null");
        this.id = id;
        this.allComponents = new ConcurrentHashMap<>();
        nonUpdatedNodes = new ArrayList<>();
        labeledContent = new ConcurrentHashMap<>();
    }
    protected final void update(double width, double height, double offsetX, double offsetY) {
            allComponents.forEach((k, v) -> {
                if (v.isRelativeCoordinate) {
                    AnchorPane.setLeftAnchor(k, v.x / 100.0 * width + offsetX);
                    AnchorPane.setTopAnchor(k, v.y / 100.0 * height + offsetY);
                }
                if (v.isRelativeSize) {
                    double newWidth = v.width / 100.0 * width;
                    double newHeight = v.height / 100.0 * height;
                    if (k instanceof Control r) {
                        r.setPrefWidth(newWidth);
                        r.setPrefHeight(newHeight);
                        return;
                    }
                    if (k instanceof Rectangle rect) {
                        rect.setWidth(newWidth);
                        rect.setHeight(newHeight);
                        return;
                    }
                    if (k instanceof ImageView imageView) {
                        imageView.setFitWidth(newWidth);
                        imageView.setFitHeight(newHeight);
                        //imageView.setPreserveRatio(true);
                        return;
                    }
                    if (k instanceof Pane pane) {
                        pane.setPrefWidth(newWidth);
                        pane.setPrefHeight(newHeight);
                    }
                }
            });
    }

    protected final void update(boolean isAbsoluteXY, double x, double y, boolean isAbsoluteSize, double width, double height) {
        double winWidth = GraphicProcessor.getter.getWidth();
        double winHeight = GraphicProcessor.getter.getHeight();
        double functionalWidth, functionalHeight, functionalX, functionalY = 0;
        if (isAbsoluteXY) {
            functionalX = x;
            functionalY = y;
        } else {
            functionalX = x / 100.0 * winWidth;
            functionalY = y / 100.0 * winHeight;
        }
        if (isAbsoluteSize) {
            functionalWidth = width;
            functionalHeight = height;
        } else {
            functionalWidth = width / 100.0 * winWidth;
            functionalHeight = height / 100.0 * winHeight;
        }
        update(functionalWidth, functionalHeight, functionalX, functionalY);
    }
    public final void standardUpdate() {
        update(GraphicProcessor.getter.getWidth(), GraphicProcessor.getter.getHeight(), 0, 0);
    }
    public void CustomUpdate () {
        standardUpdate();
    }
    public void customUpdateNonUpdatesNodes (Consumer<? super Node> action) {
        nonUpdatedNodes.forEach(action);
    }
    public void addNode(Node node, NodeParameter parameter) {
        if (node == null || parameter == null)
            Game.log(Level.INFO, "attempt to add a node, Node:" + node + ", parameter:" + parameter, null);
        else {
            double winWidth = GraphicProcessor.getter.getWidth();
            double winHeight = GraphicProcessor.getter.getHeight();
            if (parameter.isRelativeCoordinate) {
                node.setLayoutX((parameter.x / 100.0) * winWidth);
                node.setLayoutY (parameter.y / 100.0 * winHeight);
            } else {
                node.setLayoutX(parameter.x);
                node.setLayoutY(parameter.y);
            }
            if (parameter.isRelativeSize) {
                node.prefWidth(parameter.width / 100.0 * winWidth);
                node.prefHeight(parameter.height / 100.0 * winHeight);
            } else {
                node.prefWidth(parameter.width);
                node.prefHeight(parameter.height);
            }
            allComponents.put (node, parameter);
            content.getChildren().add(node);
            if (node instanceof Labeled labeled) {
                labeledContent.put(labeled, labeled.getText());
            }
        }
    }
    public final void addNonUpdatableNode (Node node) {
        nonUpdatedNodes.add(node);
        content.getChildren().add(node);
    }
    public final void removeNode(List<Node> nodes) {
        if (nodes != null) {
            allComponents.keySet().removeIf(nodes::contains);
            content.getChildren().removeIf(nodes::contains);
            labeledContent.keySet().removeIf(nodes::contains);
        }
    }
    public final void translating (LanguageRepository language) {
        labeledContent.forEach((l, sp) -> {
            //System.out.println("constant text: " + sp);
            l.setText(LabelDescriptor.descriptor(language, sp));
        });
    }
    public static GraphicComponent createGameComponent (Map <Node, NodeParameter> components, String name) {
        if (name == null || components == null)
            return null;
        GraphicComponent component = new GraphicComponent(name);
        components.forEach(component::addNode);
        return component;
    }

    public final String getId() {
        return id;
    }
    public final Pane getAnchorPane () {
        return content;
    }

    public final ConcurrentHashMap<Node, NodeParameter> getComponents() {
        return allComponents;
    }

    public final void executeGraphicAction (GraphicAction a) {
        if (a != null) {
            ActionType t = a.getType();
            switch (t) {
                case CLEAR -> {
                    content.getChildren().clear();
                    allComponents.clear();
                    labeledContent.clear();
                    nonUpdatedNodes.clear();
                }
                case ADD_NODES -> {
                    if (a instanceof RequiredMap add) {
                        Map<Node, NodeParameter> adding = add.getNodeMap();
                        adding.forEach(this::addNode);
                    }
                }
                case REMOVE_NODES -> {
                    if (a instanceof RequiredSet rs) {
                        Set<Node> set = rs.getNodeSet();
                        removeNode(set.stream().toList());
                    }
                }
                case INPUT_NON_REPEAT -> {
                    if (a instanceof ComponentActions ca) {
                        GraphicComponent componentAction = ca.getComponent();
                        if (componentAction != null) {
                            componentAction.allComponents.forEach((n, np) -> {
                                if (!allComponents.containsKey(n)) {
                                    this.addNode(n, np);
                                }
                            });
                        }
                    }
                }
                case REMOVE_DUPLICATE -> {
                    if (a instanceof ComponentActions ca) {
                        GraphicComponent gc = ca.getComponent();
                        if (gc != null) {
                            List<Node> toRemove = new ArrayList<>();
                            gc.allComponents.forEach((n, np) -> {
                                if (allComponents.containsKey(n)) {
                                    toRemove.add(n);
                                }
                            });
                            removeNode(toRemove);
                        }
                    }
                }
                case null, default -> {}
            }
        }
    }
    public void onRemoveFromScreen () {}
    public void onAddFromScreen () {}
    public void back() {
        GraphicProcessor.Controller.delete(id);
        GraphicProcessor.Controller.loadLastFromStack();
    }
}
