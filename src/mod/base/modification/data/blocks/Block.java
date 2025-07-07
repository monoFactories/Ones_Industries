package mod.base.modification.data.blocks;

import game_logic.repositories.HaveIdentifier;
import game_logic.repositories.Identifier;
import mod.base.modification.data.blocks.worlds.TerrainWorldBlock;
import mod.base.modification.data.textures.AbstractTexture;

public abstract class Block implements HaveIdentifier {
    protected AbstractTexture texture;
    private final Identifier id;

    public Block (Identifier id) {
        this.id = id;
    }
    public void setTexture (AbstractTexture texture1) {
        this.texture = texture1;
    }

    public Identifier getID() {
        return id;
    }

    public abstract BlockTextureParameter getTexture (BlockInWorld blockInWorld);
    public abstract BlockInWorld getBlock();
}
