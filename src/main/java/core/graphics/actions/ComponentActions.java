package core.graphics.actions;

import core.graphics.objects.GraphicComponent;

public class ComponentActions extends GraphicAction implements RequiredGraphicComponent {

    private GraphicComponent graph;

    public ComponentActions(String s, ActionType at, GraphicComponent graph) {
        super(s, at);
        this.graph = graph;
    }

    public ComponentActions(String[] s, ActionType at, GraphicComponent graph) {
        super(s, at);
        this.graph = graph;
    }

    @Override
    public GraphicComponent getComponent() {
        return graph;
    }

    public void setGraph(GraphicComponent graph) {
        this.graph = graph;
    }
}
