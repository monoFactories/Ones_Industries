package mod.base.modification.data.worlds.level.planets;

import mod.base.modification.data.blocks.terrain.TerrainBlock;

import mod.base.modification.repositories.BlockRepository;
import mono.factories.noises.CoordinateNoiseGenerator;
import mono.factories.noises.perlin.Perlin;

import java.util.ArrayList;
import java.util.List;

public interface AbstractPlanetNoiseParameter {
    double getHumidityStepValue ();
    double getTemperatureStepValue ();
    double getDefinedStepValue ();
    double getHumidityNoiseValue (double x, double y);
    double getTemperatureNoiseValue (double x, double y);
    double getDefinedNoiseValue (double x, double y);
    List <TerrainBlock> getTerrainBlocks ();
    AbstractPlanet getPlanet ();

    double BLOCKS_TO_ONE_HUMIDITY = 64.0, BLOCKS_TO_ONE_TEMPERATURE = 32.0, BLOCKS_TO_ONE_DEFINED = 16.0;
    static AbstractPlanetNoiseParameter getInstance (long seed, AbstractPlanet planet) {
        CoordinateNoiseGenerator n0 = Perlin.getStandardPerlin(seed, 2), n1 = Perlin.getStandardPerlin(seed ^ 0x7F34B8AE10A36B4DL, 2), n2 = Perlin.getStandardPerlin(seed ^ 0x49F3AB03EA91773CL, 2);
        return new AbstractPlanetNoiseParameter() {
            @Override
            public double getHumidityStepValue() {
                return 1.0 / BLOCKS_TO_ONE_HUMIDITY;
            }

            @Override
            public double getTemperatureStepValue() {
                return 1 / BLOCKS_TO_ONE_TEMPERATURE;
            }

            @Override
            public double getDefinedStepValue() {
                return 1 / BLOCKS_TO_ONE_DEFINED;
            }

            @Override
            public double getHumidityNoiseValue(double x, double y) {
                return n0.get(x, y) + 0.5;
            }

            @Override
            public double getTemperatureNoiseValue(double x, double y) {
                return n1.get (x, y) + 0.5;
            }

            @Override
            public double getDefinedNoiseValue(double x, double y) {
                return n2.get(x, y) + 0.5;
            }

            @Override
            public List<TerrainBlock> getTerrainBlocks() {
                List<TerrainBlock> terrains = new ArrayList<>();
                BlockRepository.BLOCK_REGISTER.forEach((m, sbr) -> sbr.forEach ((s, block) -> {
                    //System.out.println("check block: {" + block.getID() + "}");
                    if (block instanceof TerrainBlock terrain) {
                        //System.out.println("add terrain: {" + block.getID() + "}");
                        terrains.add(terrain);
                    }
                }));
                return terrains;
            }

            @Override
            public AbstractPlanet getPlanet() {
                return planet;
            }
        };
    }
}
