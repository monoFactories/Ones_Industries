package mod.base.modification.graphics.components;

import game_logic.repositories.Identifier;
import mod.base.components.constants.SpecialConstant;
import mod.base.components.constants.TagConstant;
import mod.base.components.storages.SoundTagStorage;
import core.configs.settings.SettingsHandler;
import core.configs.settings.SoundSettings;
import core.gameActions.Debug;
import core.graphics.objects.GraphicComponent;
import core.graphics.objects.NodeParameter;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import mod.base.modification.repositories.sounds.AdvancedMusicPlayer;
import mod.base.modification.repositories.sounds.MediaTag;
import mod.base.modification.repositories.sounds.SoundRepository;

import java.util.HashSet;
import java.util.List;

public class SoundsGraphicComponent extends GraphicComponent {
    public SoundsGraphicComponent(Identifier id, AnchorPane customPane) {
        super(id, customPane);
    }

    public SoundsGraphicComponent(Identifier id) {
        super(id);
    }

    @Override
    public void addNode(Node node, NodeParameter parameter) {
        if (node != null && parameter != null) {
            Debug.debug(() -> "add new node in sounds graphic component [node = \"" + node + "\", parameter = \"" + parameter + "\"");
            String id = node.getId();
            if (id != null) {
                String[] parseID = id.split(";");
                if (new HashSet<>(List.of(parseID)).contains(SpecialConstant.INTERFACE.ID_ENABLED_SOUND)) {
                    Debug.debug("Node: " + node + " on add in sound graphic component");
                    setSoundsHandlers(node);
                }
            }
            super.addNode(node, parameter);
        }
    }
    private static void play(String soundTag) {
        MediaTag media = SoundRepository.sounds.get(SoundTagStorage.getNextID(soundTag));
        if (media != null) {
            Media realMedia = media.media();
            double volume = SettingsHandler.settings.getSound().getTypedVolume(media.type()) / 100.0;
            double overallVolume = SettingsHandler.settings.getSound().getTypedVolume(SoundSettings.TypeVolume.OVERALL) / 100.0;
            AdvancedMusicPlayer.singleMediaPlayer.play(realMedia, volume * overallVolume);
        }
    }
    public static void setSoundsHandlers (Node node) {
        EventHandler<? super MouseEvent> entered = node.getOnMouseEntered();
        EventHandler<? super MouseEvent> exited = node.getOnMouseExited();
        EventHandler<? super MouseEvent> pressed = node.getOnMousePressed();
        node.setOnMouseEntered(recombine(entered, TagConstant.SOUND.INTERFACE_MOUSE_ENTERED));
        node.setOnMouseExited(recombine(exited, TagConstant.SOUND.INTERFACE_MOUSE_EXITED));
        node.setOnMousePressed(recombine(pressed, TagConstant.SOUND.INTERFACE_MOUSE_CLICKED));
        if (node instanceof Slider slider) {
            slider.valueProperty().addListener((observer, oldValue, newValue) -> play(TagConstant.SOUND.INTERFACE_SLIDER_SCROLL));
        }
    }
    public static EventHandler<? super MouseEvent> recombine(EventHandler<? super MouseEvent> argumentHandler, String tag) {
        if (argumentHandler != null) {
            return  mouseEvent -> {
                play(tag);
                argumentHandler.handle(mouseEvent);
            };
        } else {
            return mouseEvent -> play(tag);
        }
    }
}
