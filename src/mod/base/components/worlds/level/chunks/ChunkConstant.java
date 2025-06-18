package mod.base.components.worlds.level.chunks;

public final class ChunkConstant {

    public static final int STANDARD_CHUNK_SIZE = 32;

    public static Long compositeCoordinate(int x, int y) {
        return ((long)y << 32) + x;
    }

    public static Long compositeCoordinate(AbstractChunk chunk) {
        return ((long)chunk.getY() << 32) + chunk.getX();
    }
    public static int getGlobalCoordinate (int chunkCoordinate, int localInChunk) {
        return chunkCoordinate * STANDARD_CHUNK_SIZE + localInChunk;
    }
}
