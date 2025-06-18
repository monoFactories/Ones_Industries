package mod.base.components.worlds.level;

import mod.base.components.worlds.level.planets.AbstractPlanet;
import mod.base.components.worlds.level.planets.PlanetParameter;

import java.util.List;

public interface AbstractLevel {
    void addPlanet(AbstractPlanet planet);
    void removePlanet(PlanetParameter parameter);
    AbstractPlanet getPlanet (PlanetParameter parameter);
    List<AbstractPlanet> getPlanets();
}
