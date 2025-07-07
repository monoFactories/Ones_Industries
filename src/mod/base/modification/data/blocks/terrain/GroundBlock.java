package mod.base.modification.data.blocks.terrain;

import game_logic.repositories.Identifier;
import mod.base.modification.data.blocks.BlockInWorld;
import mod.base.modification.data.blocks.BlockTextureParameter;
import mod.base.modification.data.blocks.worlds.TerrainWorldBlock;

public class GroundBlock extends TerrainBlock {

    public GroundBlock(Identifier id, TerrainParameter terrainParameter) {
        super(id, terrainParameter);
    }

    //@Override
    //public BlockTextureParameter getTexture(TerrainWorldBlock blockWorld) {
    //    return BlockTextureParameter.getInstance(texture.getTexture(0));
    //}

    @Override
    public BlockTextureParameter getTexture(BlockInWorld blockInWorld) {
        if (blockInWorld instanceof TerrainWorldBlock twb)
            return BlockTextureParameter.getInstance(texture.getTexture(0));
        return null;
    }

    @Override
    public TerrainWorldBlock getBlock() {
        return new TerrainWorldBlock(this);
    }
}
