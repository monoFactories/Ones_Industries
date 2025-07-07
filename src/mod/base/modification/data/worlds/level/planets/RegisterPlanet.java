package mod.base.modification.data.worlds.level.planets;

import mod.base.modification.Base;
import mod.base.modification.registration.PlanetRegister;

public class RegisterPlanet {
    public static final PlanetDescription
    START = PlanetRegister.addPlanet(new PlanetDescription(Base.getID("start"), null, "start", null, null));
}
