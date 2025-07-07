package mod.base.components.worlds.party.level.input;

import mod.base.components.worlds.party.GameParty;
import mod.base.modification.data.worlds.level.chunks.AbstractChunk;
import mod.base.modification.data.worlds.level.planets.AbstractPlanet;

public interface BlockInput {
    AbstractChunk get (GameParty party, AbstractPlanet planet, int x, int y);
}
