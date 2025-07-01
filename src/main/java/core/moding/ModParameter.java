package core.moding;

import core.gameActions.Debug;
import game_logic.Game;
import game_logic.repositories.LanguageRepository;
import core.configs.languages.LanguageInputStream;
import core.moding.mod.ModResourceReader;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public final class ModParameter {
    private final File pathToArchive;
    private final String modName;
    private final HashMap<String, String> langPathsTable;
    public ModParameter (String name, File jar) {
        this.modName = name;
        this.pathToArchive = jar;
        this.langPathsTable = new HashMap<>();
    }
    public ModResourceReader createModResourceReader () {
        return new ModResourceReader(pathToArchive);
    }
    public String getName () {
        return this.modName;
    }

    public LanguageRepository loadLanguage (String languageName) {
        languageName = languageName.toLowerCase();
        try (ZipFile modZip = new ZipFile(pathToArchive)) {
            if (langPathsTable.containsKey(languageName)) {
                String path = langPathsTable.get(languageName);
                ZipEntry langEntry = modZip.getEntry(path);
                if (langEntry != null) {
                    return LanguageInputStream.readLanguage(modZip.getInputStream(langEntry));
                }
            } else {
                updateLangPathsTable();
                if (langPathsTable.containsKey(languageName)) {
                    String path = langPathsTable.get(languageName);
                    ZipEntry langEntry = modZip.getEntry(path);
                    if (langEntry != null) {
                        return LanguageInputStream.readLanguage(modZip.getInputStream(langEntry));
                    }
                }
            }
        } catch (IOException ie) {
            Game.log(Level.WARNING, "error when read and load language", ie);
        }
        return new LanguageRepository();
    }
    private void updateLangPathsTable () {
        langPathsTable.clear();
        try (ZipFile modFile = new ZipFile(pathToArchive)) {
            Enumeration<? extends ZipEntry> entries = modFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry current = entries.nextElement();
                if (current != null) {
                    if (current.getName().endsWith(".lang")) {
                        String name = LanguageInputStream.getName(modFile.getInputStream(current));
                        Debug.debug (() -> "Mod [" + getName() + "] have language with name: " + name);
                        String path = current.getName();
                        if (name != null) {
                            langPathsTable.put(name.toLowerCase(), path);
                        }
                    }
                }
            }
        } catch (IOException io) {
            Game.log(Level.WARNING, "Couldn't use mod archive", io);
        }
    }
    public Set<String> getListLanguages () {
        updateLangPathsTable();

        return langPathsTable.keySet();
    }
}
