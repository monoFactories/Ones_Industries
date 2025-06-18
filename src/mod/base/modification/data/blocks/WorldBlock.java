package mod.base.modification.data.blocks;

public interface WorldBlock <T extends BlockInWorld> {
    BlockTextureParameter getTexture (T blockWorld);
    T getBlock ();
}
