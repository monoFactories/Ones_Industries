package mod.base.modification.data.blocks.terrain;

import mod.base.modification.data.blocks.Block;
import mod.base.modification.data.blocks.BlockInWorld;
import mod.base.modification.data.blocks.BlockTextureParameter;
import mod.base.modification.data.blocks.WorldBlock;
import mod.base.modification.data.blocks.worlds.TerrainWorldBlock;

import java.util.Objects;

public abstract class TerrainBlock extends Block<TerrainWorldBlock> implements AbstractTerrainBlock{
    private TerrainParameter terrainParameter;

    public TerrainBlock (String id, TerrainParameter terrainParameter) {
        super(id);
        if (terrainParameter == null)
            throw new IllegalArgumentException("TerrainParameter couldn't be null");
        this.terrainParameter = terrainParameter;
    }

    public void setTerrainParameter(TerrainParameter terrainParameter) {
        if (terrainParameter != null)
            this.terrainParameter = terrainParameter;
    }

    @Override
    public TerrainParameter getTerrainParameter() {
        return terrainParameter;
    }

    @Override
    public abstract BlockTextureParameter getTexture(TerrainWorldBlock blockWorld);

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof TerrainBlock that)) return false;
        return Objects.equals(terrainParameter, that.terrainParameter) && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(terrainParameter, getId());
    }
}
