package game_logic.managers.controls;

import core.management.DualRepository;
import core.gameActions.Debug;
import core.graphics.graphichandlers.GraphicProcessor;
import game_logic.Game;
import game_logic.repositories.Identifier;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.util.function.Consumer;
import java.util.logging.Level;

public class ControlsExecutor extends DualRepository<Consumer<ControlContext>> {
    public static final ControlsExecutor executor = new ControlsExecutor();
    public void execute (Identifier fullID, ControlContext context) {
        try {
            Debug.debug("start find function for fullID: " + fullID);
            Consumer<ControlContext> c = get(fullID);
            if (c != null) {
                Debug.debug("function for fullID: \"" + fullID + "\" is: " + c + ", start running");
                c.accept(context);
            }
            Debug.debug("couldn't find function for id: " + fullID);
        } catch (Throwable th232) {
            Game.log (Level.INFO, "exception when launch control action", th232);
        }
    }
    public void addHandler () {
        GraphicProcessor.Controller.setMouseClick(mouseEvent -> {
            try {
                MouseButton selectedBtn = mouseEvent.getButton();
                Debug.debug("was clicked mouse button: " + selectedBtn.name());
                Identifier fullID = ControlModManager.controls.getActionIDFromBinds(new CurrentControl(selectedBtn));
                Debug.debug("current fullID for mouse button: \"" + selectedBtn + "\" is: " + fullID);
                if (fullID != null) {
                    ControlContext context = new ControlContext(mouseEvent);
                    execute(fullID, context);
                }
            } catch (Exception e) {
                Game.log(Level.INFO, "Exception when handling mouse event", e);
            }
        });
        GraphicProcessor.Controller.setKeyPress(keyEvent -> {
            try {
                KeyCode selectedBtn = keyEvent.getCode();
                //
                if (selectedBtn != null) {
                    Debug.debug("was clicked key button: " + selectedBtn.name());
                    Identifier fullID = ControlModManager.controls.getActionIDFromBinds(new CurrentControl(selectedBtn));
                    Debug.debug("current fullID for key button: \"" + selectedBtn + "\" is: " + fullID);
                    if (fullID != null) {
                        ControlContext context = new ControlContext(keyEvent);
                        execute(fullID, context);
                    }
                }
            } catch (Exception e) {
                Game.log(Level.INFO, "Exception when handling mouse event", e);
            }
        });
    }
}
