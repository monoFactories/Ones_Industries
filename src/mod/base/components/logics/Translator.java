package mod.base.components.logics;

import core.configs.languages.LabelDescriptor;
import game_logic.repositories.ModsRepository;
import mod.base.modification.Base;

public class Translator {
    public static String reTranslateLabel (String text) {
        try {
            return LabelDescriptor.descriptor(ModsRepository.mods.get(Base.parameter.getName()).modLanguage, text);
        } catch (Exception e) {
            return text;
        }
    }
}
