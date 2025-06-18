package mod.base.components.filling.fillgraphics;

import core.graphics.objects.GraphicComponent;
import game_logic.managers.controls.ControlsExecutor;
import mod.base.components.constants.NameConstant;
import mod.base.components.filling.fillgraphics.settings.SettingComponent;
import mod.base.components.filling.fillgraphics.settings.controls.ControlMenu;
import mod.base.components.filling.fillgraphics.settings.graphics.GraphicMenu;
import mod.base.components.filling.fillgraphics.settings.languages.LanguagePane;
import mod.base.components.filling.fillgraphics.settings.sounds.SoundMenu;
import mod.base.components.worlds.graphics.offline.interfaces.OfflineMenu;
import mod.base.modification.Base;
import mod.base.modification.graphics.components.SoundsGraphicComponent;

import java.util.function.Consumer;

public final class GraphicRegister {

    public static final GraphicComponent MAIN_PANE = register (new SoundsGraphicComponent(NameConstant.MENU_IDENTIFIER.MAIN_MENU) {@Override public void onAddFromScreen() {ControlsExecutor.executor.addHandler();}@Override public void back() {}}, MainMenu::createMainPane),
    SETTINGS_MENU = register(new SettingComponent(NameConstant.MENU_IDENTIFIER.SETTING_MENU)),
    SETTINGS_LANGUAGE_MENU = register(new LanguagePane(NameConstant.MENU_IDENTIFIER.SETTINGS_LANGUAGE_MENU)),
    SETTINGS_GRAPHIC_MENU = register(new GraphicMenu(NameConstant.MENU_IDENTIFIER.SETTINGS_GRAPHIC_MENU)),
    SETTINGS_CONTROL_MENU = register(new ControlMenu(NameConstant.MENU_IDENTIFIER.SETTINGS_CONTROL_MENU)),
    SETTINGS_SOUND_MENU = register(new SoundMenu(NameConstant.MENU_IDENTIFIER.SETTINGS_SOUND_MENU)),
    OFFLINE_PANE = register(new OfflineMenu(NameConstant.MENU_IDENTIFIER.OFFLINE_MENU))
    ;


    private static GraphicComponent register (GraphicComponent graphicComponent, Consumer<GraphicComponent> function)  {
        if (graphicComponent != null) {
            if (function != null) {
                function.accept (graphicComponent);
            }
            Base.getMod().modRepository.graphicRegister().add(graphicComponent);
            return graphicComponent;
        }
        return null;
    }
    private static GraphicComponent register (GraphicComponent graphicComponent)  {
        return register(graphicComponent, null);
    }

}
