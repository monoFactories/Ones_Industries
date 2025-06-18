package mod.base.modification.data.textures;

import java.io.InputStream;

public record TextureLoadingParameter(InputStream is, int spriteWidth, int spriteHeight) {
    public TextureLoadingParameter {
        if (is == null) {
            throw new NullPointerException("path cannot be null");
        }
        if (spriteWidth < 0 || spriteHeight < 0) {
            throw new IllegalArgumentException("width and height is positive values");
        }
    }
}
