package mod.base.modification.data.worlds.level.generation;

import mod.base.modification.data.worlds.level.chunks.AbstractChunk;
import mod.base.modification.data.worlds.level.planets.AbstractPlanetNoiseParameter;

public interface AbstractChunkGenerator {
    void fillGroundLayer (AbstractChunk chunk, AbstractPlanetNoiseParameter noise);
}
