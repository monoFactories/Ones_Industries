package mod.base.modification.data.worlds.level;

import game_logic.repositories.Identifier;
import mod.base.modification.data.worlds.level.planets.AbstractPlanet;
import mod.base.modification.data.worlds.level.planets.PlanetParameter;

import java.util.List;

public interface AbstractLevel {
    void addPlanet(AbstractPlanet planet);
    void removePlanet(Identifier id);
    AbstractPlanet getPlanet (Identifier id);
    List<AbstractPlanet> getPlanets();
    LevelParameter getParameter();
}
