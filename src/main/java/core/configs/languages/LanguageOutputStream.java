package core.configs.languages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import core.gameActions.Debug;
import game_logic.Game;
import game_logic.repositories.LanguageRepository;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class LanguageOutputStream implements Closeable {
    private final BufferedWriter bwr;
    public LanguageOutputStream (String path) throws IOException {
        if (path == null) {
            throw new NullPointerException("path is null");
        }
        bwr = new BufferedWriter (new FileWriter(path));
    }
    public LanguageOutputStream (File pathFile) throws IOException {
        if (pathFile == null) {
            throw new NullPointerException("path is null");
        }
        bwr = new BufferedWriter (new FileWriter(pathFile));
    }
    public void writeLanguage (LanguageRepository language) {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LanguageRepository.class, LanguageJson.ADAPTER).create();
        try {
            bwr.write(gson.toJson(language));
        } catch (Exception e) {
            Debug.debug(Level.WARNING, "Exception when write language ", e);
        }
    }
    public void close () throws IOException {
        bwr.close();
    }
}
