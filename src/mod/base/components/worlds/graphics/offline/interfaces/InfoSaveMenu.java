package mod.base.components.worlds.graphics.offline.interfaces;

import game_logic.repositories.Identifier;
import mod.base.components.constants.LanguageConstant;
import mod.base.components.constants.NameConstant;
import mod.base.components.constants.SpecialConstant;
import mod.base.components.constants.StyleConstant;
import mod.base.components.logics.Translator;
import core.gameActions.Debug;
import core.graphics.graphichandlers.GraphicProcessor;
import core.moding.mod.ModResourceReader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import mod.base.modification.Base;
import mod.base.modification.graphics.components.SoundMiniPanel;

import javafx.scene.image.ImageView;
import mod.base.modification.graphics.utils.TagManagement;
import org.apache.commons.io.FileUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

import static core.graphics.objects.NodeParameter.createRelativeSizeAndCoordinate;

public class InfoSaveMenu extends SoundMiniPanel {

    private static InfoSaveCreator creator;

    public InfoSaveMenu (Identifier id) {
        super(id);
    }
    public static InfoSaveMenu create (Save save, AtomicBoolean deleteFlag) {
        if (creator != null) {
            InfoSaveMenu info = creator.get(save);
            if (info != null)
                return info;
        }
        InfoSaveMenu info = new InfoSaveMenu(NameConstant.MENU_IDENTIFIER.OFFLINE_MENU_INFO_MENU);
        info.getMiniPanel().setStyle(null);
        info.getMiniPanel().getStyleClass().add(StyleConstant.OFFLINE_MENU.FLOAT_INFO_PANE);
        info.setCanMoved(true);
        info.setRelativeSize(true);
        info.setWidth(30);
        info.setHeight(30);
        Button x = new Button("X");
        TagManagement.addTagInId(x, SpecialConstant.INTERFACE.ID_ENABLED_SOUND);
        x.setOnAction (ae -> info.back());
        info.addNode(x, createRelativeSizeAndCoordinate(90, 5, 5, 5));
        ImageView trashImage = new ImageView();
        try (ModResourceReader mrr = Base.parameter.createModResourceReader()) {
            trashImage.setImage(new Image(mrr.getResource("resources/textures/gui/trash_gui.png")));
        } catch (Exception e) {
            Debug.debug(Level.INFO, "couldn't set image", e);
        }
        trashImage.setPreserveRatio(false);
        trashImage.setOnMousePressed(ae -> {
            ListSavesCreator.addConfirmDelete(save, deleteFlag, () -> GraphicProcessor.Controller.delete(info.getId()));
            /*ConfirmMiniPanel confirmDelete = new ConfirmMiniPanel(        NameConstant.MENU_NAME.OFFLINE_MENU_CONFIRM_MENU,        createRelativeSizeAndCoordinate(52.5, 55, 42.5, 40),        createRelativeSizeAndCoordinate(5, 55, 42.5, 40),        "OK",        "CANCEL",        ae1 -> {    SavesManager.deleteSave(save.file.getName());        deleteFlag.set(true);            }, null, "", "");confirmDelete.addNode(new Label(Translator.reTranslateLabel(LanguageConstant.OFFLINE_MENU.CONFIRM_DELETION)), createRelativeSizeAndCoordinate(10, 10, 80, 20));confirmDelete.setRelativeSize(true);confirmDelete.setWidth(25);confirmDelete.setHeight(15);confirmDelete.setCanMoved(true);GraphicProcessor.Controller.add(confirmDelete);*/
        });
        info.addNode(trashImage, createRelativeSizeAndCoordinate(90, 90, 5, 8));
        Label name = new Label(save.description.name());
        info.addNode (name, createRelativeSizeAndCoordinate(5, 5, 80, 5));
        Label pathToFile = new Label(Translator.reTranslateLabel(LanguageConstant.OFFLINE_MENU.PATH_TO_FILE) + ": \"" + save.file + "\"");
        info.addNode (pathToFile, createRelativeSizeAndCoordinate(5, 15, 90, 5));
        Label modsLabel = new Label (Translator.reTranslateLabel(LanguageConstant.OFFLINE_MENU.MODS));
        info.addNode(modsLabel, createRelativeSizeAndCoordinate(5, 25, 15, 5));
        ListView<String> mods = new ListView<>();
        mods.getItems().addAll(save.description.lastMods());
        info.addNode(mods, createRelativeSizeAndCoordinate(5, 35, 30, 60));
        Button play = new Button(Translator.reTranslateLabel(LanguageConstant.OFFLINE_MENU.PLAY));
        TagManagement.addTagInId(play, SpecialConstant.INTERFACE.ID_ENABLED_SOUND);
        info.addNode (play, createRelativeSizeAndCoordinate(40, 35, 50, 8));
        Label saved = new Label(Translator.reTranslateLabel(LanguageConstant.OFFLINE_MENU.SAVED) + ": " + (new SimpleDateFormat("HH:mm dd/MM/yyyy").format(new Date(save.lastEditDate))));
        info.addNode(saved, createRelativeSizeAndCoordinate(25, 25, 40, 5));
        Label size = new Label(Translator.reTranslateLabel(LanguageConstant.OFFLINE_MENU.FILE_SIZE) + ":" + FileUtils.byteCountToDisplaySize(save.size));
        info.addNode(size, createRelativeSizeAndCoordinate(70, 25, 25, 5));
        return info;
    }
    public static void addCustomInfoCreator (InfoSaveCreator info) {
        if (info != null)
            creator = info;
    }
    public interface InfoSaveCreator {
        InfoSaveMenu get(Save save);
    }
}
