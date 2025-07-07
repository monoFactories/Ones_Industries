package mod.base.components.worlds.party.level.input;

import mod.base.components.worlds.level.chunks.ChunkUtil;
import mod.base.components.worlds.party.GameParty;
import mod.base.modification.Base;
import mod.base.modification.data.worlds.level.chunks.AbstractChunk;
import mod.base.modification.data.worlds.level.planets.AbstractPlanet;

public class OfflineChunkInput implements BlockInput {
    @Override
    public AbstractChunk get(GameParty party, AbstractPlanet planet, int x, int y) {
        AbstractChunk chunk = null;
        try {
            chunk = ChunkUtil.getCurrentRawChunkCreator().apply(x, y);
        } catch (Throwable u238) {
            Base.getMod().debug("not get raw chunk", u238);
        }
        if (chunk == null) {
            chunk = ChunkUtil.getStandardRawCreator().apply(x, y);
        }
        try {
            ChunkUtil.getCurrentGroundChunkCreator().get().fillGroundLayer (chunk, planet.getGenerationParameter());
        } catch (Throwable u238) {
            Base.getMod().debug("couldn't fill first layer", u238);
            ChunkUtil.getStandardGroundCreator().get().fillGroundLayer (chunk, planet.getGenerationParameter());
        }
        return chunk;
    }
}
