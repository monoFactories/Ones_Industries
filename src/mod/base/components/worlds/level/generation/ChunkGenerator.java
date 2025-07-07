package mod.base.components.worlds.level.generation;

import mod.base.modification.data.worlds.level.chunks.AbstractChunk;
import mod.base.modification.data.worlds.level.chunks.ChunkConstant;
import mod.base.modification.data.worlds.level.generation.AbstractChunkGenerator;
import mod.base.modification.data.worlds.level.planets.AbstractPlanetNoiseParameter;
import mod.base.modification.data.blocks.terrain.TerrainBlock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class ChunkGenerator implements AbstractChunkGenerator {
    public static final ChunkGenerator STANDARD_GENERATOR = new ChunkGenerator ();
    private static void fillGrChunk(AbstractChunk chunk, AbstractPlanetNoiseParameter noise) {
        ApplicantFinder finder = new ApplicantFinder();
        List<TerrainBlock> terrainsBlocks = noise.getTerrainBlocks();
        //System.out.println("terrain blocks for planet: {" + noise.getPlanet().getID() + "}, is: " + terrainsBlocks);
        terrainsBlocks.forEach(finder::add);
        for (int y = 0; y < ChunkConstant.STANDARD_CHUNK_SIZE; y++) {
            int globalY = ChunkConstant.getGlobalCoordinate(chunk.getY(), y);
            for (int x = 0; x < ChunkConstant.STANDARD_CHUNK_SIZE; x++) {
                int globalX = ChunkConstant.getGlobalCoordinate(chunk.getX(), x);
                double temperatureStep = noise.getTemperatureStepValue();
                double temperature = noise.getTemperatureNoiseValue(globalX * temperatureStep, globalY * temperatureStep);
                double humidityStep = noise.getHumidityStepValue();
                double humidity = noise.getHumidityNoiseValue(humidityStep * globalX, globalY * humidityStep);
                humidity = Math.clamp(humidity, 0, 0.999999999);
                temperature = Math.clamp(temperature, 0, 0.999999999);
                List<TerrainBlock> applicants = finder.getApplicants(humidity, temperature);
                if (!applicants.isEmpty()) {
                    TerrainBlock winner;
                    if (applicants.size() == 1)
                        winner = applicants.getFirst();
                    else {
                        double definedStep = noise.getDefinedStepValue();
                        double defined = noise.getDefinedNoiseValue(globalX * definedStep, globalY * definedStep);
                        defined = Math.clamp(defined, 0, 0.999999999);
                        winner = applicants.get(((int) (defined * applicants.size())) % applicants.size());
                    }
                    chunk.getChunkEditor().setBlock(winner.getBlock(), x, y);
                }
            }
        }
    }

    @Override
    public void fillGroundLayer(AbstractChunk chunk, AbstractPlanetNoiseParameter noise) {
        fillGrChunk (chunk, noise);
    }

    private record TerrainEntry(TerrainBlock block, double minHumidity, double maxHumidity, double minTemperature, double maxTemperature) {
        TerrainEntry {
            if (block == null) throw new IllegalArgumentException("terrain block couldn't be null");
        }
    }
    private static class ApplicantFinder {
        final Set<TerrainEntry> terrainList = new HashSet<>();
        public void add (TerrainBlock block) {
            terrainList.add(blockToEntry(block));
        }
        static TerrainEntry blockToEntry (TerrainBlock block) {
            double minH = block.getTerrainParameter().minHumidity();
            double maxH = block.getTerrainParameter().maxHumidity();
            double minT = block.getTerrainParameter().minTemperature();
            double maxT = block.getTerrainParameter().maxTemperature();
            if (minH > maxH) {
                double maxCopy = maxH;
                maxH = minH;
                minH = maxCopy;
            }
            if (minT > maxT) {
                double maxCopy = maxT;
                maxT = minT;
                minT = maxCopy;
            }
            minH = standardClamp(minH);
            maxH = standardClamp(maxH);
            minT = standardClamp(minT);
            maxT = standardClamp(maxT);
            return new TerrainEntry(block, minH, maxH, minT, maxT);
        }
        public static double standardClamp (double v) {
            return Math.clamp(v, 0.0, 1.0);
        }
        List<TerrainBlock> getApplicants (double humidity, double temperature) {
            final double[] minDistance = {Double.MAX_VALUE};
            AtomicReference<TerrainEntry> nearEntry = new AtomicReference<>();
            List<TerrainBlock> completed = new ArrayList<>();
            terrainList.forEach(entry -> {
                double minHumidity = entry.minHumidity();
                double maxHumidity = entry.maxHumidity();
                double minTemperature = entry.minTemperature();
                double maxTemperature = entry.maxTemperature();
                boolean insideHumidity = humidity >= minHumidity && humidity <= maxHumidity;
                boolean insideTemperature = temperature >= minTemperature && temperature <= maxTemperature;
                double distance;
                if (insideHumidity && insideTemperature) {
                    minDistance[0] = -1;
                    completed.add(entry.block);
                }
                else {
                    double dx = 0;
                    if (humidity < minHumidity) {
                        dx = minHumidity - humidity;
                    } else if (humidity > maxHumidity) {
                        dx = humidity - maxHumidity;
                    }

                    double dy = 0;
                    if (temperature < minTemperature) {
                        dy = minTemperature - temperature;
                    } else if (temperature > maxTemperature) {
                        dy = temperature - maxTemperature;
                    }
                    distance = Math.sqrt(dx * dx + dy * dy);
                    if (distance < minDistance[0]) {
                        minDistance[0] = distance;
                        nearEntry.set(entry);
                    }
                }
            });
            if (minDistance[0] > -1 && nearEntry.get() != null) {
                completed.add(nearEntry.get().block);
            }
            return completed;
        }
    }
}
