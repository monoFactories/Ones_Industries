package core.configs.languages;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Language {
    private final ConcurrentHashMap<String, String> english_lang;
    public Language () {
        this(new ConcurrentHashMap<String, String>());
    }
    public Language (ConcurrentHashMap<String, String> translates) {
        if (translates != null)
            english_lang = translates;
        else
            throw new NullPointerException("translates map cannot be null");
    }
    public String get (String englishWord) {
        String word = english_lang.get(englishWord);
        if (word == null) {
            return englishWord;
        }
        return word;
    }

    public ConcurrentHashMap<String, String> getEnglish_lang() {
        return english_lang;
    }
    //public static Map<String, Path> readLanguages(Path toLanguageFolder) throws IOException {
    //    Map<String, Path> stringPathMap = new HashMap<>();
    //    try {
    //        Files.walk(toLanguageFolder).filter(Files::isDirectory).filter((direct) -> {
    //            Path infoFile = direct.resolve("info.txt");
    //            return Files.exists(infoFile) && hasContent(infoFile);
    //        }).forEach((langDir -> {
    //            String languageName = readLanguageName(langDir.resolve("info.txt"));
    //            if (languageName != null)
    //                stringPathMap.put(languageName, langDir);
    //        }));
    //    } catch (IOException i) {
    //        Game.log(Level.WARNING, "couldn't read the file", i);
    //    }
    //    return stringPathMap;
    //}
    //private static boolean hasContent (Path path) {
    //    try {
    //        return Files.lines(path).anyMatch(line -> !line.trim().isEmpty());
    //    } catch (IOException io) {
    //        Game.log(Level.WARNING, "couldn't read the file", io);
    //        return false;
    //    }
    //}
    //private static String readLanguageName (Path path) {
    //    try {
    //        return Files.lines(path)
    //                .filter(line -> !line.trim().isEmpty())
    //                .findFirst()
    //                .orElse(null);
    //    } catch (IOException i) {
    //        Game.log(Level.WARNING, "couldn't read the file", i);
    //    }
    //    return null;
    //}
    @Override
    public String toString() {
        return "Language{" +
                ", english_lang=" + english_lang +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language language)) return false;
        return Objects.equals(english_lang, language.english_lang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(english_lang);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
