package mod.base.components.worlds.level.planets;

import mod.base.components.worlds.level.chunks.AbstractChunk;

public interface AbstractPlanetEditor {
    boolean addChunk(AbstractChunk chunk);
    boolean removeChunk(AbstractChunk chunk);
    boolean removeChunk(int x, int y);
    AbstractChunk getChunk(int x, int y);
}
