package core.graphics.objects;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import game_logic.Game;

import java.util.HashMap;
import java.util.Map;

public class LoadMainMenu {
    public static GraphicComponent createMainMenu () {
        //Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
        //    GraphicProcessor.Controller.updateTableGameComponent();
        //}));
        //timeline.setCycleCount(Timeline.INDEFINITE);
        Map < Node, NodeParameter > allNodes = new HashMap<>();
        //ImageView cosmos = new ImageView(new Image(LoadMainMenu.class.getResourceAsStream("/Textures/Interface/mainMenu/menu.png")));
        Pane pane = new Pane();
        LinearGradient grayGradient = new LinearGradient(1, 0, 0, 1,true, null, new Stop(0, Color.LIGHTGRAY), new Stop(1, Color.WHEAT));
        pane.setBackground(new Background(new BackgroundFill(grayGradient, CornerRadii.EMPTY, null)));
        allNodes.put(pane, NodeParameter.createRelativeSizeAndCoordinate(100, 100, 100, 100));
        Button exit = new Button("exit");
        exit.setOnAction((actionEvent) -> {
            Game.exit();
        });
        allNodes.put(exit, NodeParameter.createRelativeSizeAndCoordinate(10, 85,10,4));
        return GraphicComponent.createGameComponent(allNodes, "main");
    }
}
