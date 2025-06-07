package game_logic;

import core.graphics.actions.ActionType;
import core.graphics.actions.GraphicAction;
import core.graphics.graphichandlers.GraphicProcessor;
import core.graphics.objects.GraphicComponent;
import game_logic.repositories.GraphicComponentsRepository;
import game_logic.repositories.Identifier;
import game_logic.repositories.ModsRepository;

import java.util.logging.Level;

public class GraphicManager {

    public static void addOnScreen (String scene) {
        GraphicComponentsRepository.addInGraphicProcessor(new Identifier(scene));
    }
    public static void deleteFromScreen (String s) {
        GraphicProcessor.Controller.delete(s);
    }
    public static void hideFromScreen (String s) {
        GraphicProcessor.Controller.hide(s);
    }
    public static void loadComponent(String currentComponent, String loaded) {
        hideFromScreen(currentComponent);
        addOnScreen(loaded);
    }
    //public static void clearScreen () {
    //    GraphicProcessor.Controller.clear();
    //}
}
