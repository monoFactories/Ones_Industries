package mod.base.components.logics;

import core.gameActions.Debug;
import core.graphics.graphichandlers.GraphicProcessor;
import mod.base.components.filling.fillgraphics.settings.SettingMenu;
import game_logic.Game;

import java.util.logging.Level;

public final class Style {
    public static void exec () {
        //List<String> fonts = Font.getFamilies();
        //System.out.println("доступные шрифты");
        //fonts.forEach(System.out::println);
        long start = System.nanoTime();
        try {
            regB("cancel_button");
            regB("main_menu_selectors");
            regB("settings_menu");
            regB("confirm_button");
            regP("setting_pane");
            regL("settings_language_label");
            regP("main_menu");
            regP("language_list");
            regL("main_menu_language");
            regM("graphic_menu");
            regM ("control_menu");
            regM("sound_menu");
            regM("offline_menu");
        } catch (Exception e) {
            System.out.println("not correct in style");
            Game.log(Level.INFO, "error initializing the style", e);
        }
        long end = System.nanoTime();
        Debug.debug("\n\n\n\nreal time style initialization: " + ((end - start) / 1_000_000.0) + " ms\n\n\n");
    }
    private static void regB (String s) {
        GraphicProcessor.Controller.addCSS(SettingMenu.class.getResource("/resources/styles/buttons/" + s + ".css").toExternalForm());
    }
    private static void regP (String s) {
        GraphicProcessor.Controller.addCSS(SettingMenu.class.getResource("/resources/styles/panes/" + s + ".css").toExternalForm());
    }
    private static void regL (String s) {
        GraphicProcessor.Controller.addCSS(SettingMenu.class.getResource("/resources/styles/labels/" + s + ".css").toExternalForm());
    }
    private static void regM (String s) {
        GraphicProcessor.Controller.addCSS(SettingMenu.class.getResource("/resources/styles/menu/" + s + ".css").toExternalForm());
    }
}
