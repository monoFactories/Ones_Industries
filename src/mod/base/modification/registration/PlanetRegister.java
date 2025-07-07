package mod.base.modification.registration;

import core.management.DualRepository;
import game_logic.repositories.Identifier;
import mod.base.modification.data.worlds.level.planets.PlanetDescription;
import mod.base.modification.repositories.Registration;

public class PlanetRegister {
    public static final String KEY_PLANET = "planets_descriptions";
    private static final DualRepository<PlanetDescription> planets = new DualRepository<>();

    static {
        Registration.register.put (KEY_PLANET, planets);
    }

    public static PlanetDescription addPlanet (PlanetDescription description) {
        if (description != null) {
            planets.add(description.getID(), description);
        }
        return description;
    }
    public static PlanetDescription getPlanet (Identifier id) {
        return planets.get(id);
    }

    public static DualRepository<PlanetDescription> planets() {
        return planets;
    }
}
