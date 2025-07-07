package mod.base.components.worlds.graphics.offline.interfaces;

import game_logic.GraphicManager;
import game_logic.repositories.Identifier;
import mod.base.components.constants.LanguageConstant;
import mod.base.components.constants.SpecialConstant;
import java.util.concurrent.atomic.AtomicBoolean;

import mod.base.components.constants.StyleConstant;
import core.graphics.objects.NodeParameter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import mod.base.components.registration.fillgraphics.GraphicRegister;
import mod.base.modification.graphics.components.SoundsGraphicComponent;
import mod.base.modification.graphics.utils.TagManagement;

public class OfflineMenu extends SoundsGraphicComponent {

    private final VBox list;
    private final AtomicBoolean deleteFlag = new AtomicBoolean(false);

    public OfflineMenu(Identifier id) {
        super(id);
        getAnchorPane().getStyleClass().add(StyleConstant.OFFLINE_MENU.MAIN_PANE);
        Pane border = new Pane();
        border.getStyleClass().add(StyleConstant.OFFLINE_MENU.BORDER_STYLE);
        addNode(border, NodeParameter.createRelativeSizeAndCoordinate(1, 1, 98, 98));
        buttons();
        list = new VBox();
        list.getStyleClass().add(StyleConstant.OFFLINE_MENU.SCROLL_VBOX);
        list.setFillWidth(true);
        ScrollPane scroll = new ScrollPane(list);
        scroll.getStyleClass().add(StyleConstant.OFFLINE_MENU.SCROLL_PANE);
        scroll.setFitToWidth(true);
        addNode(scroll, NodeParameter.createRelativeSizeAndCoordinate(50, 2, 48, 96));
    }

    private void buttons () {
        Button back = new Button(LanguageConstant.OFFLINE_MENU.BACK);
        back.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        back.getStyleClass().add(StyleConstant.OFFLINE_MENU.MAIN_BUTTONS);
        back.setOnAction(ae -> back());
        addNode(back, NodeParameter.createRelativeSizeAndCoordinate(2, 88, 14, 5));
        //
        Label offlineGame = new Label(LanguageConstant.OFFLINE_MENU.OFFLINE_GAME);
        offlineGame.getStyleClass().add(StyleConstant.OFFLINE_MENU.LABEL_WITH_NAME);
        addNode(offlineGame, NodeParameter.createRelativeSizeAndCoordinate(2, 5, 14, 10));
        //
        Button startNew = new Button(LanguageConstant.OFFLINE_MENU.CREATE_NEW_GAME);
        startNew.getStyleClass().add(StyleConstant.OFFLINE_MENU.MAIN_BUTTONS);
        TagManagement.addTagInId(startNew, SpecialConstant.INTERFACE.ID_ENABLED_SOUND);
        startNew.setOnAction(ae -> GraphicManager.loadComponent (getId(), GraphicRegister.NEW_GAME_MENU.getId()));
        addNode(startNew, NodeParameter.createRelativeSizeAndCoordinate(2, 68, 14, 10));
    }

    @Override
    public void CustomUpdate() {
        if (deleteFlag.get()) {
            deleteFlag.set(false);
            ListSavesCreator.create(list, deleteFlag);
        }
        super.standardUpdate();
    }

    @Override
    public void onAddFromScreen() {
        deleteFlag.set(false);
        ListSavesCreator.create(list, deleteFlag);
    }

    @Override
    public void onRemoveFromScreen() {
        super.onRemoveFromScreen();
    }

}

