package core.gameActions;

import core.configs.settings.SettingsHandler;
import game_logic.Game;

import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class Debug {
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
        if (SettingsHandler.settings == null)
            return;
        if (SettingsHandler.settings.getInfo().isDebugging()) {
            if (SettingsHandler.settings.getInfo().isDebugSaving())
                Game.log(lvl, message, Ne15);
            else
                Game.printLog(lvl, message, Ne15);
        }
    }
}
