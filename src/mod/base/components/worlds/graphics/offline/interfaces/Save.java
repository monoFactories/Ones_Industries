package mod.base.components.worlds.graphics.offline.interfaces;

import mod.base.components.worlds.graphics.offline.files.LevelDescription;

import java.io.File;

public record Save(File file, LevelDescription description, long size, long lastEditDate) {
}
