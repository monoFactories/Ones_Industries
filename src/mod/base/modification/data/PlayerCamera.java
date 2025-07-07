package mod.base.modification.data;

import game_logic.repositories.Identifier;

public class PlayerCamera {
    private Identifier planetId;
    private double x, y, zoom;

    public PlayerCamera() {
        zoom = 1.0;
    }

    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public void setPlanetId(Identifier planetId) {
        this.planetId = planetId;
    }
    public Identifier getPlanetId() {
        return planetId;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
