package mod.base.components.worlds.level.chunks;

import mod.base.components.worlds.level.chunks.io.AbstractChunkSerializer;
import mod.base.modification.data.blocks.BlockInWorld;

public interface AbstractChunk {
    BlockInWorld getBlock(int x, int y);
    BlockInWorld getBlock(int x, int y, int layer);
    AbstractChunkEditor getChunkEditor();
    AbstractChunkSerializer getSerializer();
    int getX();
    int getY();
}
