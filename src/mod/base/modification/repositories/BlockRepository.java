package mod.base.modification.repositories;

import core.management.DualRepository;
import mod.base.modification.data.blocks.Block;

public class BlockRepository extends DualRepository<Block> {
    public static final BlockRepository BLOCK_REGISTER = new BlockRepository();
}
