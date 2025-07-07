package mod.base.modification.data.worlds.level.planets;

import game_logic.repositories.HaveIdentifier;
import mod.base.modification.data.worlds.level.AbstractLevel;
import mod.base.modification.data.worlds.level.chunks.ChunkConstant;
import mod.base.modification.data.worlds.render.BlockSource;

public interface AbstractPlanet extends HaveIdentifier {
    AbstractPlanetEditor getPlanetEditor();
    BlockSource toBlockSource();
    PlanetDescription description ();
    AbstractLevel getLevel ();
    AbstractPlanetNoiseParameter getGenerationParameter ();

    static int[] decomposeCoordinate (int x, int y) {
        int chunkX = (int) Math.floor((double) x / ChunkConstant.STANDARD_CHUNK_SIZE);
        int chunkY = (int) Math.floor((double) y / ChunkConstant.STANDARD_CHUNK_SIZE);
        int blockX = x - ChunkConstant.STANDARD_CHUNK_SIZE * chunkX;
        int blockY = y - ChunkConstant.STANDARD_CHUNK_SIZE * chunkY;
        return new int[] {chunkX, chunkY, blockX, blockY};
    }
}
