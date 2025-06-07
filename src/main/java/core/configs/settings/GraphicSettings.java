package core.configs.settings;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class GraphicSettings {
    private double saturation, brightness, contrast;
    private double width;
    private double height;
    private boolean fullScreen;
    private double FPS;

    public GraphicSettings(double brightness, double contrast, double saturation, double width, double height, boolean fullScreen, double fps) {
        this.brightness = brightness;
        this.contrast = contrast;
        this.saturation = saturation;
        this.width = width;
        this.height = height;
        this.fullScreen = fullScreen;
        this.FPS = fps;
    }
    public GraphicSettings() {}
    public double getBrightness() {
        return brightness;
    }

    public void setBrightness(double brightness) {
        this.brightness = brightness;
    }

    public double getContrast() {
        return contrast;
    }

    public void setContrast(double contrast) {
        this.contrast = contrast;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public boolean isFullScreen() {
        return fullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public double getFPS() {
        return FPS;
    }

    public void setFPS(double FPS) {
        this.FPS = FPS;
    }

    @Override
    public String toString() {
        return "GraphicSettings{" +
                "brightness=" + brightness +
                ", saturation=" + saturation +
                ", contrast=" + contrast +
                ", width=" + width +
                ", height=" + height +
                ", fullScreen=" + fullScreen +
                '}';
    }

    public static GraphicSettings getStandard () {
        Rectangle2D win = Screen.getPrimary().getVisualBounds();
        return new GraphicSettings(0, 0, 0, win.getWidth(), win.getHeight(), true, 60.0);
    }
    public static GraphicSettings check (GraphicSettings gs) {
        if (gs != null) {
            gs.saturation = Math.clamp(gs.saturation, -1.0, 1.0);
            gs.brightness = Math.clamp(gs.brightness, -1.0, 1.0);
            gs.contrast = Math.clamp(gs.contrast, -1.0, 1.0);
            gs.width = Math.clamp(gs.width, 100.0, 9999);
            gs.height = Math.clamp(gs.height, 100.0, 9999);
            gs.FPS = Math.clamp(gs.FPS, 0.0625, 99999.99);
            return gs;
        } else
            return getStandard();
    }
}
