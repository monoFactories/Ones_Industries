package mod.base.components.worlds.level.chunks.io;

import mod.base.components.worlds.level.chunks.AbstractChunk;

public interface AbstractChunkDeserializer {
    AbstractChunk fromData (byte[] data, ChunkDeserializerContext context);
}
