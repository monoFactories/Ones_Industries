package mod.base.components.filling.fillgraphics.settings;

import game_logic.repositories.Identifier;
import mod.base.components.constants.LanguageConstant;
import mod.base.components.constants.NameConstant;
import mod.base.components.constants.SpecialConstant;
import core.configs.settings.SettingsHandler;
import game_logic.GraphicManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import mod.base.components.filling.fillgraphics.GraphicRegister;
import mod.base.modification.Base;
import mod.base.modification.graphics.components.SoundsGraphicComponent;

import static core.graphics.objects.NodeParameter.*;

public class SettingComponent extends SoundsGraphicComponent {

    public SettingComponent(Identifier id) {
        super(id);
        AnchorPane thisPane = (AnchorPane) getAnchorPane();
        thisPane.getStyleClass().add(NameConstant.SETTING_PANE_STYLE);
        Button complete = new Button(LanguageConstant.COMPLETE);
        complete.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        complete.getStyleClass().add(NameConstant.SETTING_MENU_SELECTOR_STYLE);
        complete.setOnAction(ae -> {
            SettingsHandler.write();
            this.back();
        });
        addNode(complete, createRelativeSizeAndCoordinate(35, 85, 30, 10));

        Button graphic = new Button (LanguageConstant.GRAPHIC_SELECTOR);
        graphic.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        graphic.getStyleClass().add(NameConstant.SETTING_MENU_SELECTOR_STYLE);
        graphic.setOnAction(ae -> GraphicManager.loadComponent(getId(), GraphicRegister.SETTINGS_GRAPHIC_MENU.getId()));
        addNode(graphic, createRelativeSizeAndCoordinate(10, 20, 30, 10));

        Button language = new Button(LanguageConstant.LANGUAGE_SELECTOR);
        language.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        language.getStyleClass().add(NameConstant.SETTING_MENU_SELECTOR_STYLE);
        language.setOnAction(ae -> GraphicManager.loadComponent(getId(), GraphicRegister.SETTINGS_GRAPHIC_MENU.getId()));
        addNode(language, createRelativeSizeAndCoordinate(10, 35, 30, 10));

        Label languageLabel = new Label(LanguageConstant.CURRENT_LANGUAGE);
        languageLabel.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        languageLabel.getStyleClass().add(NameConstant.SETTING_LANGUAGE_LABEL_STYLE);
        addNode(languageLabel, createRelativeSizeAndCoordinate(10, 9, 12, 7));

        Button sound = new Button(LanguageConstant.SOUND_SELECTOR);
        sound.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        sound.getStyleClass().add(NameConstant.SETTING_MENU_SELECTOR_STYLE);
        sound.setOnAction(ae -> GraphicManager.loadComponent(getId(), GraphicRegister.SETTINGS_SOUND_MENU.getId()));
        addNode(sound, createRelativeSizeAndCoordinate(60, 20, 30, 10));

        Button controls = new Button (LanguageConstant.CONTROLS_SELECTOR);
        controls.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        controls.getStyleClass().add (NameConstant.SETTING_MENU_SELECTOR_STYLE);
        controls.setOnAction(ae -> GraphicManager.loadComponent(getId(), GraphicRegister.SETTINGS_CONTROL_MENU.getId()));
        addNode(controls, createRelativeSizeAndCoordinate (60, 35, 30, 10));
    }
}
