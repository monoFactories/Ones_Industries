package core.configs.languages;

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
        try {
            bwr.write(language.getLanguageName());
            bwr.newLine();
            ConcurrentHashMap<String, ConcurrentHashMap<String, String>> languageMap = language.getLanguageParts();
            languageMap.forEach((s, chm) -> {
                try {
                    bwr.write(s);
                    bwr.newLine();
                    chm.forEach((s1, s2) -> {
                        if (s1 != null && s2 != null) {
                            try {
                                bwr.write(s1 + "=" + s2);
                                bwr.newLine();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception ex) {
            Game.log (Level.WARNING, "Exception when write language ", ex);
        }
    }
    public void close () throws IOException {
        bwr.close();
    }
}
