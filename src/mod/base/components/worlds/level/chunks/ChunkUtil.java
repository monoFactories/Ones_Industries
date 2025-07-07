package mod.base.components.worlds.level.chunks;

import mod.base.components.worlds.level.generation.ChunkGenerator;
import mod.base.modification.data.worlds.level.chunks.AbstractChunk;
import mod.base.modification.data.worlds.level.generation.AbstractChunkGenerator;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class ChunkUtil {
    private static BiFunction<Integer, Integer, AbstractChunk> currentRawChunkCreator = getStandardRawCreator();
    public static void setCurrentRawChunkCreator(BiFunction <Integer, Integer, AbstractChunk> currentRawChunkCreator1) {
        currentRawChunkCreator = currentRawChunkCreator1;
    }
    public static BiFunction<Integer, Integer, AbstractChunk> getCurrentRawChunkCreator() {
        return currentRawChunkCreator;
    }
    public static BiFunction<Integer, Integer, AbstractChunk> getStandardRawCreator () {
        return SimpleChunk::new;
    }

    private static Supplier <AbstractChunkGenerator> currentGroundChunkCreator = getStandardGroundCreator();
    public static void setCurrentGroundChunkCreator(Supplier<AbstractChunkGenerator> currentGroundChunkCreator1) {
        currentGroundChunkCreator = currentGroundChunkCreator1;
    }
    public static Supplier<AbstractChunkGenerator> getCurrentGroundChunkCreator() {
        return currentGroundChunkCreator;
    }
    public static Supplier <AbstractChunkGenerator> getStandardGroundCreator () {
        return () -> ChunkGenerator.STANDARD_GENERATOR;
    }

}
