package core.moding.loading;

import core.gameActions.FileManager;
import core.moding.mod.LoadingModParameter;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;

public abstract class DecentModsGetter {
    public abstract List<LoadingModParameter> getSortedMods();

    public static final DecentModsGetter constantDecentModsGetter = new DecentModsGetter() {
        @Override
        public List<LoadingModParameter> getSortedMods() {
            FileManager.FileAndCreateRecord datModsRecord = FileManager.createFileInDirectory("dat", "mods");
            FileManager.FileAndCreateRecord modsRecord = FileManager.createFileInDirectory("mods");
            if (datModsRecord.isCreated() && modsRecord.isCreated()) {
                FilenameFilter fnf = (file, name) -> file.isFile() && name.endsWith(".jar");
                File[] datModsFiles = datModsRecord.file().listFiles(fnf);
                File[] mods = modsRecord.file().listFiles(fnf);
                Map<String, LoadingModParameter> parameters = new HashMap<>();
                if (mods != null) {
                    for (File f : mods) {
                        LoadingModParameter parameter = ModInputStream.getModInputStream().readLoadingParameter(f);
                        if (parameter != null)
                            parameters.put(parameter.modName(), parameter);
                    }
                }
                if (datModsFiles != null) {
                    for (File f : datModsFiles) {
                        LoadingModParameter parameter = ModInputStream.getModInputStream().readLoadingParameter(f);
                        if (parameter != null)
                            parameters.put(parameter.modName(), parameter);
                    }
                }
                Set<String> wasLoaded = new HashSet<>();
                Set<String> needUpdate = new HashSet<>(parameters.keySet());
                boolean hasChange = true;

                List<LoadingModParameter> orderedModList = new ArrayList<>();

                while (hasChange) {
                    hasChange = false;
                    for (String c : needUpdate) {
                        LoadingModParameter lmp = parameters.get(c);
                        String[] dependencies = lmp.dependencies();
                        boolean allLoad = true;
                        for (String d : dependencies) {
                            if (!wasLoaded.contains(d)) {
                                allLoad = false;
                                break;
                            }
                        }
                        if (allLoad) {
                            orderedModList.add(parameters.get(c));
                            needUpdate.remove(c);
                            wasLoaded.add(c);
                            hasChange = true;
                        }
                    }
                }
                return orderedModList;
            }
            return new ArrayList<>();
        }
    };

    private static DecentModsGetter currentDecentModsGetter = constantDecentModsGetter;

    public static DecentModsGetter getModsGetter() {
        return currentDecentModsGetter;
    }

    public static void setCustomModsGetter(DecentModsGetter newCurrentDecentModsGetter) {
        if (newCurrentDecentModsGetter != null) {
            currentDecentModsGetter = newCurrentDecentModsGetter;
        }
    }
}



/*                  __________
                   /          \
                  /            \
                 /              \
                /    _______     \
               /     |     |      \
              /      =======       \
             /                      \
            /========================\
           /|                        |\
            |      ______________    |
            |      |     |     |     |
            |      |     |     |     |
            |      |     |     |     |
            |      |=====|=====|     |
            |      |     |     |     |
            |      |     |     |     |
            |      =============     |
            |                        |
            ==========================

     ADOLF HITLER 1933
      |     ======
      |     |
      |     |
      |===========|
            |     |
            |     |
       ======     |
 */