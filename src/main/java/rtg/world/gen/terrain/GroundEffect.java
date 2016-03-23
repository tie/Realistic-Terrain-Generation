package rtg.world.gen.terrain;

import rtg.util.noise.CellNoise;
import rtg.util.noise.OpenSimplexNoise;

/**
 * @author Zeno410
 */
public class GroundEffect extends HeightEffect {
    // the standard ground effect
    private final float amplitude;

    public GroundEffect(float amplitude) {
        this.amplitude = amplitude;
    }

    public final float added(OpenSimplexNoise simplex, CellNoise cell, int x, int y) {
        return TerrainBase.groundNoise(x, y, amplitude, simplex);
    }

}
