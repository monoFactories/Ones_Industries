package mod.base.components.worlds.graphics.offline.files;

import mod.base.components.logics.settings.BaseSettingsHandler;
import mod.base.components.worlds.graphics.offline.interfaces.Save;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import core.gameActions.Debug;
import game_logic.Game;
import game_logic.repositories.ModsRepository;
import mod.base.modification.Base;
import mod.base.modification.utils.lines.LinesAction;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public final class SavesManager {

    public static final String pathToSaves = "saves",
    SAVE_ARCHIVE_EXTENSION = ".mfar",
    DESCRIPTION_FILE = "LevelDescription.json"
    ;

    public static List<Save> saves () {
        File savesDirectory = Base.files.unitedFile(pathToSaves);
        File[] saves = savesDirectory.listFiles();
        if (saves != null) {
            List<Save> result = new ArrayList<>();
            for (File save : saves) {
                LevelDescription description = null;
                String fileName = save.getName();
                if (save.isDirectory()) {
                    File desc = new File(save, DESCRIPTION_FILE);
                    if (desc.exists() && desc.isFile()) {
                        try (Reader read = new FileReader(desc)) {
                            try {
                                description = new Gson().fromJson(read, LevelDescription.class);
                            } catch (JsonParseException jpe) {
                                Debug.debug("couldn't deserialize levelDescription, for file \"" + desc + "\"");
                            }
                        } catch (IOException io) {
                            Debug.debug("Couldn't read file \"" + desc + "\"");
                        }
                    }
                } else if (save.isFile() && fileName.endsWith(SAVE_ARCHIVE_EXTENSION)) {
                    try (ZipFile archive = new ZipFile(save)) {
                        ZipEntry desc = archive.getEntry(DESCRIPTION_FILE);
                        if (desc != null) {
                            try (Reader read = new InputStreamReader(archive.getInputStream(desc))) {
                                Gson gson = new Gson();
                                try {
                                    description = gson.fromJson(read, LevelDescription.class);
                                } catch (JsonParseException jsonExc) {
                                    Debug.debug("couldn't deserialize levelDescription, for archive \"" + save + "\"");
                                }
                            }
                        }
                    } catch (IOException io) {
                        Debug.debug("couldn't read archive \"" + save + "\"");
                    }
                }
                if (description != null) {
                    long lastModification = save.lastModified();
                    long size = FileUtils.sizeOfDirectory(save);
                    result.add(new Save(save, description, size, lastModification));
                }
            }
            return result;
        }
        return new ArrayList<>();
    }
    public static void addSave (String name) {
        boolean inArchive = BaseSettingsHandler.getBaseSettings().getSave().savingInArchive;
        if (name == null)
            return;
        List<String> modsList = new ArrayList<>();
        ModsRepository.forEach((s, m) -> modsList.add(s));
        String[] mods = modsList.toArray(new String[0]);
        LevelDescription description = new LevelDescription(name, mods);
        File saves = Base.files.unitedFile(pathToSaves);
        if (inArchive) {
            File archiveFile = new File(saves, name + SAVE_ARCHIVE_EXTENSION);
            archiveFile = editFileName(archiveFile);
            try {
                boolean b = archiveFile.createNewFile();
                if (b)
                    Debug.debug("create new file \"" + archiveFile + "\"");
                try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(archiveFile))) {
                    ZipEntry desc = new ZipEntry(DESCRIPTION_FILE);
                    zos.putNextEntry(desc);
                    Gson g = new Gson();
                    zos.write(g.toJson(description).getBytes());
                    zos.closeEntry();
                } catch (Exception e) {
                    Debug.debug("Exception when write in file");
                }
            } catch (IOException i) {
                Debug.debug("Couldn't create new file");
            }
        } else {

        }
    }
    public static void deleteSave (String name) {
        Debug.debug("\ndeleting save: " + name);
        File save = new File(Base.files.unitedFile(pathToSaves), name);
        if (save.exists()) {
            if (save.isDirectory()) {
                try {
                    FileUtils.deleteDirectory(save);
                } catch (SecurityException | IOException sec) {
                    Game.log(Level.WARNING, "exception when delete save \"" + name + '\"', sec);
                }
            } else {
                try {
                    boolean b = save.delete();
                    Debug.debug((b ? "successful deletion" : "failed deletion") + ": " + save);
                } catch (SecurityException sec) {
                    Game.log(Level.WARNING, "exception when delete save \"" + name + '\"', sec);
                }
            }
        } else {
            Debug.debug("can not find save for path \"" + save + "\"");
        }
    }
    private static File editFileName (File target) {
        while (target.exists()) {
            File parent = target.getParentFile();
            String name = target.getName();
            target = new File(parent, LinesAction.iterateName(name));
        }
        return target;
    }
}
