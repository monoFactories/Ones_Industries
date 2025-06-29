package mod.base.components.filling.fillgraphics.settings.controls;

import game_logic.managers.controls.CurrentControl;
import game_logic.repositories.Identifier;
import mod.base.components.constants.SpecialConstant;
import mod.base.components.constants.StyleConstant;
import core.graphics.graphichandlers.GraphicProcessor;
import game_logic.managers.controls.ControlModManager;
import game_logic.managers.controls.ControlsExecutor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import mod.base.modification.Base;
import mod.base.modification.graphics.components.SoundsGraphicComponent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import static mod.base.components.constants.NameConstant.*;
import static mod.base.components.constants.LanguageConstant.*;
import static core.graphics.objects.NodeParameter.createRelativeSizeAndCoordinate;

public class ControlMenu extends SoundsGraphicComponent {
    /// logic
    //private ControlsChanger controlsChanger;
    private final ControlsStorage controlsStorage;
    private final ConcurrentHashMap<Identifier, Set<Button>> buttonsList = new ConcurrentHashMap<>(); /// need for fast update
    /// interface
    private final VBox content;
    private final ScrollPane scrolling;

    public ControlMenu(Identifier id) {
        super(id);
        getAnchorPane().getStyleClass().add(StyleConstant.CONTROL_MENU.MAIN_PANE);
        content = new VBox();
        content.getStyleClass().add(StyleConstant.CONTROL_MENU.CONTROLS_CONTENT);
        scrolling = new ScrollPane(content);
        scrolling.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        controlsStorage = new ControlsStorage();
        scrolling.getStyleClass().add(StyleConstant.CONTROL_MENU.SCROLL_PANE);
        scrolling.setFitToWidth(true);
        addNode(scrolling, createRelativeSizeAndCoordinate(25, 10, 50, 88));
        Button close = new Button(COMPLETE);
        close.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        close.setOnAction(ae -> {
            GraphicProcessor.Controller.delete (getId());
            GraphicProcessor.Controller.loadLastFromStack();
        });
        close.getStyleClass().add(StyleConstant.CONTROL_MENU.CONTROLS_EXIT);
        addNode(close, createRelativeSizeAndCoordinate(5 , 85, 19, 9));
    }

    @Override
    public void onAddFromScreen() {
        ControlsExecutor.executor.addHandler();
        controlsStorage.clear();
        content.getChildren().clear();
        buttonsList.clear();
        controlsStorage.generate();
        toScreen();
    }

    @Override
    public void onRemoveFromScreen() {
        controlsStorage.clear();
        content.getChildren().clear();
        buttonsList.clear();
        ControlsExecutor.executor.addHandler();
    }
    private void toScreen () {
        //System.out.println("sorted parts: " + controlsStorage.getSortedParts() + ", \n parts" + controlsStorage.getParts());
        controlsStorage.getSortedParts().forEach((s, stringIdentifierTreeMap) -> stringIdentifierTreeMap.forEach((partName, identifier) -> createTab(partName, controlsStorage.getParts().get(identifier))));
    }

    //private void addModTab (ModControlsStorage mcs) {
    //    TreeSet<String> sortedTranslatedPartsNames = mcs.translatedPartsName;
    //    ConcurrentHashMap<String, List<CurrentControlView>> translatedPartsLists = mcs.translatedPartsStorage;
    //    sortedTranslatedPartsNames.forEach(s -> {
    //        List<CurrentControlView> listEntry = translatedPartsLists.get(s);
    //        if (listEntry != null)
    //            createTab(s, listEntry);
    //    });
    //}
    private void createTab (String nameEntry, List<CurrentControlView> tabEntry) {
        VBox tab = new VBox();
        VBox tabContent = createContentTab(tabEntry);
        tabContent.setVisible(false);
        tabContent.setManaged(false);
        Button closeTab = new Button(">");
        closeTab.setOnAction(ae -> {
            if (tabContent.isVisible()) {
                tabContent.setVisible(false);
                tabContent.setManaged(false);
                closeTab.setText(">");
            } else {
                tabContent.setVisible(true);
                tabContent.setManaged(true);
                closeTab.setText("v");
            }
        });
        SoundsGraphicComponent.setSoundsHandlers(closeTab);
        closeTab.getStyleClass().add(StyleConstant.CONTROL_MENU.BLOCK_HIDE);
        Label name = new Label(nameEntry);
        name.getStyleClass().add(StyleConstant.CONTROL_MENU.BLOCK_NAME);
        HBox firstLine = new HBox (closeTab, name);
        firstLine.getStyleClass().add(StyleConstant.CONTROL_MENU.BLOCK_FIRST_LINE);
        tab.getChildren().addAll(firstLine, tabContent);
        tab.getStyleClass().add(StyleConstant.CONTROL_MENU.CONTROLS_BLOCK);
        content.getChildren().add(tab);
    }
    private VBox createContentTab (List<CurrentControlView> entries) {
        VBox tabContent = new VBox();
        entries.forEach(sv -> {
            Label lineName = new Label(sv.getName());
            lineName.getStyleClass().add(StyleConstant.CONTROL_MENU.BLOCK_LINE_LABEL);
            Button lineBtn = createBtn(sv);
            Region r = new Region();
            HBox.setHgrow(r, Priority.ALWAYS);
            HBox line = new HBox (lineName, r, lineBtn);

            line.getStyleClass().add(StyleConstant.CONTROL_MENU.BLOCK_LINE);
            tabContent.getChildren().add(line);
        });
        tabContent.getStyleClass().add(StyleConstant.CONTROL_MENU.BLOCK_CONTENT);
        return tabContent;
    }
    private Button createBtn (CurrentControlView cv) {
        Identifier fullID = cv.getId();
        Set<Button> setForThis = buttonsList.computeIfAbsent(fullID, k -> new HashSet<>());
        CurrentControl thisControl = cv.getControl();
        String forButton;
        try {
            if (thisControl.isMouse()) {
                forButton = thisControl.getBtn().name();
            } else {
                forButton = thisControl.getKey().name();
            }
        } catch (NullPointerException n) {
            forButton = "";
        }
        Button set = new Button (forButton);
        set.setOnMouseClicked (me -> handlerButtons(cv.getId()));
        setForThis.add (set);
        set.getStyleClass().add(StyleConstant.CONTROL_MENU.BLOCK_LINE_BUTTON);
        SoundsGraphicComponent.setSoundsHandlers(set);
        return set;
    }
    private void handlerButtons (Identifier id) {
        GraphicProcessor.Controller.setKeyPress(keyEvent -> {
            KeyCode selected = keyEvent.getCode();
            ControlModManager.controls.add(id, new CurrentControl(selected));
            update();
            ControlsExecutor.executor.addHandler();
        });
        GraphicProcessor.Controller.setMouseClick(mouseEvent -> {
            MouseButton selected = mouseEvent.getButton();
            ControlModManager.controls.add(id, new CurrentControl(selected));
            update();
            ControlsExecutor.executor.addHandler();
        });
    }
    private void update () {
        buttonsList.forEach ((identifier, set) -> {
            CurrentControl current = ControlModManager.controls.get(identifier);
            String content = "";
            if (current != null) {
                content = current.isMouse() ? current.getBtn().name() : current.getKey().name();
            }
            final String finalContent = content;
            set.forEach(b -> b.setText(finalContent));
        });
    }
}
