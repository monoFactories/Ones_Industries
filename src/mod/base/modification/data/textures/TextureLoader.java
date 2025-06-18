package mod.base.modification.data.textures;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.io.InputStream;
import java.util.Objects;

public class TextureLoader {
    /// @param spriteWidth - the count of sprites in width in image
    public static AbstractTexture get (InputStream is, int spriteWidth, int spriteHeight) {
        try {
            Image image = new Image(Objects.requireNonNull(is));
            if (spriteHeight == 1 && spriteWidth == 1) {
                return new Texture(image);
            } else {
                int widthSprite = (int)(image.getWidth() / spriteWidth);
                int heightSprite = (int) (image.getHeight() / spriteHeight);
                Image[][] matrixImage = new Image[spriteHeight][spriteWidth];
                for (int y = 0; y < spriteHeight; y++) {
                    for (int x = 0; x < spriteWidth; x++) {
                        WritableImage sprite = new WritableImage(image.getPixelReader(), x * widthSprite, y * heightSprite, widthSprite, heightSprite);
                        matrixImage[y][x] = sprite;
                    }
                }
                return new SpriteTexture(matrixImage);
            }
        } catch (Exception e) {
            return Texture.UNKNOWN;
        }
    }

    public static AbstractTexture get (TextureLoadingParameter parameter) {
        if (parameter != null)
            return get(parameter.is(), parameter. spriteWidth(), parameter.spriteHeight());
        else
            throw new NullPointerException("parameter cannot be null");
    }
}
