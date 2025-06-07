package game_logic.repositories;

import game_logic.Game;
import core.moding.mod.Mod;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.logging.Level;

public class ModsRepository {
    private static final ConcurrentHashMap <String, Mod> mods = new ConcurrentHashMap<>();

    public static void add (Mod mod) {
        if (mod != null) {
            String name = mod.getParameter().getName();
            if (!mods.containsKey(name)) {
                mods.put(name, mod);
            } else {
                Game.log(Level.WARNING, "The name of this mod is already present in the game", null);
            }
        }
    }
    public static Mod get (String name) {
        return name != null ? mods.get(name) : null;
    }
    public static void forEach (BiConsumer<String, Mod> action) {
        mods.forEach(action);
    }
    public static int getModsCount() {
        return mods.size();
    }
}
