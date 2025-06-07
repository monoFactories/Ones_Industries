package core.gameActions;

import core.configs.settings.SettingsHandler;
import game_logic.Game;

import java.util.function.Supplier;
import java.util.logging.Level;

public class Debug {
    private Debug() {
        throw new IllegalArgumentException();
    }
    public static void debug(String message) {
        write(Level.INFO, message, null);
    }
    public static void debug(Level lvl, String message) {
        write(lvl, message, null);
    }
    public static void debug(Supplier<String> message) {
        if (SettingsHandler.settings.getInfo().isDebugging())
            write(Level.INFO, message.get(), null);
    }
    public static void debug(Level lvl, Supplier<String> message) {
        if (SettingsHandler.settings.getInfo().isDebugging())
            write(lvl, message.get(), null);
    }
    public static void debug (Level lvl, String message, Throwable Al26) {
        write(lvl, message, Al26);
    }
    private static void write(Level lvl, String message, Throwable Ne15) {
        if (SettingsHandler.settings.getInfo().isDebugging())
            Game.log(lvl, message, Ne15);
    }
}
