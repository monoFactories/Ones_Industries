package core.graphics.objects;

import game_logic.repositories.Identifier;
import javafx.scene.layout.AnchorPane;
import core.graphics.graphichandlers.GraphicProcessor;

public class MiniPanel extends GraphicComponent {
    /// constants:
    private static final String commonStyle = "-fx-background-color: lightgray; -fx-border-color: black; -fx-border-width: 2;";
    private static final Size standard = new Size() {
        @Override
        public double getWidth() {
            return GraphicProcessor.getter.getWidth();
        }

        @Override
        public double getHeight() {
            return GraphicProcessor.getter.getHeight();
        }
    };
    ///sizes and characteristics:
    private Size size;/// using for auto change size if isRelative == true, if isRelative == false size is null
    private boolean isRelative;
    private double width;
    private double height;

    ///coordinates:
    private boolean canMoved;
    private double x;
    private double y;
    ///other characteristics:
    private final AnchorPane panel;
    /// moved characteristics:
    private double mouseX;
    private double mouseY;
    private double lastX;
    private double lastY;
    //
    /*
    * @param id - identifier the panel,
    * @param isRelativeSize if true, the panel size will be relative,
     * and the width and height parameters will be perceived as
     * percentages of values returned by methods of the "Size" interface.
    * @param width is the width of the panel, if isRelativeSize is false,
     * else a percentage of the value returned by the "getWidth"
     * method in the "Size" interface.
     * @param height see description for the "width" parameter.
    * */
    /// constructors:
    public MiniPanel(Identifier id) {
        this(id, standard, null, false, getNormalWidth(),getNormalHeight(), false, getNormalX(),getNormalY());
    }
    public MiniPanel(Identifier id, double width, double height, double x, double y) {
        this(id, standard, null, false, width, height, false, x ,y);
    }
    public MiniPanel(Identifier id, boolean isRelative, double width, double height, boolean canMoved, double x, double y) {
        this(id, standard, null, isRelative, width, height, canMoved, x ,y);
    }
    public MiniPanel(Identifier id, AnchorPane customPane) {
        this(id, standard, customPane, false, getNormalWidth(), getNormalHeight(), false, getNormalX(), getNormalY());
    }
    public MiniPanel(Identifier id, AnchorPane customPane, double width, double height, double x, double y) {
        this(id, standard, customPane, false, width, height, false, x ,y);
    }
    public MiniPanel(Identifier id, AnchorPane customPane, boolean isRelative, double width, double height, boolean canMoved, double x, double y) {
        this(id, standard, customPane, isRelative, width, height, canMoved, x ,y);
    }
    private MiniPanel(Identifier id, Size size, AnchorPane pane, boolean isRelativeSize, double width, double height, boolean canMoved, double x, double y) {
        super(id);
        this.size = size;
        this.isRelative = isRelativeSize;
        this.width = width;
        this.height = height;
        this.canMoved = canMoved;
        this.x = x;
        this.y = y;
        lastX = x;
        lastY = y;
        panel = new AnchorPane();
        panel.setLayoutX(x);
        panel.setLayoutY(y);
        panel.setPrefWidth(width);
        panel.setPrefHeight(height);
        panel.setStyle(commonStyle);
        updatedActionEvents();
        addNonUpdatableNode(panel);
    }
    public AnchorPane getMiniPanel() {
        return panel;
    }
    private void updateState () {
        if (isRelative) {
            panel.setPrefWidth(width / 100.0 * size.getWidth());
            panel.setPrefHeight(height / 100.0 * size.getHeight());
        }
        limiterCoordinates(this.x, this.y);
        panel.setLayoutX(x);
        panel.setLayoutY(y);
        //System.out.println("x - " + panel.getLayoutX() + ", y - " + panel.getLayoutY() + ". winWidth : " + GraphicProcessor.getter.getWidth() + " , winHeight : " + GraphicProcessor.getter.getHeight());
    }
    private void setLayouts () {
        double winWidth;
    }
    private void updatedActionEvents () {
        if (canMoved) {
            panel.setOnMousePressed((mouseEvent -> {
                mouseX = mouseEvent.getSceneX();
                mouseY = mouseEvent.getSceneY();
            }));
            panel.setOnMouseDragged(mouseEvent -> {
                double currentMouseX = mouseEvent.getSceneX();
                double currentMouseY = mouseEvent.getSceneY();
                double deltaX = currentMouseX - mouseX;
                double deltaY = currentMouseY - mouseY;
                limiterCoordinates(lastX + deltaX, lastY + deltaY);
            });
            panel.setOnMouseReleased((mouseEvent -> {
                //limiterCoordinates();
                lastX = x;
                lastY = y;
            }));
        } else {
            panel.setOnMousePressed(null);
            panel.setOnMouseDragged(null);
            panel.setOnMouseReleased(null);
        }
    }
    private void limiterCoordinates (double x, double y) {
        double winWidth = GraphicProcessor.getter.getWidth();
        double winHeight = GraphicProcessor.getter.getHeight();
        double fivePercentW = 0.05 * winWidth;
        double fivePercentH = 0.05 * winHeight;
        double ninetyFivePercentW = 0.95 * winWidth;
        double ninetyFivePercentH = 0.95 * winHeight;
        double currentWidth = panel.getWidth();
        double currentHeight = panel.getHeight();
        if (x + currentWidth >= fivePercentW && x <= ninetyFivePercentW) {
            this.x = x;
        } else {
            this.x = (x + currentWidth < fivePercentW) ? fivePercentW : ninetyFivePercentW ;
        }
        if (y + currentHeight > fivePercentH && y < ninetyFivePercentH) {
            this.y = y;
        } else {
            this.y = (y + currentHeight < fivePercentW) ? fivePercentH : ninetyFivePercentH ;
        }
    }
    @Override
    public void CustomUpdate() {
        updateState();
        super.update(panel.getWidth(), panel.getHeight(), x, y);
    }
    private static double getNormalWidth () {
        double winWidth = GraphicProcessor.getter.getWidth();
        return 0.3 * winWidth;
    }
    private static double getNormalHeight () {
        double winHeight = GraphicProcessor.getter.getHeight();
        return 0.3 * winHeight;
    }
    private static double getNormalX () {
        return 0.35 * GraphicProcessor.getter.getWidth();
    }
    private static double getNormalY () {
        return 0.35 * GraphicProcessor.getter.getHeight();
    }

    public void setRelativeSize (boolean b) {
        isRelative = b;
        updatedActionEvents();
    }
    public void setRelativeSizeAndChangeSize (boolean b) {
        double winWidth = size.getWidth();
        double winHeight = size.getHeight();
        double newWidth = width;
        double newHeight = height;
        if (b && !isRelative) {
            newWidth =  this.width * 100.0 / winWidth;
            newHeight = this.height * 100.0 / winHeight;
        } else if (!b && isRelative) {
            newWidth = this.width / 100.0 * winWidth;
            newHeight = this.height / 100.0 * winHeight;
        }
        if (isRelative != b) {
            this.width = newWidth;
            this.height = newHeight;
            isRelative = b;
        }
        updatedActionEvents();
    }
    public void setCanMoved(boolean canMoved) {
        this.canMoved = canMoved;
        updatedActionEvents();
    }
    public void setX(double x) {
        limiterCoordinates(x, this.y);
    }
    public void setY(double y) {
        limiterCoordinates(this.x, y);
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public void setHeight (double height) {
        this.height = height;
    }
    public interface Size {
        public double getWidth();
        public double getHeight();
    }
}
