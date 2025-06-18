package mod.base.components.worlds.level;

import mod.base.components.worlds.level.planets.AbstractPlanet;
import mod.base.components.worlds.level.planets.PlanetParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleLevel implements AbstractLevel {

    public static AbstractLevel level = new SimpleLevel();

    private final ConcurrentHashMap<PlanetParameter, AbstractPlanet> planets;
    public SimpleLevel () {
        this.planets = new ConcurrentHashMap<>();
    }
    @Override
    public void addPlanet(AbstractPlanet planet) {
        if (planet != null) {
            planets.putIfAbsent(planet.getParameter(), planet);
        }
    }

    @Override
    public void removePlanet(PlanetParameter parameter) {
        if (parameter != null) {
            planets.remove(parameter);
        }
    }

    @Override
    public AbstractPlanet getPlanet(PlanetParameter parameter) {
        if (parameter != null) {
            return planets.get(parameter);
        }
        return null;
    }

    @Override
    public List<AbstractPlanet> getPlanets() {
        return new ArrayList<>(planets.values());
    }
}
