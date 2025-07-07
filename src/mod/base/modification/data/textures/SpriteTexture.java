package mod.base.modification.data.textures;

import javafx.scene.image.Image;
import game_logic.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpriteTexture implements AbstractTexture {
    private final Image[] frames;

    public SpriteTexture(List<Image> frames) {
        if (frames == null)
            this.frames = new Image[0];
        else {
            this.frames = frames.toArray(new Image[0]);
        }
    }
    public SpriteTexture(Image[][] imageMatrix) {
        if (imageMatrix == null)
            throw new NullPointerException("imageMatrix cannot be null");
        List<Image> imageList = new ArrayList<>();
        for (Image[] matrix : imageMatrix) {
            imageList.addAll (Arrays.asList(matrix));
        }
        this.frames = imageList.toArray(new Image[0]);
    }

    @Override
    public Image getTexture(int numFrame) {
        if (numFrame < 0 || numFrame >= frames.length)
            return null;
        return frames [numFrame];
    }

    public int getCountFrames () {
        return frames.length;
    }
}
