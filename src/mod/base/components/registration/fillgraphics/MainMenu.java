package mod.base.components.registration.fillgraphics;

import mod.base.components.constants.LanguageConstant;
import mod.base.components.constants.NameConstant;
import mod.base.components.constants.SpecialConstant;
import core.graphics.objects.GraphicComponent;
import core.graphics.objects.NodeParameter;
import static core.graphics.objects.NodeParameter.createRelativeSizeAndCoordinate;

import game_logic.Game;
import game_logic.GraphicManager;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

public class MainMenu {

    public static void createMainPane (GraphicComponent gc) {
        Label languageLabel = new Label(LanguageConstant.CURRENT_LANGUAGE);
        languageLabel.getStyleClass().add(NameConstant.MAIN_MENU_LANGUAGE_STYLE);
        languageLabel.setTextAlignment(TextAlignment.CENTER);
        languageLabel.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        gc.addNode(languageLabel, NodeParameter.createRelativeSizeAndCoordinate(82,89,13, 6));
        gc.getAnchorPane().getStyleClass().add(NameConstant.MAIN_MENU_STYLE);
        Pane border = new AnchorPane();
        border.getStyleClass().add(NameConstant.MAIN_MENU_BASE_STYLE);
        gc.addNode(border, createRelativeSizeAndCoordinate(39, 24, 22, 52));

        Button offline = new Button(LanguageConstant.MAIN_MENU_OFFLINE);
        offline.setOnAction(ae -> {
            GraphicManager.loadComponent(NameConstant.MENU_IDENTIFIER.MAIN_MENU, GraphicRegister.OFFLINE_PANE.getId());
        });
        offline.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        offline.getStyleClass().add (NameConstant.MAIN_MENU_SELECTOR_STYLE);
        gc.addNode(offline, createRelativeSizeAndCoordinate(40, 25, 20, 10));

        Button online = new Button (LanguageConstant.MAIN_MENU_ONLINE);
        online.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        online.getStyleClass().add(NameConstant.MAIN_MENU_SELECTOR_STYLE);
        gc.addNode(online, createRelativeSizeAndCoordinate(40, 35, 20, 10));

        Button settings = new Button (LanguageConstant.MAIN_MENU_SETTINGS);
        settings.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        settings.getStyleClass().add(NameConstant.MAIN_MENU_SELECTOR_STYLE);
        settings.setOnAction(ae -> {
            //GraphicManager.hideFromScreen(NameConstant.MAIN_MENU);
            //GraphicManager.addOnScreen(Base.getGlobalId(NameConstant.SETTING_MENU));
            GraphicManager.loadComponent(NameConstant.MENU_IDENTIFIER.MAIN_MENU, GraphicRegister.SETTINGS_MENU.getId());
        });
        gc.addNode(settings, createRelativeSizeAndCoordinate(40, 45, 20, 10));

        Button mods = new Button (LanguageConstant.MAIN_MENU_MODS);
        mods.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        mods.getStyleClass().add(NameConstant.MAIN_MENU_SELECTOR_STYLE);
        gc.addNode(mods, createRelativeSizeAndCoordinate(40, 55, 20, 10));

        Button exit = new Button(LanguageConstant.MAIN_MENU_EXIT);
        exit.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        exit.getStyleClass().add(NameConstant.MAIN_MENU_CANCEL_STYLE);
        exit.setOnAction(e -> Game.exit());
        gc.addNode(exit, createRelativeSizeAndCoordinate(40, 65, 20, 10));
    }
}
