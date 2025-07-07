package mod.base.components.worlds.level.planets;

import game_logic.repositories.Identifier;
import mod.base.modification.data.worlds.level.planets.RegisterPlanet;

public class PlanetUtil {
    private static Identifier startPlanetIdentifier = RegisterPlanet.START.getID();
    public static void setStartPlanetIdentifier(Identifier startPlanetIdentifier1) {
        startPlanetIdentifier = startPlanetIdentifier1;
    }
    public static Identifier getStartPlanetIdentifier() {
        return startPlanetIdentifier;
    }
}
