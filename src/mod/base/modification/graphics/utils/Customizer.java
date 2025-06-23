package mod.base.modification.graphics.utils;

import core.graphics.objects.GraphicComponent;
import core.graphics.objects.NodeParameter;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import static core.graphics.objects.NodeParameter.createRelativeSizeAndCoordinate;

public class Customizer {
    public static void addIntField (GraphicComponent purposeComponent, int min, int max, Label nameLabel, Label unitedPercent, TextField purposeField, NodeParameter pos) {
        if (purposeComponent != null && nameLabel != null && unitedPercent != null && purposeField != null && pos != null) {
            final boolean isAcceptMinus = min < 0;
            purposeField.textProperty().addListener((observable, oldVal, newVal) -> {
                if (!newVal.isEmpty()) {
                    try {
                        int currentVal = Integer.parseInt(newVal);
                        if (currentVal < min) {
                            purposeField.setText(String.valueOf(min));
                            return;
                        }
                        if (currentVal > max) {
                            purposeField.setText(String.valueOf(max));
                        }
                    } catch (NumberFormatException e) {
                        if (newVal.equals("-") && isAcceptMinus) {
                            return;
                        }
                        purposeField.setText(oldVal);
                    }
                }
            });
            double width = pos.width;
            double height = pos.height;
            double x = pos.x;
            double y = pos.y;
            purposeComponent.addNode(nameLabel, createRelativeSizeAndCoordinate(x, y, width * 0.5, height));
            purposeComponent.addNode(purposeField, createRelativeSizeAndCoordinate(x + width * 0.5, y, width * 0.3, height));
            purposeComponent.addNode(unitedPercent, createRelativeSizeAndCoordinate(x + width * 0.8, y, width * 0.2, height));
        }
    }
    public static void addIntField (GraphicComponent purposeComponent, int min, int max, String name, TextField purposeField, String unit, NodeParameter param) {
        addIntField(purposeComponent, min, max, new Label(name), new Label(unit), purposeField, param);
    }
    public static void addIntSliderField (GraphicComponent purposeComponent, int min, int max, Label nameLabel, Label unitedPercent, TextField purposeField, NodeParameter pos, Slider slider, int standard) {
        if (purposeComponent != null && nameLabel != null && unitedPercent != null && purposeField != null && pos != null) {
            final boolean isAcceptMinus = min < 0;
            if (standard <= max && standard >= min) {
                slider.setValue(standard);
            }
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);

            // Связываем изменение ползунка с текстовым полем
            slider.valueProperty().addListener((observable, oldValue, newValue) -> purposeField.setText(String.valueOf(newValue.intValue())));

            // Связываем изменение текстового поля с ползунком
            purposeField.textProperty().addListener((observable, oldVal, newVal) -> {
                if (!newVal.isEmpty()) {
                    try {
                        int currentVal = Integer.parseInt(newVal);
                        if (currentVal < min) {
                            purposeField.setText(String.valueOf(min));
                            slider.setValue(min);
                            return;
                        }
                        if (currentVal > max) {
                            purposeField.setText(String.valueOf(max));
                            slider.setValue(max);
                            return;
                        }
                        slider.setValue(currentVal);
                    } catch (NumberFormatException e) {
                        if (newVal.equals("-") && isAcceptMinus) {
                            return;
                        }
                        purposeField.setText(oldVal);
                    }
                } else {
                    slider.setValue(min); // Если поле пустое, устанавливаем ползунок на минимум
                }
            });

            double width = pos.width;
            double height = pos.height;
            double x = pos.x;
            double y = pos.y;

            purposeComponent.addNode(nameLabel, createRelativeSizeAndCoordinate(x, y, width * 0.3, height));
            purposeComponent.addNode(slider, createRelativeSizeAndCoordinate(x + width * 0.3, y, width * 0.5, height)); // Добавляем ползунок
            purposeComponent.addNode(purposeField, createRelativeSizeAndCoordinate(x + width * 0.8, y, width * 0.15, height));
            purposeComponent.addNode(unitedPercent, createRelativeSizeAndCoordinate(x + width * 0.95, y, width * 0.05, height));

        }
    }
}
