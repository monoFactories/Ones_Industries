package mod.base.components.worlds.graphics.offline.files;

import mod.base.modification.Base;

import java.io.File;

public class OfflineFileChecker {
    public static void init () {
        File saves = Base.files.unitedFile(SavesManager.pathToSaves);
    }
}
