package com.teamabnormals.environmental.core.data.server.modifiers;

import com.teamabnormals.blueprint.common.world.modification.chunk.ChunkGeneratorModifierProvider;
import com.teamabnormals.blueprint.common.world.modification.chunk.modifiers.SurfaceRuleModifier;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

public class EnvironmentalChunkGeneratorModifierProvider extends ChunkGeneratorModifierProvider {

    public EnvironmentalChunkGeneratorModifierProvider(DataGenerator dataGenerator) {
        super(dataGenerator, Environmental.MOD_ID);
    }

    @Override
    protected void registerEntries() {
        ConditionSource isPinelands = isBiome(EnvironmentalBiomes.PINELANDS.getKey());

        RuleSource coarseDirt = state(Blocks.COARSE_DIRT.defaultBlockState());
        RuleSource dirt = state(Blocks.DIRT.defaultBlockState());
        RuleSource stone = state(Blocks.STONE.defaultBlockState());

        // RuleSource pinelandsStoneRuleSource = sequence(ifTrue(ON_CEILING, SurfaceRules.ifTrue(surfaceNoiseAbove(2.5F), stone)), ifTrue(ON_FLOOR, SurfaceRules.ifTrue(surfaceNoiseAbove(2.4F), stone)));
        // SurfaceRules.ifTrue(noiseRange(0.0F, 2.5F), coarseDirt)

        this.entry("environmental_surface_rule").selects("minecraft:overworld")
                .addModifier(new SurfaceRuleModifier(ifTrue(abovePreliminarySurface(), ifTrue(isPinelands, SurfaceRules.sequence(ifTrue(SurfaceRules.steep(), stone), SurfaceRules.ifTrue(surfaceNoiseAbove(3.0F), stone)))), false));
    }

    private static ConditionSource noiseRange(double low, double high) {
        return noiseCondition(Noises.SURFACE, low / 8.25D, high / 8.25D);
    }

    private static ConditionSource surfaceNoiseAbove(double noise) {
        return noiseCondition(Noises.SURFACE, noise / 8.25D, Double.MAX_VALUE);
    }
}
