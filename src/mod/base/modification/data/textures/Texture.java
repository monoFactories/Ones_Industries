package mod.base.modification.data.textures;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Texture implements AbstractTexture {

    private Image texture;

    public static final Texture UNKNOWN;
    static {
        WritableImage image = new WritableImage(2,2);
        image.getPixelWriter().setColor(0,0,Color.PURPLE);
        image.getPixelWriter().setColor(1,0,Color.BLACK);
        image.getPixelWriter().setColor(0,1,Color.BLACK);
        image.getPixelWriter().setColor(1,1,Color.PURPLE);
        UNKNOWN = new Texture(image);
    }

    protected Texture() {
    }
    public Texture(Image image) {
        this.texture = image;
    }

    @Override
    public Image getTexture (int numFrame) {
        return this.texture;
    }
}
