package mod.base.components.worlds.level.planets;

import mod.base.modification.data.blocks.terrain.TerrainBlock;

import java.util.List;

public interface AbstractPlanetNoiseParameter {
    double getHumidityStepValue ();
    double getTemperatureStepValue ();
    double getDefinedStepValue ();
    double getHumidityNoiseValue (double x, double y);
    double getTemperatureNoiseValue (double x, double y);
    double getDefinedNoiseValue (double x, double y);
    List<TerrainBlock> getTerrainBlocks ();
}
