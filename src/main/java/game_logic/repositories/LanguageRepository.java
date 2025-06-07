package game_logic.repositories;

import game_logic.Game;

import java.util.concurrent.ConcurrentHashMap;

public class LanguageRepository {
    private String languageName;
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, String>> languageParts;
    public LanguageRepository () {
        languageParts = new ConcurrentHashMap<>(5);
        languageName = "none";
    }
    public void setLanguagePart(String partName, ConcurrentHashMap<String, String> translatesMap) {
        if (partName == null || translatesMap == null)
            throw new NullPointerException("one of the parameter is null");
        else
            languageParts.put(partName, translatesMap);
    }
    public String get (String type, String gameWord) {
        String word;
        try {
            word = languageParts.get(type).get(gameWord);
        } catch (NullPointerException nullE) {
            return gameWord;
        }
        return word == null ? gameWord : word;
    }
    public String get (String typeWord) {
        if (typeWord != null) {
            String[] type_Word = typeWord.split(":", 2);
            if (type_Word.length >= 2) {
                return get(type_Word[0], type_Word[1]);
            }
        }
        return typeWord;
    }
    public void add (String type, String gameWord, String languageWord) {
        if (gameWord != null && languageWord != null) {
            try {
                languageParts.get(type).put(gameWord, languageWord);
            } catch (NullPointerException nullE) {
                Game.addLog("don't find part of language");
            }
        }
    }
    public void addAndCreatePart (String partName, String gameWord, String languageWord) {
        if (partName != null) {
            if (languageParts.get(partName) == null)
                setLanguagePart(partName, new ConcurrentHashMap<>());
            add(partName, gameWord, languageWord);
        }
    }
    public void setLanguageName(String languageName) {
        if (languageName != null)
            this.languageName = languageName;
    }
    public String getLanguageName() {
        return this.languageName;
    }

    public ConcurrentHashMap<String, ConcurrentHashMap<String, String>> getLanguageParts() {
        return languageParts;
    }

    /*public String checkPath (String path, boolean inJAR) {
    /    if (path == null)
    //        return null;
    //    String infoName = "/languageInfo.info";
    //    if (inJAR) {
    //        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
    //        try (ZipFile gameZip = new ZipFile(new File(url.toURI()))) {
    //            String infoPath = path + infoName;
    //            ZipEntry infoFile = gameZip.getEntry(infoPath);
    //            if (infoFile != null) {
    //                try (InputStream streamInfoFile = gameZip.getInputStream(infoFile);
    //                     BufferedReader readerInfoFile = new BufferedReader(new InputStreamReader(streamInfoFile))) {
    //                    String firstLine = readerInfoFile.readLine();
    //                    if (firstLine != null && !firstLine.trim().isEmpty())
    //                        return firstLine.trim();
    //                } catch (IOException io) {
    //                    Game.log(Level.WARNING, "couldn't read a infoFile with path:" + infoPath, io);
    //                }
    //            }
    //        } catch (Exception e) {
    //            Game.log(Level.WARNING, "couldn't verify the \"path\"", e);
    //        }
    //    } else {
    //        try {
    //            File pathFile = new File(path  + infoName);
    //            if (pathFile.exists() && pathFile.exists()) {
    //                File infoFile = new File(pathFile, infoName);
    //                if (infoFile.exists() && infoFile.isFile()) {
    //                    try (BufferedReader reader = new BufferedReader(new FileReader(infoFile))) {
    //                        String firstLine = reader.readLine();
    //                        if (firstLine != null && !firstLine.trim().isEmpty())
    //                            return firstLine;
    //                    } catch (IOException i) {
    //                        Game.log(Level.WARNING, "couldn't read a file: languageInfo.info", i);
    //                    }
    //                }
    //            }
    //        } catch (SecurityException s) {
    //            Game.log(Level.WARNING, "security error when working with a file", s);
    //        }
    //    }
        return null;
    }*/
}
