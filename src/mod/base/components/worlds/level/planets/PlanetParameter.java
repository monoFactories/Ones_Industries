package mod.base.components.worlds.level.planets;

public record PlanetParameter(String id) {
    public PlanetParameter {
        if (id == null) {
            throw new NullPointerException("planet id couldn't be null");
        }
    }
}
