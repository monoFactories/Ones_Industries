package mod.base.components.registration.data;

import core.management.DualRepository;
import core.moding.mod.ModResourceReader;
import mod.base.modification.Base;
import mod.base.modification.data.blocks.Block;
import mod.base.modification.data.blocks.terrain.GroundBlock;
import mod.base.modification.data.blocks.terrain.TerrainParameter;
import mod.base.modification.data.textures.TextureLoader;
import mod.base.modification.repositories.SimpleModRepository;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;

public class BaseBlocks {
    private static final DualRepository.AbstractSingleRepository<Block> blocks = ((SimpleModRepository) Base.getMod().getModRegister()).blocks;
    static {
        try (ModResourceReader mrr = Base.getMod().getParameter().createModResourceReader()) {
            register (new GroundBlock (Base.getID("lands/grass_1.png"), new TerrainParameter (0.4, 0.85, 0.25, 0.85)), mrr, "lands/grass_1.png", 1, 1);
            register (new GroundBlock (Base.getID("lands/grass_2.png"), new TerrainParameter (0.1, 0.55, 0.65, 0.95)), mrr, "lands/grass_2.png", 2, 2);
            register (new GroundBlock (Base.getID("lands/grass_3.png"), new TerrainParameter (0.3, 0.75, 0.35, 0.75)), mrr, "lands/grass_3.png", 2, 2);
            register (new GroundBlock (Base.getID("lands/deep_water.png"), new TerrainParameter (0.75, 1.0, 0.0, 0.45)), mrr, "lands/deep_water.png", 2, 2);
        } catch (Exception e) {
            //System.out.println ("error in loading blocks" + e);
        }

    }
    private static Block register (Block block, ModResourceReader mrr, String res, int w, int h) {
        InputStream is = mrr.getResource ("resources/textures/blocks/" + res);
        if (is == null)
            System.out.println("I.S. null");
        block.setTexture(TextureLoader.get (is, w, h));
        blocks.add (block.getID(), block);
        return block;
    }
}
