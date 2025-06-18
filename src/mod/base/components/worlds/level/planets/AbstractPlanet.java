package mod.base.components.worlds.level.planets;

import mod.base.components.worlds.level.chunks.ChunkConstant;
import mod.base.components.worlds.render.BlockSource;

public interface AbstractPlanet {
    PlanetParameter getParameter();
    AbstractPlanetEditor getPlanetEditor();
    BlockSource toBlockSource();
    static int[] decomposeCoordinate (int x, int y) {
        int chunkX = (int) Math.floor((double) x / ChunkConstant.STANDARD_CHUNK_SIZE);
        int chunkY = (int) Math.floor((double) y / ChunkConstant.STANDARD_CHUNK_SIZE);
        int blockX = x - ChunkConstant.STANDARD_CHUNK_SIZE * chunkX;
        int blockY = y - ChunkConstant.STANDARD_CHUNK_SIZE * chunkY;
        return new int[] {chunkX, chunkY, blockX, blockY};
    }
}
