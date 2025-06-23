package mod.base.components.filling.fillsounds;

import mod.base.components.constants.SpecialConstant;
import mod.base.components.constants.TagConstant;
import core.gameActions.Debug;
import javafx.scene.media.Media;
import mod.base.modification.Base;
import mod.base.modification.repositories.SimpleModRepository;
import mod.base.modification.repositories.sounds.MediaTag;

import static core.configs.settings.SoundSettings.TypeVolume;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;

public class SoundRegister {
    public final static MediaTag
    INTERFACE_MOUSE_ENTERED = register (SpecialConstant.SOUND_ID.MOUSE_ENTERED, "interfaces/enter", TypeVolume.INTERFACE, TagConstant.SOUND.INTERFACE_MOUSE_ENTERED),
    INTERFACE_MOUSE_CLICKED = register(SpecialConstant.SOUND_ID.MOUSE_CLICKED, "interfaces/click", TypeVolume.INTERFACE, TagConstant.SOUND.INTERFACE_MOUSE_CLICKED),
    INTERFACE_MOUSE_EXITED = register(SpecialConstant.SOUND_ID.MOUSE_EXITED, "interfaces/exit", TypeVolume.INTERFACE, TagConstant.SOUND.INTERFACE_MOUSE_EXITED),
    INTERFACE_SLIDER_SCROLL = register(SpecialConstant.SOUND_ID.SLIDER_SCROLL, "interfaces/scroll", TypeVolume.INTERFACE, TagConstant.SOUND.INTERFACE_SLIDER_SCROLL);


    private static MediaTag register (String localId, String relativeMediaPath, TypeVolume type, String... tags) {
        if (localId != null && relativeMediaPath != null) {
            Base.getMod().debug ("add sound with parameters: " + localId + ", " + relativeMediaPath + ", " + type + ", " + Arrays.toString(tags));
            String fullPath = "/resources/sounds/" + relativeMediaPath + ".mp3";
            URL u = SoundRegister.class.getResource(fullPath);
            if (u != null) {
                try {
                    Media m = new Media(u.toExternalForm());
                    MediaTag mt = new MediaTag(Base.getID(localId), m, tags, type != null ? type : TypeVolume.OVERALL);
                    ((SimpleModRepository) Base.getMod().getModRegister()).media.add(mt.id(), mt);
                    return mt;
                    //modsRep.add(id, new MediaTag(m, tags, type));
                } catch (Exception e) {
                    Debug.debug(Level.INFO, "Couldn't read sound with path: \"" + relativeMediaPath + "\"", e);
                }
            }
        }
        return null;
    }
}
