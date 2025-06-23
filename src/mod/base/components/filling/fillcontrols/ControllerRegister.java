package mod.base.components.filling.fillcontrols;

import core.graphics.graphichandlers.GraphicProcessor;
import core.management.ControlEntry;
import core.moding.data.ControlRegister;
import game_logic.managers.controls.ControlContext;
import game_logic.repositories.Identifier;
import javafx.scene.input.KeyCode;
import mod.base.components.constants.LanguageConstant;
import mod.base.components.constants.LocalIdentifierConstant;
import mod.base.modification.Base;

import java.util.function.Consumer;

public class ControllerRegister {
    public static final ControlRegister MOD_CONTROL_REGISTER = Base.getMod().getModRegister().controlRegister();

    public static final Identifier
            MOVE_PART_ID = Base.getID ("move_part"),
            INTERFACE_PART_ID = Base.getID("interface_part")
    ;
    public static final ControlRegister.ControlPart
            MOVE_PART = register(LanguageConstant.CONTROL.MOVE_PART, MOVE_PART_ID),
            INTERFACE_PART = register(LanguageConstant.CONTROL.INTERFACE_PART, INTERFACE_PART_ID)
    ;
    public static final ControlRegister.ControlVariable
            MOVE_UP = register(LocalIdentifierConstant.CONTROL_REGISTER.KEY_MOVE_UP, LanguageConstant.CONTROL.KEY_UP, null, MOVE_PART_ID, new ControlEntry(KeyCode.W)),
            MOVE_LEFT = register(LocalIdentifierConstant.CONTROL_REGISTER.KEY_MOVE_LEFT, LanguageConstant.CONTROL.KEY_LEFT, null, MOVE_PART_ID, new ControlEntry(KeyCode.A)),
            MOVE_DOWN = register(LocalIdentifierConstant.CONTROL_REGISTER.KEY_MOVE_DOWN, LanguageConstant.CONTROL.KEY_DOWN, null, MOVE_PART_ID, new ControlEntry(KeyCode.S)),
            MOVE_RIGHT = register(LocalIdentifierConstant.CONTROL_REGISTER.KEY_MOVE_RIGHT, LanguageConstant.CONTROL.KEY_RIGHT, null, MOVE_PART_ID, new ControlEntry(KeyCode.D)),
            INTERFACE_BACK = register(LocalIdentifierConstant.CONTROL_REGISTER.KEY_INTERFACE_BACK, LanguageConstant.CONTROL.KEY_BACK, context -> GraphicProcessor.Controller.back(), INTERFACE_PART_ID, new ControlEntry(KeyCode.DELETE))
    ;


    private static ControlRegister.ControlPart register (String name, Identifier id) {
        ControlRegister.ControlPart part = MOD_CONTROL_REGISTER.createPart(name, id);
        MOD_CONTROL_REGISTER.add(part, part.getIdentifier());
        return part;
    }
    private static ControlRegister.ControlVariable register (Identifier id, String name, Consumer<ControlContext> action, Identifier partId, ControlEntry ce) {
        ControlRegister.ControlVariable cv = MOD_CONTROL_REGISTER.createVariable(ce, name, id, action, partId);
        MOD_CONTROL_REGISTER.add(cv);
        return cv;
    }
    /*static ControlRepository cr = Base.modRepository.controls;
    public static void add () {
        add(KeyCode.W, "move_up", null);
        add(KeyCode.A, "move_left", null);
        add(KeyCode.S, "move_down", null);
        add(KeyCode.D, "move_right", null);
        cr.add(new ControlRepository.ControlPart("control:move", new String [] {
                "move_up", "move_left", "move_down", "move_right"
        }));
        cr.add(new ControlRepository.ControlPart("control:interface", new String[] {
            "move_left"
        }));
        add(KeyCode.ESCAPE, "exit", null);
        add(KeyCode.DELETE, "back", context -> {
            GraphicProcessor.Controller.back();
        });
    }
    static void add (KeyCode kc, String name, Consumer<ControlContext> act) {
        if (kc != null && name != null) {
            cr.add(new ControlRegister.ControlVariable(kc, "control:" + name, name, act));
        }
    }
    static void add (MouseButton kc, String name, Consumer<ControlContext> act) {
        if (kc != null && name != null) {
            cr.add( new ControlRepository.ControlVariable(kc, "control:" + name, name, act));
        }
    }*/
}
