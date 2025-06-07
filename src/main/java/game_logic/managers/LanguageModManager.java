package game_logic.managers;

import core.configs.settings.SettingsHandler;
import game_logic.Game;
import game_logic.repositories.LanguageRepository;
import game_logic.repositories.ModsRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class LanguageModManager {

    private static String language = "english";

    public static void setLanguage (String s) {
        Game.addLog("translating on: " + s);
        language = s.toLowerCase();
        SettingsHandler.setLastLanguage(language);
        ModsRepository.forEach((n, m) -> m.setLanguage(language));
    }
    public static String currentLanguage () {
        return language;
    }
    public static String translate (String ModPartId) {
        String[] abstractPath = ModPartId.split(":", 3);
        if (abstractPath.length >= 3) {
            try {
                return ModsRepository.get(abstractPath[0]).modLanguage.get(abstractPath[1], abstractPath[2]);
            } catch (NullPointerException n) {
                Game.log(Level.INFO, "Couldn't translate : \"" + ModPartId + "\"", n);
            }
        }
        return ModPartId;
    }
    public static String translate (String toTranslate, LanguageRepository language) {
        if (toTranslate != null) {
            String[] split = toTranslate.split(":", 3);
            int length = split.length;
            String result = toTranslate;
            if (length >= 3) {
                LanguageRepository otherLang = new LanguageRepository();
                try {
                    otherLang = ModsRepository.get(split[0]).modLanguage;
                } catch (NullPointerException nul) {
                    System.out.println("don't find mod for translate: " + toTranslate);
                }
                result = otherLang.get(split[1], split[2]);
            } else if (length == 2) {
                result = language.get(split[0], split[1]);
            }
            return result;
        }
        return "EMPTY";
    }
    public static Set<String> getLanguagesSet () {
        HashSet<String> toReturn = new HashSet<>();
        ModsRepository.forEach((n, m) -> m.getLanguages().stream().map(String::toLowerCase).forEach(toReturn::add));
        return toReturn;
    }
}
