package mod.base.modification.data.blocks;

import mod.base.modification.data.textures.AbstractTexture;

public abstract class Block <T extends BlockInWorld> {
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

    public abstract BlockTextureParameter getTexture (T blockInWorld);
    public abstract T getBlock();
}
