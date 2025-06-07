package core.gameStorage;

import core.configs.settings.SettingsHandler;
import core.gameActions.Debug;
import core.gameActions.FileManager;
import core.moding.mod.Mod;
import core.moding.mod.ModInputStream;
import game_logic.Game;
import game_logic.managers.controls.ControlModManager;
import game_logic.managers.LanguageModManager;
import game_logic.repositories.ModsRepository;

import java.io.File;
import java.util.logging.Level;

public class Loader {
    /// class for loading mods
    public static volatile boolean isRun = false;
    public static volatile String state = "initializing";
    public static double percentOfLoad = 0.0;

    public static void loadingMods () {
        File mods = FileManager.concatToDirectory("mods");
        File[] jars = mods.listFiles((f) -> f.exists() && f.isFile() && f.canRead() && f.getName().endsWith(".jar"));
        if (jars != null) {
            try (ModInputStream mis = new ModInputStream()) {
                int count = jars.length;
                if (count == 0)
                    percentOfLoad = 25.0;
                else {
                    double deltaP = 25.0 / count;
                    Game.addLog("start loading mods, count: " + count);
                    for (File m : jars) {
                        String name = m.getName();
                        percentOfLoad += deltaP;
                        state = "loading mod : " + name;
                        long start = System.nanoTime();
                        Mod mod = mis.getMode(m);
                        if (mod == null)
                            continue;
                        Debug.debug("running code for loading other mods after load: " + mod.getParameter().getName());
                        long end = System.nanoTime();
                        ModsRepository.add(mod);
                        long end1 = System.nanoTime();
                        Game.addLog("mod: " + mod.getParameter().getName() + " is loading, total time: " + ((end - start) / 1000_000.0) + " ms\n" + "running for time: " + ((end1 - end) / 1000_000.0) + "ms");
                    }
                }
            } catch (Exception e) {
                Game.log(Level.WARNING, "couldn't load mod", e);
            }
        }
        int count = ModsRepository.getModsCount();
        System.out.println("count mods for running: " + count);
        //AtomicInteger c = new AtomicInteger();
        if (count == 0)
            percentOfLoad = 100.0;
        double deltaPercent = 75.0 / count;
        ModsRepository.forEach((s, m) -> {
            state = "running mod : " + m.getParameter().getName();
            Debug.debug("start run mod: " + s);
            m.run();
            Debug.debug("end run mod: " + s);
            percentOfLoad += deltaPercent;
        });
        Debug.debug("start set language");
        LanguageModManager.setLanguage(SettingsHandler.settings.getInfo().getLastLanguage());
        Debug.debug("start loading controls from file");
        ControlModManager.load();
        Debug.debug("start rebinding controls");
        ControlModManager.controls.rebinds();
        isRun = true;
    }
}
