package mod.base.modification.data.blocks;

import javafx.scene.image.Image;

public record BlockTextureParameter(double sizeX, double sizeY, double offsetX, double offsetY, Image image) {
    public static BlockTextureParameter getInstance (Image image) {
        return new BlockTextureParameter(1, 1, 0, 0, image);
    }
}
