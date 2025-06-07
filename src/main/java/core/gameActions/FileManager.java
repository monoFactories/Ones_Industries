package core.gameActions;

import game_logic.Game;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;

public class FileManager {
    private static File directoryGame;
    private static boolean directoryOK = false;
    static {
        getPathToDirectory();
    }
    private static void getPathToDirectory() {
        URL url = FileManager.class.getProtectionDomain().getCodeSource().getLocation();
        try {
            File file = new File(url.toURI());
            directoryGame = file.getParentFile();
        } catch (URISyntaxException e) {
            Game.log(Level.WARNING ,"Error when load directory of game", e);
        }
    }
    public static File getDirectoryGame() {
        return directoryGame;
    }
    public static File concatToDirectory (String... add) {
        File returnedFile = directoryGame;
        for (String path : add) {
            if (path == null)
                throw new NullPointerException("one of parameters is null");
            else {
                returnedFile = new File(returnedFile, path);
            }
        }
        return returnedFile;
    }
    public static boolean existOrCreate (File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            boolean isCreate = parent.mkdirs();
            if (!isCreate) {
                Game.log(Level.WARNING, "couldn't create folders: " + parent.getPath(), null);
                return false;
            }
        }
        if (!file.exists()) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                Game.log(Level.INFO, "couldn't create file: " + file.getPath(), e);
                return false;
            }
        } else return true;
    }
}
