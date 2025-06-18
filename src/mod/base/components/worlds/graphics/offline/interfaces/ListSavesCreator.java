package mod.base.components.worlds.graphics.offline.interfaces;

import mod.base.components.constants.NameConstant;
import mod.base.components.constants.StyleConstant;
import mod.base.components.logics.Translator;
import core.gameActions.Debug;
import core.graphics.graphichandlers.GraphicProcessor;
import core.moding.mod.ModResourceReader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import mod.base.components.constants.LanguageConstant;
import mod.base.components.worlds.graphics.offline.files.SavesManager;
import mod.base.modification.Base;
import mod.base.modification.graphics.components.SoundsGraphicComponent;
import mod.base.modification.utils.MathUtils.MemoryUtils;
import mod.base.modification.graphics.components.ConfirmMiniPanel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import static core.graphics.objects.NodeParameter.createRelativeSizeAndCoordinate;

public class ListSavesCreator {
    public static void create (VBox target, AtomicBoolean deleteFlag) {
        target.getChildren().clear();
        List<Save> saves = SavesManager.saves();
        saves.forEach(save -> {
            Label name = new Label(save.description.name());
            name.getStyleClass().add(StyleConstant.OFFLINE_MENU.BLOCK_NAME);
            Button info = getInfoButton(save, deleteFlag);
            SoundsGraphicComponent.setSoundsHandlers(info);
            String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date(save.lastEditDate));
            Label lastDate = new Label(date);
            Label size = new Label(MemoryUtils.convertByteCount(save.size));
            Region parser = new Region();
            HBox.setHgrow(parser, Priority.ALWAYS);
            ImageView delete = getDeleteButton(save, deleteFlag);
            HBox line = new HBox();
            line.getChildren().add(name);
            line.getChildren().add(parser);
            line.getChildren().add(info);
            line.getChildren().add(delete);
            line.getChildren().add(lastDate);
            line.getChildren().add(size);
            line.getStyleClass().add(StyleConstant.OFFLINE_MENU.SCROLL_BLOCK);
            SoundsGraphicComponent.setSoundsHandlers(line);
            target.getChildren().add(line);
        });
    }

    private static ImageView getDeleteButton(Save save, AtomicBoolean deleteFlag) {
        //Button delete = new Button(LabelDescriptor.descriptor(ModsRepository.mods.get(Base.parameter.getName()).modLanguage, LanguageConstant.OFFLINE_MENU.DELETE));
        ImageView delete = new ImageView();
        delete.setOnMouseClicked(ae -> {
            addConfirmDelete(save, deleteFlag, null);
        });
        delete.getStyleClass().add(StyleConstant.OFFLINE_MENU.SCROLL_BLOCK_DELETE);
        try (ModResourceReader mrr = Base.parameter.createModResourceReader()) {
            delete.setImage(new Image(mrr.getResource("resources/textures/gui/trash_gui.png")));
        } catch (Exception e) {
            Debug.debug(Level.INFO, "couldn't set image", e);
        }
        return delete;
    }
    private static Button getInfoButton (Save save, AtomicBoolean deleteFlag) {
        Button btn = new Button("i");
        btn.setOnAction(ae -> {
            GraphicProcessor.Controller.add(InfoSaveMenu.create(save, deleteFlag));
        });
        btn.getStyleClass().add(StyleConstant.OFFLINE_MENU.SCROLL_BLOCK_INFO);
        return btn;
    }
    static void addConfirmDelete (Save save, AtomicBoolean deleteFlag, Runnable anotherAction) {
        ConfirmMiniPanel confirmDelete = new ConfirmMiniPanel(NameConstant.MENU_IDENTIFIER.OFFLINE_MENU_CONFIRM_MENU, createRelativeSizeAndCoordinate(52.5, 55, 42.5, 40), createRelativeSizeAndCoordinate(5, 55, 42.5, 40), "OK", "CANCEL", ae1 -> {
            SavesManager.deleteSave(save.file.getName());
            deleteFlag.set(true);
            if (anotherAction != null) {
                try {
                    anotherAction.run();
                } catch (Exception e) {
                    Base.getMod().debug(Level.INFO, "couldn't execute action after clicking OK", e);
                }
            }
        }, null, StyleConstant.OFFLINE_MENU.MAIN_BUTTONS, StyleConstant.OFFLINE_MENU.MAIN_BUTTONS);
        confirmDelete.getMiniPanel().setStyle(null);
        confirmDelete.getMiniPanel().getStyleClass().add(StyleConstant.OFFLINE_MENU.FLOAT_DELETE_PANE);
        Label confirmDeleteL = new Label(Translator.reTranslateLabel(LanguageConstant.OFFLINE_MENU.CONFIRM_DELETION));
        confirmDeleteL.getStyleClass().add(StyleConstant.OFFLINE_MENU.LABEL_WITH_NAME);
        confirmDelete.addNode(confirmDeleteL, createRelativeSizeAndCoordinate(10, 10, 80, 20));
        confirmDelete.setRelativeSize(true);
        confirmDelete.setWidth(25);
        confirmDelete.setHeight(15);
        confirmDelete.setCanMoved(true);
        GraphicProcessor.Controller.add(confirmDelete);
    }
    public static void init() {}
}
