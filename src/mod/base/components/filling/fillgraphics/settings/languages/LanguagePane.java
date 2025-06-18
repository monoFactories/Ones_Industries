package mod.base.components.filling.fillgraphics.settings.languages;

import game_logic.repositories.Identifier;
import mod.base.components.constants.SpecialConstant;
import core.graphics.graphichandlers.GraphicProcessor;
import core.graphics.objects.GraphicComponent;
import game_logic.managers.LanguageModManager;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import mod.base.modification.graphics.components.SoundsGraphicComponent;

import static mod.base.components.constants.LanguageConstant.*;
import static mod.base.components.constants.NameConstant.*;
import static core.graphics.objects.NodeParameter.*;

public class LanguagePane extends SoundsGraphicComponent {

    private final ListView<String> languagesList;

    public LanguagePane(Identifier id) {
        super(id);
        getAnchorPane().getStyleClass().add(SETTING_PANE_STYLE);

        languagesList = new ListView<>();
        languagesList.getSelectionModel().setSelectionMode (SelectionMode.SINGLE);
        languagesList.getStyleClass().add(LANGUAGE_LIST_VIEW_STYLE);
        addNode(languagesList, createRelativeSizeAndCoordinate(10, 10, 80, 70));

        Button complete = getComplete(languagesList, this);
        complete.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        complete.getStyleClass().add(SETTING_MENU_SELECTOR_STYLE);
        addNode(complete, createRelativeSizeAndCoordinate(35, 85, 30, 10));

    }

    @Override
    public void onAddFromScreen() {
        languagesList.getItems().clear();
        languagesList.getItems().addAll(LanguageModManager.getLanguagesSet());
    }
    private static Button getComplete(ListView<String> languagesList, GraphicComponent languageMenu) {
        Button complete = new Button (COMPLETE);
        complete.setOnAction(ae -> {
            String selectedLang = languagesList.getSelectionModel().getSelectedItem();
            if (selectedLang != null) {
                LanguageModManager.setLanguage(selectedLang);
            }
            GraphicProcessor.Controller.delete(languageMenu.getId());
            GraphicProcessor.Controller.loadLastFromStack();
        });
        return complete;
    }
}
