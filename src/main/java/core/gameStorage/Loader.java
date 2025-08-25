package core.gameStorage;

import core.configs.settings.SettingsHandler;
import core.gameActions.Debug;
import core.moding.loading.DecentModsGetter;
import core.moding.mod.LoadingModParameter;
import core.moding.loading.ModInputStream;
import game_logic.managers.controls.ControlModManager;
import game_logic.managers.LanguageModManager;
import game_logic.repositories.ModsRepository;

import java.util.List;

public class Loader {
    /// class for loading mods
    public static volatile boolean isRun = false;
    public static volatile String state = "initializing";
    public static double percentOfLoad = 0.0;

    public static void loadingMods () {
        /* File mods = FileManager.concatToDirectory("mods");
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
                        Mod mod = mis.getMod(m);
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
        } */

        DecentModsGetter getter = DecentModsGetter.getModsGetter();
        ModInputStream mis = ModInputStream.getModInputStream();
        List<LoadingModParameter> mods = getter.getSortedMods();
        int ma = mods.size();
        System.out.println("start cycle: i = 0; max = " + ma);
        for (int i = 0; i < ma; i++) {
            DecentModsGetter newGetter = DecentModsGetter.getModsGetter();
            if (getter != newGetter) {
                getter = newGetter;
                mods = getter.getSortedMods();
                i = 0;
                ma = mods.size();
            }
            ModInputStream newMis = ModInputStream.getModInputStream();
            if (mis != newMis)
                mis = newMis;
            LoadingModParameter lmp = mods.get(i);
            System.out.println("current lmp: " + lmp);
            percentOfLoad = 75.0 * (i + 1.0) / ma;
            ModsRepository.add(mis.getMod(lmp.file()));
        }
        int count = ModsRepository.getModsCount();
        System.out.println("count mods for running: " + count);
        if (count == 0)
            percentOfLoad = 100.0;
        else {
            int[] a = {0};
            ModsRepository.forEach((s, m) -> {
                a[0]++;
                state = "running mod : " + m.getParameter().getName();
                Debug.debug("start run mod: " + s);
                m.run();
                Debug.debug("end run mod: " + s);
                percentOfLoad = 75.0 + (double) a[0] / count;
            });
        }
        Debug.debug("start set language");
        LanguageModManager.setLanguage(SettingsHandler.settings.getInfo().getLastLanguage());
        Debug.debug("start loading controls from file");
        ControlModManager.load();
        Debug.debug("start rebinding controls");
        ControlModManager.controls.rebinds();
        isRun = true;
    }
}
