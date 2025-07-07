package mod.base.components.worlds.level;

import game_logic.repositories.Identifier;
import mod.base.modification.data.worlds.level.LevelParameter;
import mod.base.modification.data.worlds.level.planets.AbstractPlanet;
import mod.base.modification.data.worlds.level.planets.PlanetParameter;
import mod.base.modification.data.worlds.level.AbstractLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleLevel implements AbstractLevel {

    private final LevelParameter parameter;
    private final ConcurrentHashMap<Identifier, AbstractPlanet> planets;

    public SimpleLevel (long seed) {
        this.planets = new ConcurrentHashMap<>();
        this.parameter = new LevelParameter(seed);
    }
    @Override
    public void addPlanet(AbstractPlanet planet) {
        if (planet != null) {
            planets.putIfAbsent (planet.getID(), planet);
        }
    }

    @Override
    public void removePlanet(Identifier id) {
        if (id != null) {
            planets.remove(id);
        }
    }

    @Override
    public AbstractPlanet getPlanet(Identifier id) {
        if (id != null) {
            return planets.get(id);
        }
        return null;
    }

    @Override
    public List<AbstractPlanet> getPlanets() {
        return new ArrayList<>(planets.values());
    }

    @Override
    public LevelParameter getParameter() {
        return parameter;
    }
}
