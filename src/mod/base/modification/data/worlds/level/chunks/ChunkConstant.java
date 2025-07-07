package mod.base.modification.data.worlds.level.chunks;

public final class ChunkConstant {

    public static final int STANDARD_CHUNK_SIZE = 32;
    public static final long mask = 0xFFFFFFFFL;

    public static Long compositeCoordinate(int x, int y) {
        return ((long)y << 32) + x;
    }

    public static Long compositeCoordinate(AbstractChunk chunk) {
        return ((long)chunk.getY() << 32) + chunk.getX();
    }
    public static int getGlobalCoordinate (int chunkCoordinate, int localInChunk) {
        return chunkCoordinate * STANDARD_CHUNK_SIZE + localInChunk;
    }
    public static int getChunkCoordinate (double c) {
        return (int) Math.floor (c / STANDARD_CHUNK_SIZE);
    }
    public static int[] decomposeCoordinate (long l) {
        return new int[] {(int) (l & mask), (int) ((l >>> 32) & mask)};
    }
}
