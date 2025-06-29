package mod.base.components.constants;

public final class LanguageConstant {
    private LanguageConstant () {
        throw new UnsupportedOperationException("it's class only for constant");
    }
    /// graphic
    public static final String
    LANGUAGE_SELECTOR = "/[interface:settings_language_selector/]",
    GRAPHIC_SELECTOR = "/[interface:settings_graphic_selector/]",
    CURRENT_LANGUAGE = "/{CURRENT_LANG/}",
    SOUND_SELECTOR = "/[interface:settings_sound_selector/]",
    CONTROLS_SELECTOR = "/[interface:settings_controls_selector/]",
    /// main menu
    MAIN_MENU_OFFLINE = "/[interface:main_menu_offline_game_selector/]",
    MAIN_MENU_ONLINE = "/[interface:main_menu_online_game_selector/]",
    MAIN_MENU_SETTINGS = "/[interface:main_menu_settings_selector/]",
    MAIN_MENU_MODS = "/[interface:main_menu_mods/]",
    MAIN_MENU_EXIT = "/[interface:exit/]"
    ,
    /// other
    COMPLETE  = "/[interface:complete/]"
    ,
    /// graphic settings
    GRAPHIC_FULLSCREEN_MESSAGE = "/[interface:graphic_fullscreen/]",
    BRIGHTNESS = "/[interface:graphic_brightness/]",
    CONTRAST = "/[interface:graphic_contrast/]",
    SATURATION = "/[interface:graphic_saturation/]",
    WIDTH = "/[interface:graphic_width/]",
    HEIGHT = "/[interface:graphic_height/]",
    FRAME_RATE = "/[interface:frame_rate/]",
    FPS = "/[interface:fps/]",
    GRAPHIC_MENU_CONFIRM = "/[interface:confirm/]",
    GRAPHIC_MENU_APPLY = "/[interface:apply/]",
    GRAPHIC_MENU_CANCEL = "/[interface:cancel/]"
    ;
    public static final class SOUND_SETTINGS {
        public static final String
        TRANSPORT = "/[sounds:transport/]",
        MECHANISMS = "/[sounds:mechanisms/]",
        OVERALL = "/[sounds:overall/]",
        OTHER = "/[sounds:other/]",
        MUSIC = "/[sounds:music/]",
        INTERFACE = "/[sounds:interface/]"
        ;
    }
    public static final class OFFLINE_MENU {
        public static final String
        BACK = "/[offline_interface:back/]",
        OFFLINE_GAME = "/[offline_interface:offline_game/]",
        DELETE = "/[offline_interface:delete/]",
        CONFIRM_DELETION = "/[offline_interface:confirm_deletion/]",
        PATH_TO_FILE = "/[offline_interface:path_to_file/]",
        SAVED = "/[offline_interface:saved/]",
        CREATE_NEW_GAME = "/[offline_interface:start_new_game/]",
        MODS = "/[offline_interface:mods/]",
        FILE_SIZE = "/[offline_interface:file_size/]",
        PLAY = "/[offline_interface:play/]"
        ;
    }
    public static final class NEW_GAME_MENU {
        public static final String
        BACK = "/[offline_new_game_menu:cancel/]",
        NAME = "/[offline_new_game_menu:name/]",
        PLAY = "/[offline_new_game_menu:play/]",
        MAIN = "/[offline_new_game_menu:main/]",
        SEED = "/[offline_new_game_menu:seed/]"
        ;
    }
    public static final class CONTROL {
        public final static String
        MOVE_PART = "control:move",
        KEY_UP = "control:move_up",
        KEY_LEFT = "control:move_left",
        KEY_DOWN = "control:move_down",
        KEY_RIGHT = "control:move_right",
        INTERFACE_PART = "control:interface",
        KEY_EXIT = "control:exit",
        KEY_BACK = "control:back"
        ;/*

move=move
move_up=up
move_left=left
move_down=down
move_right=right
exit=exit
interface=interface
back=back
        */
    }
}
