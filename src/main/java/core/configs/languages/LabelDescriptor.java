package core.configs.languages;

import core.gameActions.Debug;
import game_logic.managers.LanguageModManager;
import game_logic.repositories.LanguageRepository;
import game_logic.repositories.ModsRepository;

import java.util.HashMap;

public class LabelDescriptor {
    public static String descriptor (LanguageRepository rep, String constantName) {
        //System.out.println("запуск дескриптора для имени: " + constantName);
        Debug.debug("start description for:" + constantName);
        if (constantName == null)
            return null;
        char[] desk = constantName.toCharArray();
        boolean inPart = false;
        HashMap<Integer, Entry> pastes = new HashMap<>();
        int s = -1;
        StringBuilder contentEntry = new StringBuilder();
        for (int i = 0; i < desk.length; i++) {
            char c = desk[i];
            if (i != 0) {
                 if (c == ']' && desk[i - 1] == '/') {
                    if (inPart) {
                        contentEntry.append(c);
                        pastes.put(s, new Entry(s, i, contentEntry.toString()));
                        contentEntry.setLength(0);
                    }
                    inPart = false;
                }
                else if (c == '[' && desk[i - 1] == '/') {
                    if (inPart) {
                        pastes.put(s, new Entry(s, i - 2, contentEntry.toString()));
                        contentEntry.setLength(0);
                    }
                    inPart = true;
                    s = i - 1;
                }
                else if (inPart) {
                    contentEntry.append(c);
                }
            }
        }
        pastes.forEach((i, e) -> {
            //System.out.println ("текущий entry : " + e.content);
            e.content = e.content.replace("/[", "").replace("/]", "");
        });
        pastes.forEach((i ,e) -> {
            String translated = LanguageModManager.translate(e.content, rep);
            Debug.debug("translate for: " + e.content + " it is: " + translated);
            e.content = translated;//
        });
        StringBuilder newLine = new StringBuilder();
        for (int i = 0; i < desk.length; i++) {
            if (pastes.containsKey(i)) {
                Entry e = pastes.get(i);
                String e_content = e.content;
                if (e_content != null) {
                    newLine.append(e_content);
                    i = e.end;
                }
            } else {
                newLine.append(desk[i]);
            }
        }
        pastes.clear();
        char[] translatingArray = newLine.toString().toCharArray();
        newLine.setLength(0);
        boolean inArg = false;
        int startArg = -1;
        int endArg;
        StringBuilder currentArgument = new StringBuilder();
        for (int i = 0; i < translatingArray.length; i++) {
            char cur = translatingArray[i];
            if (i != 0) {
                if (inArg) {
                    currentArgument.append(cur);
                }
                if (cur == '{' && translatingArray[i - 1] == '/') {
                    if (inArg) {
                        endArg = i - 2;
                        pastes.put(startArg, new Entry(startArg, endArg, currentArgument.toString()));
                    }
                    currentArgument.setLength(0);
                    startArg = i - 1;
                    inArg = true;
                }
                else if (cur == '}' && translatingArray[i - 1] == '/') {
                    if (inArg) {
                        endArg = i;
                        pastes.put(startArg, new Entry(startArg, endArg, currentArgument.toString()));
                        inArg = false;
                    }
                }
            }
        }
        pastes.forEach((i, e) -> {
            e.content = e.content.replace("/{", "").replace("/}", "");
        });
        for (int i = 0; i < translatingArray.length; i++) {
            if (pastes.containsKey(i)) {
                String line = "";
                Entry e = pastes.get(i);
                if (e != null) {
                    if (e.content != null) {
                        switch (e.content) {
                            case "CURRENT_LANG" -> line = LanguageModManager.currentLanguage();
                            default -> line = e.content;
                        }
                    }
                    i = e.end;
                }
                newLine.append (line);
            } else {
                newLine.append(translatingArray[i]);
            }
        }
        return newLine.toString();
    }
    private static final class Entry {
        final int start;
        final int end;
        String content;
        Entry (int s, int e, String c) {
            start = s;
            end = e;
            content = c;
        }
    }
}
