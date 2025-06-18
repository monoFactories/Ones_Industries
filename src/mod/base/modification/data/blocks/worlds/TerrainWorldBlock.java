package mod.base.modification.data.blocks.worlds;

import mod.base.modification.data.blocks.BlockInWorld;
import mod.base.modification.data.blocks.terrain.TerrainBlock;

public record TerrainWorldBlock(TerrainBlock block) implements BlockInWorld<TerrainBlock> {
    @Override
    public TerrainBlock getBlock() {
        return block;
    }
}
