package mod.base.components.worlds.graphics.offline.interfaces;

import core.graphics.graphichandlers.GraphicProcessor;
import core.graphics.objects.NodeParameter;
import game_logic.GraphicManager;
import game_logic.repositories.Identifier;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import mod.base.components.constants.LanguageConstant;
import mod.base.components.constants.SpecialConstant;
import mod.base.components.constants.StyleConstant;
import mod.base.components.worlds.party.GameParty;
import mod.base.modification.Base;
import mod.base.modification.graphics.components.SoundsGraphicComponent;
import mod.base.modification.graphics.utils.ButtonSwitchFactory;
import mod.base.modification.graphics.utils.InputFieldCreator;
import mod.base.modification.graphics.utils.TagManagement;

import java.util.Random;

public class NewGameMenu extends SoundsGraphicComponent {

    public NewGameMenu(Identifier id) {
        super(id);
        getAnchorPane().getStyleClass().add(StyleConstant.NEW_GAME_MENU.ROOT);
        //
        Button cancel = new Button (LanguageConstant.NEW_GAME_MENU.BACK);
        cancel.getStyleClass().add (StyleConstant.NEW_GAME_MENU.BUTTON);
        cancel.setOnAction(ae -> back());
        TagManagement.addTagInId(cancel, SpecialConstant.INTERFACE.ID_ENABLED_SOUND);
        addNode (cancel, NodeParameter.createRelativeSizeAndCoordinate(2, 2, 15, 6));
        //
        Label title = new Label (LanguageConstant.NEW_GAME_MENU.NAME);
        title.getStyleClass().add (StyleConstant.NEW_GAME_MENU.TITLE);
        addNode (title, NodeParameter.createRelativeSizeAndCoordinate(30, 2, 40, 8));
        //
        Button play = new Button (LanguageConstant.NEW_GAME_MENU.PLAY);
        play.getStyleClass().add(StyleConstant.NEW_GAME_MENU.PLAY);
        TagManagement.addTagInId(play, SpecialConstant.INTERFACE.ID_ENABLED_SOUND);
        addNode (play, NodeParameter.createRelativeSizeAndCoordinate(83, 2, 15, 6));

        //
        int count = 2;
        double x = 20, width = 60, widthPerCount = width / count, y = 20, switcherHeight = 8;
        //
        Pane mainCanvas = new Pane();
        mainCanvas.getStyleClass().add(StyleConstant.NEW_GAME_MENU.CANVAS);
        addNode(mainCanvas, NodeParameter.createRelativeSizeAndCoordinate(x, y, width, 75));

        ButtonSwitchFactory buttonSwitcher = new ButtonSwitchFactory();
        //
        Button mainSettings = new Button (LanguageConstant.NEW_GAME_MENU.MAIN);
        InputFieldCreator.LabelFieldButtonEntry fieldSeed = InputFieldCreator.createLFBE(InputFieldCreator.TypeInputInField.LONG, NodeParameter.createRelativeSizeAndCoordinate(23, 33, 35, 6), 2.0, 5.0, 1.0);
        Label seed = fieldSeed.label();
        seed.setText(LanguageConstant.NEW_GAME_MENU.SEED);
        seed.getStyleClass().add(StyleConstant.NEW_GAME_MENU.SEED_LABEL);
        TextField field = fieldSeed.field();
        field.getStyleClass().add (StyleConstant.NEW_GAME_MENU.SEED_FIELD);
        Button loadNewSeed = fieldSeed.btn();
        loadNewSeed.setText("↻");
        loadNewSeed.setOnAction(ae -> field.setText(String.valueOf(new Random().nextLong())));
        loadNewSeed.getStyleClass().add(StyleConstant.NEW_GAME_MENU.SEED_BUTTON);

        play.setOnAction(ae -> {
            GameParty party = GameParty.getInstance (field.getText().hashCode());
            PartyMenu partyMenu = new PartyMenu(Base.getID("party_menu"));
            Base.getMod().debug("start Party");
            Thread partyThread = new Thread(party);
            partyThread.setDaemon(true);
            partyThread.start();
            Base.getMod().debug("set party");
            partyMenu.setGameParty(party);
            Base.getMod().debug("party menu add on screen");
            GraphicManager.hideFromScreen(this.getId());
            GraphicProcessor.Controller.add (partyMenu);
        });
        //
        buttonSwitcher.addSwitcher (mainSettings, NodeParameter.createRelativeSizeAndCoordinate(x, y, widthPerCount, switcherHeight), StyleConstant.NEW_GAME_MENU.SWITCHER_NO_ACTIVE, StyleConstant.NEW_GAME_MENU.SWITCHER_ACTIVE)
                .addNode(seed, fieldSeed.labelParam())
                .addNode(field, fieldSeed.fieldParam())
                .addNode(loadNewSeed, fieldSeed.btnParam())
        ;
        buttonSwitcher.addSwitcher (new Button ("test"), NodeParameter.createRelativeSizeAndCoordinate(x + widthPerCount, y, widthPerCount, switcherHeight));

        buttonSwitcher.addOnComponent(this, mainSettings);
    }

    @Override
    public void onAddFromScreen () {

    }
}
