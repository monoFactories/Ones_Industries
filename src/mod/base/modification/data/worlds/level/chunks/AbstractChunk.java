package mod.base.modification.data.worlds.level.chunks;

import mod.base.modification.data.blocks.BlockInWorld;

public interface AbstractChunk {
    BlockInWorld getBlock(int x, int y);
    BlockInWorld getBlock(int x, int y, int layer);
    AbstractChunkEditor getChunkEditor();
    int getX();
    int getY();
}
