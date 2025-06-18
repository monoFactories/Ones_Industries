package mod.base.components.worlds.level.chunks;

import mod.base.modification.data.blocks.BlockInWorld;

public interface AbstractChunkEditor {
    void setBlock (BlockInWorld block, int x, int y);
    void setBlock (BlockInWorld block, int x, int y, int layer);
}
