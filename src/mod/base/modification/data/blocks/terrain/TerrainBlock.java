package mod.base.modification.data.blocks.terrain;

import game_logic.repositories.Identifier;
import mod.base.modification.data.blocks.Block;

import java.util.Objects;

public abstract class TerrainBlock extends Block implements AbstractTerrainBlock {
    private TerrainParameter terrainParameter;

    public TerrainBlock (Identifier id, TerrainParameter terrainParameter) {
        super(id);
        if (terrainParameter == null)
            throw new IllegalArgumentException ("TerrainParameter couldn't be null");
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
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof TerrainBlock that)) return false;
        return Objects.equals(terrainParameter, that.terrainParameter) && getID().equals(that.getID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(terrainParameter, getID());
    }
}
