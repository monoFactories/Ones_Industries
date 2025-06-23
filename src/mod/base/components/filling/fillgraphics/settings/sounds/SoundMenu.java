package mod.base.components.filling.fillgraphics.settings.sounds;

import game_logic.repositories.Identifier;
import mod.base.components.constants.StyleConstant;
import core.configs.languages.LabelDescriptor;
import core.configs.settings.SettingsHandler;
import core.configs.settings.SoundSettings;
import core.gameActions.Debug;
import core.graphics.graphichandlers.GraphicProcessor;
import core.graphics.objects.GraphicComponent;
import core.graphics.objects.NodeParameter;
import game_logic.repositories.LanguageRepository;
import game_logic.repositories.ModsRepository;

import static core.graphics.objects.NodeParameter.*;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import mod.base.components.constants.LanguageConstant;
import mod.base.components.constants.NameConstant;
import mod.base.modification.Base;
import mod.base.modification.graphics.components.SoundsGraphicComponent;

import java.util.HashSet;
import java.util.Set;

public class SoundMenu extends SoundsGraphicComponent {
    private final Set<SoundSettingEntry> entries = new HashSet<>();
    public SoundMenu(Identifier id) {
        super(id);
        getAnchorPane().getStyleClass().add(StyleConstant.SOUND_MENU.MAIN_PANE);
        Button back = new Button(LanguageConstant.COMPLETE);
        back.setOnAction(ae -> back());
        back.getStyleClass().add(StyleConstant.SOUND_MENU.BUTTONS);
        SoundsGraphicComponent.setSoundsHandlers(back);
        addNode(back, createRelativeSizeAndCoordinate(5, 88, 20, 7));
        SoundSettings.TypeVolume[] types = SoundSettings.TypeVolume.values();
        for (SoundSettings.TypeVolume type : types) {
            entries.add(new SoundSettingEntry(type, SoundSettingEntry.getNameForType(type)));
        }
        entries.forEach(e -> e.addInGraphicComponent(this, SoundSettingEntry.getParameterForType(e.type)));
    }

    @Override
    public void onAddFromScreen() {
        entries.forEach(sse -> {
            sse.update();
            sse.fromSettings();
        });
    }

    @Override
    public void back() {
        entries.forEach(SoundSettingEntry::toSettings);
        GraphicProcessor.Controller.delete(getId());
        GraphicProcessor.Controller.loadLastFromStack();
    }

