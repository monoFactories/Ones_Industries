package mod.base.components.worlds.level.chunks.io;

public interface AbstractChunkSerializer {
    byte[] toData (ChunkSerializerContext context);
}
