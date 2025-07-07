package mod.base.modification.data.worlds.render;

import mod.base.modification.data.blocks.BlockInWorld;

public interface BlockSource {
    BlockInWorld getBlock (int x, int y, int layer);
}
