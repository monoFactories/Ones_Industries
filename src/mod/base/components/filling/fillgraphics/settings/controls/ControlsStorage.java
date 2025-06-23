package mod.base.components.filling.fillgraphics.settings.controls;

import core.management.DualRepository;
import core.moding.data.ControlRegister;
import game_logic.managers.controls.ControlModManager;
import game_logic.managers.controls.CurrentControl;
import game_logic.repositories.Identifier;
import game_logic.repositories.LanguageRepository;
import game_logic.repositories.ModsRepository;

import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class ControlsStorage {
    //private final TreeSet <String> sortedNamesMods = new TreeSet<>();
    private final TreeMap <String, TreeMap <String, Identifier>> sortedParts = new TreeMap<>();
    private final ConcurrentHashMap <Identifier, List<CurrentControlView>> parts = new ConcurrentHashMap<>();

    public void generate () {
        ModsRepository.forEach((s, n) -> {
            sortedParts.put(s, new TreeMap<>());
            ControlRegister constantControls = n.getModRegister().controlRegister();
            LanguageRepository modLanguage = n.modLanguage;
            if (modLanguage == null)
                modLanguage = new LanguageRepository();
            DualRepository.SingleRepository<CurrentControl> currentControls = ControlModManager.controls.getEntry(s);
            ModControlsStorage.generate(modLanguage, constantControls, currentControls, s, this);

        });
    }
    public void clear () {

    }
    //public ConcurrentHashMap<String, ModControlsStorage> getModsStorage() {
    //    return modsStorage;
    //}
    public ConcurrentHashMap<Identifier, List<CurrentControlView>> getParts() {
        return parts;
    }
    public TreeMap<String, TreeMap<String, Identifier>> getSortedParts() {
        return sortedParts;
    }
}
