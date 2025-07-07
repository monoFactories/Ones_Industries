package mod.base.modification.data.worlds.level.planets;

import mod.base.modification.data.worlds.level.chunks.AbstractChunk;

import java.util.function.BiConsumer;

public interface AbstractPlanetEditor {
    boolean addChunk(AbstractChunk chunk);
    boolean removeChunk (AbstractChunk chunk);
    boolean removeChunk(int x, int y);
    AbstractChunk getChunk(int x, int y);
    void forEach (BiConsumer<Long, AbstractChunk> fun);
}
