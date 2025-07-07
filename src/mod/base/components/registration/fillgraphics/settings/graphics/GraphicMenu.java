package mod.base.components.registration.fillgraphics.settings.graphics;

import game_logic.repositories.Identifier;
import mod.base.components.constants.SpecialConstant;
import core.configs.settings.GraphicSettings;
import core.configs.settings.SettingsHandler;
import core.graphics.graphichandlers.GraphicProcessor;
import core.graphics.objects.NodeParameter;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import mod.base.modification.graphics.components.SoundsGraphicComponent;

import java.util.Timer;
import java.util.TimerTask;

import static mod.base.components.constants.LanguageConstant.*;
import static mod.base.components.constants.NameConstant.*;
import static core.graphics.objects.NodeParameter.*;

public class GraphicMenu extends SoundsGraphicComponent {
    private final TextField brightnessField;
    private final TextField contrastField;
    private final TextField saturationField;
    private final TextField widthField;
    private final TextField heightField;
    private final TextField FPSField;
    private final CheckBox fullscreenCheckBox;
    private final Button end, apply, confirm, cancel;
    private GraphicSettings gs;
    private Timer timer;

    public GraphicMenu(Identifier id) {
        super(id);
        super.getAnchorPane().getStyleClass().add(GRAPHIC_MENU_STYLE);
        //GraphicSettings currentSettings = SettingsHandler.settings.getGraphicSettings();
        ////gs = new GraphicSettings(currentSettings.getBrightness(), currentSettings.getContrast(), currentSettings.getSaturation(), currentSettings.getWidth(), currentSettings.getHeight(), currentSettings.isFullScreen(), currentSettings.getFPS());
        /// fullscreen
        fullscreenCheckBox = new CheckBox(GRAPHIC_FULLSCREEN_MESSAGE);
        fullscreenCheckBox.getStyleClass().add(GRAPHIC_MENU_FULLSCREEN_CHECKBOX_STYLE);
        addNode(fullscreenCheckBox, createRelativeSizeAndCoordinate(5, 5, 10, 5));

        /// main buttons, 4 buttons, 15% - button
        end = new Button(COMPLETE);
        end.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        end.getStyleClass().add(GRAPHIC_MENU_COMPLETE_STYLE);
        addNode (end, createRelativeSizeAndCoordinate (8, 85, 15, 10));

        apply = new Button (GRAPHIC_MENU_APPLY);
        apply.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        apply.getStyleClass().add(GRAPHIC_MENU_COMPLETE_STYLE);
        addNode (apply, createRelativeSizeAndCoordinate (31, 85, 15, 10));

        confirm = new Button(GRAPHIC_MENU_CONFIRM);
        confirm.getStyleClass().add(CONFIRM_STYLE);
        confirm.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        confirm.setVisible(false);
        addNode (confirm, createRelativeSizeAndCoordinate (54, 85, 15, 10));

        cancel = new Button(GRAPHIC_MENU_CANCEL);
        cancel.setId(SpecialConstant.INTERFACE.ID_ENABLED_SOUND + ";");
        cancel.getStyleClass().add(MAIN_MENU_CANCEL_STYLE);
        cancel.setVisible(false);
        addNode (cancel, createRelativeSizeAndCoordinate (77, 85, 15, 10));
        /// actions
        end.setOnAction(ae -> {
            stopTimer();
            removeCustom();
            GraphicProcessor.Controller.delete(id);
            GraphicProcessor.Controller.loadLastFromStack();
        });
        apply.setOnAction(ae -> {
            applyGraphicSettings();
            startTimer();
            confirm.setVisible(true);
            cancel.setVisible(true);
        });
        cancel.setOnAction(ae -> removeCustom());
        confirm.setOnAction(ae -> {
            SettingsHandler.settings.setGraphicSettings(gs);
            removeCustom();
        });
        /// fields
        brightnessField = new TextField();
        setUnitedField(brightnessField, BRIGHTNESS, "%",-100, 100, createRelativeSizeAndCoordinate(5, 20, 20, 10));

        contrastField = new TextField();
        setUnitedField(contrastField, CONTRAST, "%",-100, 100, createRelativeSizeAndCoordinate(5, 35, 20, 10));

        saturationField = new TextField();
        setUnitedField(saturationField, SATURATION, "%",-100, 100, createRelativeSizeAndCoordinate(5, 50, 20, 10));

        widthField = new TextField();
        setUnitedField(widthField, WIDTH, "",1, 10000, createRelativeSizeAndCoordinate(50, 20, 20, 10));

        heightField = new TextField();
        setUnitedField(heightField, HEIGHT, "",1, 10000, createRelativeSizeAndCoordinate(50, 35, 20, 10));

        FPSField = new TextField();
        setUnitedField(FPSField, FRAME_RATE, FPS,1, 99999, createRelativeSizeAndCoordinate(50, 50, 20, 10));
    }
    private void setUnitedField (TextField tf, String name, String unitName, final int min, final int max, NodeParameter pos) {
        Label nameLabel = new Label(name);
        nameLabel.getStyleClass().add(GRAPHIC_MENU_LABEL_FIELDS_STYLE);
        Label unitedPercent = new Label(unitName);
        final boolean isAcceptMinus = min < 0;
        tf.textProperty().addListener((observable, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                try {
                    int currentVal = Integer.parseInt(newVal);
                    if (currentVal < min) {
                        tf.setText(String.valueOf(min));
                        return;
                    }
                    if (currentVal > max) {
                        tf.setText(String.valueOf(max));
                    }
                } catch (NumberFormatException e) {
                    if (newVal.equals("-") && isAcceptMinus) {
                        return;
                    }
                    tf.setText(oldVal);
                }
            }
        });
        double width = pos.width;
        double height = pos.height;
        double x = pos.x;
        double y = pos.y;
        addNode(nameLabel, createRelativeSizeAndCoordinate(x, y, width * 0.5, height));
        addNode(tf, createRelativeSizeAndCoordinate(x + width * 0.5, y, width * 0.3, height));
        addNode(unitedPercent, createRelativeSizeAndCoordinate(x + width * 0.8, y, width * 0.2, height));
    }
    private void setGraphicSettings () {
        GraphicSettings currentSettings = SettingsHandler.settings.getGraphicSettings();
        gs = new GraphicSettings(currentSettings.getBrightness(), currentSettings.getContrast(), currentSettings.getSaturation(), currentSettings.getWidth(), currentSettings.getHeight(), currentSettings.isFullScreen(), currentSettings.getFPS());
    }
    private void setText () {
        brightnessField.setText(String.valueOf(Math.round(gs.getBrightness() * 100.0)));
        contrastField.setText(String.valueOf( Math.round(gs.getContrast() * 100.0) ));
        saturationField.setText(String.valueOf(Math.round (gs.getSaturation() * 100.0)));
        widthField.setText(String.valueOf(Math.round(gs.getWidth())));
        heightField.setText(String.valueOf(Math.round(gs.getHeight())));
        FPSField.setText(String.valueOf(Math.round(gs.getFPS())));
        fullscreenCheckBox.setSelected(gs.isFullScreen());
    }
    private void applyGraphicSettings () {
        double bright = getValue (brightnessField, 0.0, -100, 100) / 100.0;
        double contrast = getValue (contrastField, 0.0, -100, 100) / 100.0;
        double saturation = getValue (saturationField, 0.0, -100, 100) / 100.0;
        double width = getValue (widthField, 1920.0, 10.0, 9999);
        double height = getValue (heightField, 1080.0, 10.0, 9999);
        double fps = getValue (FPSField, 60, 1, 99999);
        boolean fullscreen = fullscreenCheckBox.isSelected();
        gs.setBrightness(bright);
        gs.setContrast(contrast);
        gs.setSaturation(saturation);
        gs.setWidth(width);
        gs.setHeight(height);
        gs.setFPS(fps);
        gs.setFullScreen(fullscreen);
        GraphicProcessor.Controller.useSettings(gs);
    }
    private static double getValue (TextField tf, double defaultValue, double min, double max) {
        try {
            return Math.clamp(Double.parseDouble(tf.getText()), min, max);
        } catch (NullPointerException | IllegalArgumentException e) {
            return defaultValue;
        }
    }
    @Override
    public void onAddFromScreen() {
        //System.out.println("fill text in graphic menu");
        setGraphicSettings();
        setText();
    }
    private void removeCustom () {
        GraphicProcessor.Controller.useSettings(SettingsHandler.settings.getGraphicSettings());
        setText();
        cancel.setVisible(false);
        confirm.setVisible(false);
    }
    private void startTimer () {
        stopTimer();
        timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                removeCustom();
            }
        }, 15000);
    }
    private void stopTimer () {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
