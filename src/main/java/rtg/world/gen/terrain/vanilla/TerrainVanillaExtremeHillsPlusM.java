package rtg.world.gen.terrain.vanilla;

import rtg.util.noise.CellNoise;
import rtg.util.noise.OpenSimplexNoise;
import rtg.world.gen.terrain.TerrainBase;

public class TerrainVanillaExtremeHillsPlusM extends TerrainBase {

    private float width;
    private float strength;
    private float lakeDepth;
    private float lakeWidth;
    private float terrainHeight;

    /*
     * width = 230f strength = 120f lake = 50f;
     *
     * 230f, 120f, 50f
     */

    public TerrainVanillaExtremeHillsPlusM(float mountainWidth, float mountainStrength, float depthLake) {

        this(mountainWidth, mountainStrength, depthLake, 260f, 68f);
    }

    public TerrainVanillaExtremeHillsPlusM(float mountainWidth, float mountainStrength, float depthLake, float widthLake, float height) {

        width = mountainWidth;
        strength = mountainStrength;
        lakeDepth = depthLake;
        lakeWidth = widthLake;
        terrainHeight = height;
    }

    @Override
    public float generateNoise(OpenSimplexNoise simplex, CellNoise cell, int x, int y, float border, float river) {
        return terrainLonelyMountain(x, y, simplex, cell, river, strength, width, terrainHeight);
    }
}
