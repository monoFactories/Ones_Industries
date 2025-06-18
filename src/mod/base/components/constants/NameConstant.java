package mod.base.components.constants;

import game_logic.repositories.Identifier;
import mod.base.modification.Base;

public final class NameConstant {

    private NameConstant () {
        throw new UnsupportedOperationException("its class for constant");
    }

    public static final class MENU_IDENTIFIER {
        ///menu
        public static final Identifier
        MAIN_MENU = local("main_menu"),
        SETTING_MENU = local("setting_menu"),
        SETTINGS_LANGUAGE_MENU = local("settings_language_menu"),
        SETTINGS_GRAPHIC_MENU = local("graphics_settings_menu"),
        SETTINGS_CONTROL_MENU = local("settings_control_menu"),
        SETTINGS_SOUND_MENU = local("settings_sound_menu"),
        OFFLINE_MENU = local("offline_menu"),
        OFFLINE_MENU_CONFIRM_MENU = local("offline_menu:confirm_panel"),
        OFFLINE_MENU_INFO_MENU = local("offline_menu:info_panel)"
        ;
    }
    ///styles
    public static final String
    SETTING_PANE_STYLE = "setting-pane",
    MAIN_MENU_SELECTOR_STYLE = "main-menu-selectors",
    SETTING_MENU_SELECTOR_STYLE = "settings-selector",
    SETTING_LANGUAGE_LABEL_STYLE = "settings-language-label",
    MAIN_MENU_STYLE = "main-menu-pane",
    MAIN_MENU_BASE_STYLE = "main-menu-core",
    MAIN_MENU_CANCEL_STYLE = "cancel-button",
    LANGUAGE_LIST_VIEW_STYLE = "language-list",
    MAIN_MENU_LANGUAGE_STYLE = "main-menu-language",
    GRAPHIC_MENU_STYLE = "graphic-menu",
    GRAPHIC_MENU_COMPLETE_STYLE = "graphic-menu-complete-selector",
    CONFIRM_STYLE = "confirm-button",
    GRAPHIC_MENU_LABEL_FIELDS_STYLE = "graphic-menu-label-field",
    GRAPHIC_MENU_FULLSCREEN_CHECKBOX_STYLE = "fullscreen-checkbox";
    ;
    private static Identifier local (String local) {
        return Base.getID (local);
    }
}
