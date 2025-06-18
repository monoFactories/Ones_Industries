package mod.base.components.worlds.level.planets;

import mod.base.components.worlds.level.chunks.AbstractChunk;
import mod.base.components.worlds.level.chunks.ChunkConstant;
import mod.base.components.worlds.render.BlockSource;
import mod.base.modification.data.blocks.BlockInWorld;

import java.util.concurrent.ConcurrentHashMap;

public class SimplePlanet implements AbstractPlanet {

    private final PlanetParameter parameter;
    private final ConcurrentHashMap<Long, AbstractChunk> chunks;

    public SimplePlanet (PlanetParameter parameter) {
        this.parameter = parameter;
        chunks = new ConcurrentHashMap<>();
    }
    @Override
    public PlanetParameter getParameter() {
        return parameter;
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
}
