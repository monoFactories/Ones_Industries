package mod.base.modification.graphics.components;

import core.graphics.graphichandlers.GraphicProcessor;
import core.graphics.objects.NodeParameter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public final class ConfirmMiniPanel extends SoundMiniPanel {

    private final Button ok = new Button();
    private final Button cancel = new Button();
    private volatile boolean useBack = true;

    private final EventHandler<ActionEvent> okAction;
    private final EventHandler<ActionEvent> cancelAction;

    public ConfirmMiniPanel(String id, NodeParameter okParam, NodeParameter cancelParam, String okText, String cancelText, EventHandler<ActionEvent> okAction, EventHandler<ActionEvent> cancelAction, String okStyle, String cancelStyle) {
        super(id);
        if (okParam == null || cancelParam == null)
            throw new NullPointerException("parameters couldn't be null");
        ok.setText(okText);
        cancel.setText(cancelText);
        ok.getStyleClass().add(okStyle);
        cancel.getStyleClass().add(cancelStyle);
        this.okAction = okAction;
        this.cancelAction = cancelAction;
        ok.setOnAction(ae -> ok());
        cancel.setOnAction(ae -> cancel());
        addNode(ok, okParam);
        addNode(cancel, cancelParam);
    }
    private void ok() {
        if (okAction != null) {
            useBack = false;
            okAction.handle(new ActionEvent());
        }
        GraphicProcessor.Controller.delete(super.getId());
    }
    private void cancel() {
        if (cancelAction != null) {
            useBack = false;
            cancelAction.handle(new ActionEvent());
        }
        GraphicProcessor.Controller.delete(super.getId());
    }

    public static void init(){}
}
