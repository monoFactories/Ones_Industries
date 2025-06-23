package mod.base.components.filling.fillgraphics.settings.controls;

import core.configs.languages.Language;
import core.management.DualRepository;
import core.moding.data.ControlRegister;
import core.moding.mod.Mod;
import game_logic.managers.LanguageModManager;
import game_logic.managers.controls.ControlModManager;
import game_logic.managers.controls.CurrentControl;
import game_logic.repositories.Identifier;
import game_logic.repositories.LanguageRepository;
import game_logic.repositories.ModsRepository;
import game_logic.repositories.controls.ConstantModControlRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ModControlsStorage {

//    public final TreeSet<String> translatedPartsName = new TreeSet<>();
//    public final ConcurrentHashMap<String, List<CurrentControlView>> translatedPartsStorage = new ConcurrentHashMap<>();

//    public ModControlsStorage (LanguageRepository language, ControlRegister constantControls, DualRepository.SingleRepository <CurrentControl> currentControls, String modName, ConcurrentHashMap <Identifier, List<CurrentControlView>> parts, TreeMap <String, TreeMap <String, Identifier>> sortedParts) {
//        generate(language, constantControls, currentControls, modName, parts, sortedParts);
//    }
    public static void generate (LanguageRepository language, ControlRegister constantControls, DualRepository.SingleRepository<CurrentControl> currentControls, String modName, ControlsStorage controlsStorage) {
        constantControls.forEach((str, controlVariable) -> {
            CurrentControl currentControl = currentControls.get (controlVariable.id());
            if (currentControl != null) {
                String name = LanguageModManager.translate(controlVariable.getName(), language);
                Identifier partIdentifier = controlVariable.getPartId();
                if (partIdentifier != null) {
                    ControlRegister.ControlPart controlPart = ConstantModControlRepository.parts.get (partIdentifier);
                    Mod thisPartMod = ModsRepository.get(partIdentifier.getSpace());
                    LanguageRepository thisPartLanguage = thisPartMod != null ? thisPartMod.modLanguage : language;
                    if (controlPart != null) {
                        controlsStorage.getParts().computeIfAbsent(partIdentifier, ID -> {
                            controlsStorage.getSortedParts().get(modName).put(LanguageModManager.translate (controlPart.getName(), thisPartLanguage), ID);
                            return new ArrayList<>();
                        }).add(new CurrentControlView(currentControl, controlVariable.id(), name));
                        return;
                    }
                }
                Identifier id = new Identifier("mod_EMPTY", modName);
                controlsStorage.getParts().computeIfAbsent(id, idKey -> {
                    controlsStorage.getSortedParts().get(modName).put(modName, idKey);
                    return new ArrayList<>();
                }).add(new CurrentControlView(currentControl, controlVariable.id(), name));
            }
        });




        //Set<String> modsSet = new HashSet<> ();
        //currentControls.forEach ((s, currentControl) -> modsSet.add(s));
        //Set<Identifier> noUsing = modsSet.stream().map(s -> new Identifier(modName, s)).collect(Collectors.toSet());
//
        //List<ControlRegister.ControlPart> parts = constantControls.getParts();
        //parts.forEach (p -> addPart(p, language, constantControls, modName, noUsing, true));
        //if (!noUsing.isEmpty()) {
        //    ControlRegister.ControlPart modPart = new ControlRegister.ControlPart (modName, noUsing.toArray(new Identifier[0]));
        //    addPart(modPart, language, constantControls, modName, noUsing, false);
        //}
        constantControls.forEach((str, controlVar) -> {
            Identifier identifier = controlVar.id();
            CurrentControl cc = ControlModManager.controls.get(identifier);
            if (cc != null) {

            }
        });
    }
    private void addPart (ControlRegister.ControlPart part, LanguageRepository language, ControlRegister constantControl, String modName, Set<Identifier> noUsing, boolean isRemoveFromSet) {
        //Identifier[] property = part.getProperty();
        //List<CurrentControlView> views = new ArrayList<>();
        //for (Identifier identifier : property) {
        //    DualRepository.SingleRepository<CurrentControl> repository = ControlModManager.controls.getEntry(modName);
        //    CurrentControl currentFromThis = repository.get (identifier);
        //    CurrentControl currentFromOther = ControlModManager.controls.get (identifier);
        //    boolean inThisMod = identifier.getSpace().equals(modName);
        //    String name = "empty";
        //    CurrentControl currentCopy = null;
        //    boolean flag_IsGood = true;
        //    if (currentFromOther != currentFromThis) {
        //        if (currentFromThis != null) {
        //            currentCopy = new CurrentControl(currentFromThis);
        //        } else {
        //            currentCopy = new CurrentControl(currentFromOther);
        //        }
        //    } else {
        //        if (currentFromOther != null) {
        //            name = constantControl.getNameFromID(identifier);
        //            currentCopy = new CurrentControl(currentFromThis);
        //        } else
        //            flag_IsGood = false;
        //    }
//
        //        if (inThisMod && isRemoveFromSet) {
        //            noUsing.remove(identifier);
        //        }
//
        //        //if (name != null) {
        //        //    name = /*LabelDescriptor.descriptor(language, name);*/ LanguageModManager.translate(name, language);
        //        //} else {
        //        //    name = "empty";
        //        //}
        //    if (flag_IsGood) {
        //        CurrentControlView cv = new CurrentControlView(currentCopy, identifier, name);
        //        views.add(cv);
        //    }
        //}
        //String name = LanguageModManager.translate(part.getName(), language);
        //translatedPartsName.add(name);
        //translatedPartsStorage.put(name, views);
    }
}
