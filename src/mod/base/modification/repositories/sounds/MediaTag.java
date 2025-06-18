package mod.base.modification.repositories.sounds;

import core.configs.settings.SoundSettings;
import game_logic.repositories.Identifier;
import javafx.scene.media.Media;

import java.util.Objects;

public final class MediaTag {
    private final Media media;
    private final String[] tags;
    private final SoundSettings.TypeVolume type;
    private final Identifier id;
    public  MediaTag (Identifier id, Media media, String[] tag, SoundSettings.TypeVolume type) {
        if (media != null && id != null) {
            this.id = id;
            this.media = media;
            this.tags = Objects.requireNonNullElseGet(tag, () -> new String[0]);
            if (type == null || type == SoundSettings.TypeVolume.OVERALL)
                type = SoundSettings.TypeVolume.OTHER;
            this.type = type;
        } else {
            throw new NullPointerException("media and id couldn't null");
        }
    }

    public Identifier id() {
        return id;
    }

    public Media media() {
        return media;
    }

    public String[] tags() {
        return tags;
    }

    public SoundSettings.TypeVolume type() {
        return type;
    }
}
