package mod.base.components.worlds.level.chunks;

import core.management.Matrix;
import mod.base.modification.data.blocks.BlockInWorld;
import mod.base.modification.data.worlds.level.chunks.AbstractChunk;
import mod.base.modification.data.worlds.level.chunks.AbstractChunkEditor;
import mod.base.modification.data.worlds.level.chunks.ChunkConstant;

import java.util.ArrayList;
import java.util.List;

public class SimpleChunk implements AbstractChunk {

    private final int x, y;

    private final List <Matrix <BlockInWorld>> blocks;

    public SimpleChunk (int x, int y) {
        blocks = new ArrayList<>();
        Matrix<BlockInWorld> firstLayer = new Matrix<>(ChunkConstant.STANDARD_CHUNK_SIZE, ChunkConstant.STANDARD_CHUNK_SIZE);
        blocks.add (firstLayer);
        this.x = x;
        this.y = y;
    }
    @Override
    public BlockInWorld getBlock(int x, int y) {
        return getBlock(x, y, 0);
    }

    @Override
    public BlockInWorld getBlock(int x, int y, int layer) {
        return getMatrix(layer).get(x, y);
    }
    private Matrix<BlockInWorld> getMatrix (int layer) {
        layer = Math.clamp (layer, 0, blocks.size() - 1);
        return blocks.get(layer);
    }
    @Override
    public AbstractChunkEditor getChunkEditor() {
        return new AbstractChunkEditor() {
            @Override
            public void setBlock(BlockInWorld block, int x, int y) {
                setBlock(block, x, y, 0);
            }

            @Override
            public void setBlock(BlockInWorld block, int x, int y, int layer) {
                Matrix<BlockInWorld> blockMatrix = getMatrix(layer);
                blockMatrix.set(x, y, block);
            }
        };
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
