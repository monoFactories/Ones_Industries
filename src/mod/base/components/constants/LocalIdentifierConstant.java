package mod.base.components.constants;

import game_logic.repositories.Identifier;
import mod.base.modification.Base;

public final class LocalIdentifierConstant {
    public final static class CONTROL_REGISTER {
        public static final Identifier
        KEY_MOVE_UP = getLoc("move_up"),
        KEY_MOVE_LEFT = getLoc("move_left"),
        KEY_MOVE_DOWN = getLoc("move_down"),
        KEY_MOVE_RIGHT = getLoc("move_right"),
        KEY_INTERFACE_EXIT = getLoc("interface_exit"),
        KEY_INTERFACE_BACK = getLoc("interface_back")
        ;
    }
    private static Identifier getLoc (String local) {
        return Base.getID(local);
    }
}
