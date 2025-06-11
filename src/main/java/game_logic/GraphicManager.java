package game_logic;

import core.graphics.graphichandlers.GraphicProcessor;
import game_logic.repositories.GraphicComponentsRepository;
import game_logic.repositories.Identifier;

public class GraphicManager {

    public static void addOnScreen (Identifier scene) {
        GraphicComponentsRepository.addInGraphicProcessor(scene);
    }
    public static void deleteFromScreen (Identifier s) {
        GraphicProcessor.Controller.delete(s);
    }
    public static void hideFromScreen (Identifier s) {
        GraphicProcessor.Controller.hide(s);
    }
    public static void loadComponent(Identifier currentComponent, Identifier loaded) {
        hideFromScreen(currentComponent);
        addOnScreen(loaded);
    }
    //public static void clearScreen () {
    //    GraphicProcessor.Controller.clear();
    //}
}
