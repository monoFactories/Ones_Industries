package mod.base.components.worlds.graphics.offline.interfaces;

import mod.base.components.worlds.graphics.offline.files.LevelDescription;

import java.io.File;

public class Save {
    public final File file;
    public final LevelDescription description;
    public final long size;
    public final long lastEditDate;
    public Save (File file, LevelDescription description, long size, long lastEditDate) {
        this.file = file;
        this.description = description;
        this.size = size;
        this.lastEditDate = lastEditDate;
    }
    public static void init() {}
}
