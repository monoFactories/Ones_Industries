package core.configs.languages;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game_logic.Game;
import game_logic.repositories.LanguageRepository;

import java.io.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

public class LanguageInputStream  {
    public static String getName (InputStream is) {
        LanguageRepository repository = readLanguage(is);
        return repository == null ? null : repository.getLanguageName();
    }
    public static LanguageRepository readLanguage (InputStream is) {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LanguageRepository.class, LanguageJson.ADAPTER).create();
        try {
            return gson.fromJson (new InputStreamReader(is), LanguageRepository.class);
        } catch (Exception e) {
            return null;
        }
    }
}
