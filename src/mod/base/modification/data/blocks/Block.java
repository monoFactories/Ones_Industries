package mod.base.modification.data.blocks;

import mod.base.modification.data.blocks.worlds.TerrainWorldBlock;
import mod.base.modification.data.textures.AbstractTexture;

public abstract class Block  {
    protected AbstractTexture texture;
    private final String id;

    public Block (String id) {
        this.id = id;
    }
    public void setTexture (AbstractTexture texture1) {
        this.texture = texture1;
    }

    public String getId() {
        return id;
    }

    public abstract BlockTextureParameter getTexture (BlockInWorld blockInWorld);
    public abstract BlockInWorld getBlock();

    public abstract BlockTextureParameter getTexture(TerrainWorldBlock blockWorld);
}
