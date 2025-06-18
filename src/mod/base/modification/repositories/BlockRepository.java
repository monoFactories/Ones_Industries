package mod.base.modification.repositories;

import core.management.DualRepository;
import mod.base.modification.data.blocks.Block;

public class BlockRepository extends DualRepository<Block> {
    public static final BlockRepository BLOCK_REGISTER = new BlockRepository();
    /*private final ConcurrentHashMap<String, Block> blockTable;
    private final ConcurrentHashMap<Integer, String> tagTable;
    private final BlockRepositoryRegister register = new BlockRepositoryRegister();
    private CountDownLatch registerLatch;

    public BlockRepository() {
        blockTable = new ConcurrentHashMap<>();
        tagTable = new ConcurrentHashMap<>();
        registerLatch = new CountDownLatch(0);
    }
    public void register (ModResourceReader mrr) {
        registerLatch = new CountDownLatch(1);
        register.register(mrr);
        registerLatch.countDown();
    }
    public void add(BlockElement[] blocks) {
        blocker();
        register.add(blocks);
    }
    public Block get(Integer integer) {
        blocker();
        return get(tagTable.get(integer));
    }
    public Block get(String s) {
        blocker();
        return blockTable.get(s);
    }
    public void remove(int[] ids) {
        blocker();
        String[] tags = new String[ids.length];
        for (int e = 0; e < ids.length; e++) {
            tags[e] = tagTable.get(ids[e]);
        }
        remove(tags);
    }
    public void remove(String[] tags) {
        blocker();
        for (String tag: tags) {
            if (tag != null) {
                blockTable.remove(tag);
            }
        }
    }
    public void clear() {
        blocker();
        blockTable.clear();
        tagTable.clear();
    }
    private void blocker () {
        try {
            registerLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    private class BlockRepositoryRegister {
        private final List<BlockElement> elementList;
        public BlockRepositoryRegister () {
            this.elementList = new ArrayList<>();
        }

        public void register(ModResourceReader mdr) {
            Iterator<BlockElement> blockIterator = elementList.iterator();
            while (blockIterator.hasNext()) {
                BlockElement element = blockIterator.next();
                element.block.setTexture(TextureLoader.get(mdr.getResource(element.pathToTexture), element.spriteWidth, element.spriteHeight));
                int id = tagTable.size();
                while (tagTable.containsKey(id)) {
                    id++;
                }
                tagTable.put(id, element.tag);
                blockTable.put(element.tag, element.block);
                blockIterator.remove();
            }
        }

        public void add(BlockElement e) {
            elementList.add(e);
        }

        public void add(BlockElement[] e) {
            elementList.addAll(List.of(e));
        }
    }
    public static final class BlockElement {
        private final Block block;
        private final String pathToTexture;
        private final String tag;
        private final int spriteWidth;
        private final int spriteHeight;

        public BlockElement (Block block,String tag, String pathToTexture, int spriteWidth, int spriteHeight) {
            if (block != null && pathToTexture != null && tag != null && spriteWidth >= 0 && spriteHeight >= 0) {
                this.block = block;
                this.pathToTexture = pathToTexture;
                this.spriteWidth = spriteWidth;
                this.spriteHeight = spriteHeight;
                this.tag = tag;
            } else {
                throw new IllegalArgumentException("one of parameters have not correct value");
            }
        }
    }*/
}
