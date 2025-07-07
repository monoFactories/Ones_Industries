package mod.base.components.worlds.level.planets;

import game_logic.repositories.Identifier;
import mod.base.modification.data.worlds.level.AbstractLevel;
import mod.base.modification.data.worlds.level.chunks.AbstractChunk;
import mod.base.modification.data.worlds.level.chunks.ChunkConstant;
import mod.base.modification.data.worlds.level.planets.*;
import mod.base.modification.data.worlds.render.BlockSource;
import mod.base.modification.data.blocks.BlockInWorld;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public abstract class SimplePlanet implements AbstractPlanet {

    private AbstractPlanetNoiseParameter generationParameter;
    private final Identifier planetId;
    private final ConcurrentHashMap<Long, AbstractChunk> chunks;

    public SimplePlanet (Identifier id) {
        if (id == null)
            throw new NullPointerException ("invalid id");
        this.planetId = id;
        chunks = new ConcurrentHashMap<>();
    }

    @Override
    public AbstractPlanetEditor getPlanetEditor() {
        return new AbstractPlanetEditor() {
            @Override
            public boolean addChunk(AbstractChunk chunk) {
                return chunks.put(ChunkConstant.compositeCoordinate(chunk.getX(), chunk.getY()), chunk) == null;
            }

            @Override
            public boolean removeChunk(AbstractChunk chunk) {
                return chunks.remove(ChunkConstant.compositeCoordinate(chunk)) != null;
            }

            @Override
            public boolean removeChunk(int x, int y) {
                return chunks.remove(ChunkConstant.compositeCoordinate(x, y)) != null;
            }

            @Override
            public AbstractChunk getChunk(int x, int y) {
                return chunks.get(ChunkConstant.compositeCoordinate(x, y));
            }

            @Override
            public void forEach(BiConsumer<Long, AbstractChunk> fun) {
                chunks.forEach (fun);
            }
        };
    }

    @Override
    public BlockSource toBlockSource() {
        return new BlockSource() {
            int lastChunkX;
            int lastChunkY;
            AbstractChunk lastCalledChunk;
            @Override
            public BlockInWorld getBlock(int x, int y, int layer) {
                int[] coordinates = AbstractPlanet.decomposeCoordinate(x, y);
                if (lastCalledChunk == null || lastChunkX != coordinates[0] || lastChunkY != coordinates[1]) {
                    lastCalledChunk = getPlanetEditor().getChunk(coordinates[0], coordinates[1]);
                }
                if (lastCalledChunk != null) { 
                    return lastCalledChunk.getBlock(coordinates[2], coordinates[3]);
                }
                return null;
            }
        };
    }

    @Override
    public abstract PlanetDescription description();

    @Override
    public abstract AbstractLevel getLevel();

    @Override
    public Identifier getID() {
        return planetId;
    }

    @Override
    public AbstractPlanetNoiseParameter getGenerationParameter() {
        if (generationParameter == null)
            generationParameter = description().getNoiseParameter().apply(this);
        return generationParameter;
    }
}
