package teamrtg.rtg.mods.vanilla.biomes;

import net.minecraft.block.BlockDirt;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import teamrtg.rtg.world.biome.surface.part.CliffSelector;
import teamrtg.rtg.world.biome.surface.part.SurfacePart;
import teamrtg.rtg.world.biome.terrain.TerrainBase;
import teamrtg.rtg.world.gen.ChunkProviderRTG;
import teamrtg.rtg.world.gen.deco.*;
import teamrtg.rtg.world.gen.deco.DecoFallenTree.LogCondition;
import teamrtg.rtg.world.gen.deco.DecoTree.TreeCondition;
import teamrtg.rtg.world.gen.deco.DecoTree.TreeType;
import teamrtg.rtg.world.gen.deco.helper.DecoHelper5050;

public class RealisticBiomeVanillaForest extends RealisticBiomeVanillaBase {

    public RealisticBiomeVanillaForest() {

        super(
                Biomes.FOREST,
            Biomes.RIVER
        );
    }

    @Override
    protected TerrainBase initTerrain() {
        return new TerrainBase() {
            @Override
            public float generateNoise(ChunkProviderRTG provider, int x, int y, float border, float river) {
                groundNoise = groundNoise(x, y, groundVariation, provider.simplex);
                float m = hills(x, y, 10f, provider.simplex, river);
                float floNoise = 65f + groundNoise + m;

                return riverized(floNoise, river);
            }
        };
    }

    @Override
    protected SurfacePart initSurface() {
        SurfacePart surface = new SurfacePart();
        surface.add(new CliffSelector(1.5f)
            .add(PARTS.selectTopAndFill()
                .add(this.PARTS.SHADOW_STONE)));
        surface.add(new CliffSelector((x, y, z, provider) -> 1.5f - ((y - 60f) / 65f) + provider.simplex.noise3(x / 8f, y / 8f, z / 8f) * 0.5f)
            .add(PARTS.selectTop()
                .add(PARTS.STONE_OR_COBBLE)))
            .add(PARTS.selectFill()
                .add(PARTS.STONE));
        surface.add(PARTS.surfaceMix(PARTS.MIX_NOISE));
        surface.add(PARTS.surfaceGeneric());
        return surface;
    }

    @Override
    protected void initDecos() {
        // Trees first.
        DecoTree bigPines = new DecoTree();
        bigPines.strengthNoiseFactorForLoops = true;
        bigPines.treeType = TreeType.BIG_PINES;
        bigPines.distribution.noiseDivisor = 80f;
        bigPines.distribution.noiseFactor = 60f;
        bigPines.distribution.noiseAddend = -15f;
        bigPines.treeCondition = TreeCondition.ALWAYS_GENERATE;
        bigPines.maxY = 140;
        this.addDeco(bigPines);

        // More trees.
        DecoTree smallPinesTreesForest = new DecoTree();
        smallPinesTreesForest.strengthFactorForLoops = 3f;
        smallPinesTreesForest.treeType = TreeType.SMALL_PINES_TREES_FORESTS;
        smallPinesTreesForest.treeCondition = TreeCondition.ALWAYS_GENERATE;
        smallPinesTreesForest.maxY = 120;
        this.addDeco(smallPinesTreesForest);

        // Add some fallen trees of the oak and spruce variety (50/50 distribution).
        DecoFallenTree decoFallenOak = new DecoFallenTree();
        decoFallenOak.logCondition = LogCondition.RANDOM_CHANCE;
        decoFallenOak.logConditionChance = 8;
        decoFallenOak.maxY = 100;
        decoFallenOak.logBlock = Blocks.LOG;
        decoFallenOak.logMeta = (byte) 0;
        decoFallenOak.leavesBlock = Blocks.LEAVES;
        decoFallenOak.leavesMeta = (byte) -1;
        decoFallenOak.minSize = 3;
        decoFallenOak.maxSize = 6;

        DecoFallenTree decoFallenSpruce = new DecoFallenTree();
        decoFallenSpruce.logCondition = LogCondition.RANDOM_CHANCE;
        decoFallenSpruce.logConditionChance = 8;
        decoFallenSpruce.maxY = 100;
        decoFallenSpruce.logBlock = Blocks.LOG;
        decoFallenSpruce.logMeta = (byte) 1;
        decoFallenSpruce.leavesBlock = Blocks.LEAVES;
        decoFallenSpruce.leavesMeta = (byte) -1;
        decoFallenSpruce.minSize = 3;
        decoFallenSpruce.maxSize = 6;

        DecoHelper5050 decoFallenTree = new DecoHelper5050(decoFallenOak, decoFallenSpruce);
        this.addDeco(decoFallenTree);

        // Shrubs to fill in the blanks.
        DecoShrub decoShrub = new DecoShrub();
        decoShrub.maxY = 110;
        decoShrub.strengthFactor = 3f;
        this.addDeco(decoShrub);

        // Only 1-block tall flowers so we can see the trees better.
        DecoFlowersRTG decoFlowersRTG = new DecoFlowersRTG();
        decoFlowersRTG.flowers = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        decoFlowersRTG.maxY = 128;
        decoFlowersRTG.strengthFactor = 8f;
        this.addDeco(decoFlowersRTG);

        // Grass filler.
        DecoGrass decoGrass = new DecoGrass();
        decoGrass.maxY = 128;
        decoGrass.strengthFactor = 12f;
        this.addDeco(decoGrass);
    }

    @Override
    protected void initProperties() {
        config.addBlock(config.MIX_BLOCK_TOP).setDefault(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL));
        config.addBlock(config.MIX_BLOCK_FILL).setDefault(Blocks.DIRT.getDefaultState());
        config.addBlock(config.BEACH_BLOCK).setDefault(Blocks.SAND.getDefaultState());
    }
}
