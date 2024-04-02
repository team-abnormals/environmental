package com.teamabnormals.environmental.core.data.server.modifiers;

import com.teamabnormals.blueprint.common.world.modification.chunk.ChunkGeneratorModifierProvider;
import com.teamabnormals.blueprint.common.world.modification.chunk.modifiers.SurfaceRuleModifier;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import com.teamabnormals.environmental.core.registry.EnvironmentalNoiseParameters;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

public class EnvironmentalChunkGeneratorModifierProvider extends ChunkGeneratorModifierProvider {

    public EnvironmentalChunkGeneratorModifierProvider(DataGenerator dataGenerator) {
        super(dataGenerator, Environmental.MOD_ID);
    }

    @Override
    protected void registerEntries() {
        ConditionSource isPineBarrens = isBiome(EnvironmentalBiomes.PINE_BARRENS.getKey(), EnvironmentalBiomes.SNOWY_PINE_BARRENS.getKey(), EnvironmentalBiomes.OLD_GROWTH_PINE_BARRENS.getKey(), EnvironmentalBiomes.SNOWY_OLD_GROWTH_PINE_BARRENS.getKey());
        ConditionSource isPineSlopes = isBiome(EnvironmentalBiomes.PINE_SLOPES.getKey());

        RuleSource stone = state(Blocks.STONE.defaultBlockState());
        RuleSource nearSurfaceStone = ifTrue(not(ON_FLOOR), stone);

        this.entry("environmental_surface_rule").selects("minecraft:overworld")
                .addModifier(new SurfaceRuleModifier(ifTrue(abovePreliminarySurface(), ifTrue(isPineBarrens, SurfaceRules.sequence(ifTrue(SurfaceRules.steep(), stone), ifTrue(surfaceNoiseAbove(Noises.SURFACE, 3.0F), ifTrue(not(noiseRange(EnvironmentalNoiseParameters.PINE_BARRENS_STONE.getKey(), -1.25F, 1.25F)), stone)), ifTrue(surfaceNoiseAbove(Noises.SURFACE, 2.0F), nearSurfaceStone), ifTrue(not(stoneDepthCheck(-1, true, CaveSurface.FLOOR)), nearSurfaceStone)))), false))
                .addModifier(new SurfaceRuleModifier(ifTrue(abovePreliminarySurface(), ifTrue(isPineSlopes, stone)), false));
    }

    private static ConditionSource noiseRange(ResourceKey<NormalNoise.NoiseParameters> noise, double low, double high) {
        return noiseCondition(noise, low / 8.25D, high / 8.25D);
    }

    private static ConditionSource surfaceNoiseAbove(ResourceKey<NormalNoise.NoiseParameters> noise, double low) {
        return noiseCondition(noise, low / 8.25D, Double.MAX_VALUE);
    }
}