    private static String transformationVolume (double volume) {
        return String.valueOf(Math.round(volume));
    }
    private static double transformationVolume (String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException n) {
            return -1.0;
        }
    }
    private static class SoundSettingEntry {
        private final SoundSettings.TypeVolume type;
        private final Slider slider;
        private final String constantName;
        private final Label name;
        private final TextField valueField;

        public SoundSettingEntry (SoundSettings.TypeVolume type, String name) {
            this.type = type;
            slider = new Slider();
            slider.setMax(100);
            slider.setMin(0);
            SoundsGraphicComponent.setSoundsHandlers(slider);
            slider.getStyleClass().add(StyleConstant.SOUND_MENU.SOUND_SLIDER);
            this.constantName = name;
            this.name = new Label(name);
            this.name.getStyleClass().add(StyleConstant.SOUND_MENU.LINE_NAME);
            valueField = new TextField();
            valueField.getStyleClass().add(StyleConstant.SOUND_MENU.LINE_TEXT_FIELD);
            binds();
        }
        private void binds () {
            slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                valueField.setText(String.valueOf(newValue.intValue()));
                toSettings();
            });
            valueField.textProperty().addListener((observable, oldVal, newVal) -> {
                if (!newVal.isEmpty()) {
                    try {
                        int currentVal = Integer.parseInt(newVal);
                        if (currentVal < 0) {
                            valueField.setText(String.valueOf(0));
                            slider.setValue(0);
                            return;
                        }
                        if (currentVal > 100) {
                            valueField.setText(String.valueOf(100));
                            slider.setValue(100);
                            return;
                        }
                        slider.setValue(currentVal);
                    } catch (NumberFormatException e) {
                        valueField.setText(oldVal);
                    }
                } else {
                    slider.setValue(0); // Если поле пустое, устанавливаем ползунок на минимум
                }
            });
        }
        public void addInGraphicComponent (GraphicComponent gc, NodeParameter param) {
            Label percent = new Label("%");
            name.setMaxWidth(Double.MAX_VALUE);
            slider.setMaxWidth(Double.MAX_VALUE);
            valueField.setMaxWidth(Double.MAX_VALUE);
            percent.setMaxWidth(Double.MAX_VALUE);
            percent.getStyleClass().add(StyleConstant.SOUND_MENU.LINE_NAME);
            HBox unitedLine = new HBox (name, slider, valueField, percent);
            name.prefWidthProperty().bind(unitedLine.widthProperty().multiply(0.3));
            slider.prefWidthProperty().bind(unitedLine.widthProperty().multiply(0.4));
            valueField.prefWidthProperty().bind(unitedLine.widthProperty().multiply(0.2));
            percent.prefWidthProperty().bind(unitedLine.widthProperty().multiply(0.1));
            unitedLine.setFillHeight(true);
            unitedLine.getStyleClass().add(StyleConstant.SOUND_MENU.HBOX_LINE);
            gc.addNode(unitedLine, param);
        }
        public void update () {
            Debug.debug("GUI sound entry for type: " + type.name() + " is updated");
            LanguageRepository rep = ModsRepository.get(Base.parameter.getName()).modLanguage;
            Debug.debug("language for translating: " + rep);
            name.setText(LabelDescriptor.descriptor (rep, constantName));
        }
        public static String getNameForType (SoundSettings.TypeVolume type) {
            return switch (type) {
                case OVERALL -> LanguageConstant.SOUND_SETTINGS.OVERALL;
                case INTERFACE -> LanguageConstant.SOUND_SETTINGS.INTERFACE;
                case MUSIC -> LanguageConstant.SOUND_SETTINGS.MUSIC;
                case FACTORY -> LanguageConstant.SOUND_SETTINGS.MECHANISMS;
                case CAR -> LanguageConstant.SOUND_SETTINGS.TRANSPORT;
                case OTHER -> LanguageConstant.SOUND_SETTINGS.OTHER;
                case null -> "UNKNOWN";
            };
        }
        public static NodeParameter getParameterForType (SoundSettings.TypeVolume type) {
            double h = 10;
            double w = 39.5;
            return switch (type) {
                // ot s tup 12
                case OVERALL -> createRelativeSizeAndCoordinate(5, 25, w, h);
                case MUSIC -> createRelativeSizeAndCoordinate(5, 47, w, h);
                case INTERFACE -> createRelativeSizeAndCoordinate(5, 69, w, h);
                case FACTORY -> createRelativeSizeAndCoordinate(45.5, 25, w, h);
                case CAR -> createRelativeSizeAndCoordinate(45.5, 47, w, h);
                case OTHER -> createRelativeSizeAndCoordinate(45.5, 69, w, h);
                case null -> null;
            };
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SoundSettingEntry that)) return false;
            return type == that.type && constantName.equals(that.constantName);
        }

        @Override
        public int hashCode() {
            return (type.hashCode() * 255 + 32767 + constantName.hashCode() * 7);
        }
        public String getValueField () {
            return valueField.getText();
        }
        public String getValueSlider () {
            return slider.getValue() + "";
        }
        public void toSettings() {
            double value = transformationVolume(valueField.getText());
            value = Math.clamp(value, 0.0, 100.0);
            SettingsHandler.settings.getSound().setTypedVolume(type, value);
        }
        public void fromSettings () {
            double value = SettingsHandler.settings.getSound().getTypedVolume(type);
            value = Math.clamp(value, 0.0, 100.0);
            slider.setValue(value);
        }
    }
}
