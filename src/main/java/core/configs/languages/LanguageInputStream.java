package core.configs.languages;

import game_logic.Game;
import game_logic.repositories.LanguageRepository;

import java.io.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

public class LanguageInputStream {
    public static String getName (InputStream is) {
        String name = null;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            name = br.readLine();
            is.close();
        } catch (IOException i) {
            Game.log(Level.WARNING, "couldn't close the input-stream", i);
        }
        if (name == null)
            return null;
        name = name.trim().toLowerCase();
        return (!name.isEmpty() ? name : null);
    }
    public static LanguageRepository readLanguage (InputStream is) {
        LanguageRepository langRepository = new LanguageRepository();
        String name = null;
        try (BufferedReader read = new BufferedReader(new InputStreamReader(is))) {
            name = read.readLine();
            AtomicReference<String> selectedPart = new AtomicReference<>("null");
            read.lines().forEach((str) -> {
                String[] convertLines = lineConverter(str);
                if (convertLines.length == 1) {
                    selectedPart.set(convertLines[0]);
                }
                else if (convertLines.length == 2) {
                    langRepository.addAndCreatePart(selectedPart.get(), convertLines[0], convertLines[1]);
                }
            });
        } catch (IOException | NullPointerException io) {
            Game.log(Level.WARNING ,"couldn't read language", io);
        }
        try {
            is.close();
        } catch (IOException io) {
            Game.log(Level.WARNING, "couldn't close the input-stream", io);
        }
        langRepository.setLanguageName(name);
        return langRepository;
    }
    private static String[] lineConverter (String line) {
        StringBuilder builder0 = new StringBuilder();
        StringBuilder builder1 = new StringBuilder();
        boolean twoPart = false;
        char[] linesChars = line.toCharArray();
        for (char current : linesChars) {
            if (current == '=' && !twoPart) {
                twoPart = true;
                continue;
            }
            if (twoPart)
                builder1.append(current);
            else
                builder0.append(current);
        }
        if (!twoPart) {
            return new String[]{builder0.toString().trim()};
        }
        return new String[] {
            builder0.toString().trim(),
            builder1.toString().trim()
        };
    }
}
