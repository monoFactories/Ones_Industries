package mod.base.modification.repositories;

import core.management.DualRepository;
import core.moding.data.ModRegister;
import core.moding.mod.Mod;
import mod.base.modification.data.blocks.Block;
import mod.base.modification.repositories.sounds.MediaTag;

public class SimpleModRepository extends ModRegister {
    public final DualRepository.SingleRepository<Block> blocks;
    public final DualRepository.SingleRepository<MediaTag> media;

    public SimpleModRepository (Mod mod) {
        super(mod);
        this.blocks = new DualRepository.SingleRepository<>();
        media = new DualRepository.SingleRepository<>();
    }
}
