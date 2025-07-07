package mod.base.modification.data.worlds.level.planets;

import game_logic.repositories.Identifier;

public record PlanetParameter(Identifier id) {
    public PlanetParameter {
        if (id == null) {
            throw new NullPointerException("planet id couldn't be null");
        }
    }
}
