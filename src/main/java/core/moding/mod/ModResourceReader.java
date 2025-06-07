package core.moding.mod;

import game_logic.Game;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ModResourceReader implements AutoCloseable {
    private final ZipFile JAR;
    public ModResourceReader (File file) {
        try {
            JAR = new ZipFile(file);
        } catch (IOException e) {
            Game.log(Level.WARNING, "Couldn't create ModResourceReader", e);
            throw new IllegalArgumentException();
        }
    }
    public InputStream getResource (String s) {
        try {
            ZipEntry z = JAR.getEntry(s);
            return JAR.getInputStream(z);
        }
        catch (IOException e) {
            Game.log(Level.WARNING, "couldn't read the resource", e);
        }
        return null;
    }
    public Enumeration<? extends ZipEntry> getModsEntries() {
        return JAR.entries();
    }
    @Override
    public void close() throws Exception {
        JAR.close();
    }
}
