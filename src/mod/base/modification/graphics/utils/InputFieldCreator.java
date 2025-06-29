package mod.base.modification.graphics.utils;

import core.graphics.objects.GraphicComponent;
import core.graphics.objects.NodeParameter;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

import static core.graphics.objects.NodeParameter.createRelativeSizeAndCoordinate;

public class InputFieldCreator {
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

    public static LabelFieldButtonEntry createLFBE (TypeInputInField type, NodeParameter parameterOfField, double ratioLabel, double ratioField, double ratioBtn) {
        Label label = new Label();
        TextField field = getFieldForType(type);
        Button btn = new Button ();
        double sumRatio = ratioLabel + ratioField + ratioBtn;
        double x = parameterOfField.x;
        double width = ratioLabel / sumRatio * parameterOfField.width;
        NodeParameter labelParam = createRelativeSizeAndCoordinate (x, parameterOfField.y, width, parameterOfField.height);
        x += width;
        width = ratioField / sumRatio * parameterOfField.width;
        NodeParameter fieldParam = createRelativeSizeAndCoordinate (x, parameterOfField.y, width, parameterOfField.height);
        x += width;
        width = ratioBtn / sumRatio * parameterOfField.width;
        NodeParameter btnParam = createRelativeSizeAndCoordinate (x, parameterOfField.y, width, parameterOfField.height);
        return new LabelFieldButtonEntry(label, labelParam, field, fieldParam, btn, btnParam);
    }
    public static TextField getFieldForType (TypeInputInField type) {
        TextField field = new TextField();
        switch (type) {
            case INT -> field.textProperty().addListener((o, l, n) -> {
                if (!n.isEmpty()) {
                    try {
                        if (n.equals("-"))
                            return;
                        int d = Integer.parseInt(n);
                    } catch (NumberFormatException e) {
                        field.setText(l);
                    }
                }
            });
            case LONG -> field.textProperty().addListener((o, l, n) -> {
                if (!n.isEmpty()) {
                    try {
                        if (n.equals("-"))
                            return;
                        long d = Long.parseLong(n);
                    } catch (NumberFormatException e) {
                        field.setText(l);
                    }
                }
            });
            case DOUBLE -> field.textProperty().addListener((o, l, n) -> {
                if (!n.isEmpty()) {
                    if (!validDoubleInput(n))
                        field.setText(l);
                }
            });
        }
        return field;
    }
    public static boolean validDoubleInput(String v) {
        if (v.isEmpty()) return true; // пустая строка - промежуточное состояние

        char[] elements = v.toCharArray();
        boolean havePow = false;      // встретилась ли 'e'
        boolean havePoint = false;    // встретилась ли '.'
        boolean haveDigitBeforeE = false; // были ли цифры до 'e'
        boolean haveDigitAfterE = true;   // по умолчанию true, проверим после 'e'

        for (int i = 0; i < elements.length; i++) {
            char c = elements[i];

            if (c == '-') {
                // Минус может быть только в начале или сразу после 'e'
                if (i == 0) continue;
                if (elements[i - 1] == 'e') continue;
                return false;
            }

            if (c == '+') {
                // Плюс может быть только сразу после 'e'
                if (i == 0) return false;
                if (elements[i - 1] == 'e') continue;
                return false;
            }

            if (c == 'e' || c == 'E') {
                if (havePow) return false; // второй 'e' недопустим
                if (i == 0) return false;  // 'e' не может быть первым символом
                havePow = true;
                haveDigitAfterE = false; // после 'e' должны быть цифры
                continue;
            }

            if (c == '.') {
                if (havePoint || havePow) return false; // точка не может быть после 'e' или второй раз
                havePoint = true;
                continue;
            }

            if (c >= '0' && c <= '9') {
                if (!havePow) haveDigitBeforeE = true;
                else haveDigitAfterE = true;
                continue;
            }

            // Если символ не распознан, возвращаем false
            return false;
        }

        // Проверяем, что перед 'e' есть цифры
        if (!haveDigitBeforeE) return false;
        // Если есть 'e', то после него тоже должны быть цифры
        if (havePow && !haveDigitAfterE) return false;

        return true;
    }

    public record LabelFieldButtonEntry(Label label, NodeParameter labelParam, TextField field, NodeParameter fieldParam, Button btn, NodeParameter btnParam) {
        public void addOnComponent (GraphicComponent gc) {
            if (gc != null) {
                gc.addNode(label, labelParam);
                gc.addNode(btn, btnParam);
                gc.addNode(field, fieldParam);
            }
        }
    }
    public enum TypeInputInField {
        INT,
        LONG,
        DOUBLE,
        ANY
    }
}
