package com.teamabnormals.environmental.core.data.server.modifiers;

import com.teamabnormals.blueprint.common.world.modification.chunk.ChunkGeneratorModifierProvider;
import com.teamabnormals.blueprint.common.world.modification.chunk.modifiers.SurfaceRuleModifier;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalBiomes;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalNoiseParameters;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

public class EnvironmentalChunkGeneratorModifierProvider extends ChunkGeneratorModifierProvider {

	public EnvironmentalChunkGeneratorModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(Environmental.MOD_ID, output, provider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		ConditionSource isPineBarrens = isBiome(EnvironmentalBiomes.PINE_BARRENS, EnvironmentalBiomes.SNOWY_PINE_BARRENS, EnvironmentalBiomes.OLD_GROWTH_PINE_BARRENS, EnvironmentalBiomes.SNOWY_OLD_GROWTH_PINE_BARRENS);
		ConditionSource isPineSlopes = isBiome(EnvironmentalBiomes.PINE_SLOPES);
		ConditionSource isCedarCreek = isBiome(EnvironmentalBiomes.CEDAR_RIVER, EnvironmentalBiomes.CEDAR_BANK);

		RuleSource stone = state(Blocks.STONE.defaultBlockState());
		RuleSource nearSurfaceStone = ifTrue(not(ON_FLOOR), stone);
		RuleSource sand = state(Blocks.SAND.defaultBlockState());
		RuleSource sandstone = state(Blocks.SANDSTONE.defaultBlockState());
		RuleSource muddySand = state(EnvironmentalBlocks.MUDDY_SAND.get().defaultBlockState());
		RuleSource mud = state(Blocks.MUD.defaultBlockState());
		RuleSource sandy = sequence(ifTrue(ON_CEILING, sandstone), sand);
		RuleSource sandyToMud = sequence(
				ifTrue(
						yBlockCheck(VerticalAnchor.aboveBottom(64 + 62), 0),
						sandy
				),
				ifTrue(
						yBlockCheck(VerticalAnchor.aboveBottom(64 + 58), 0),
						sequence(ifTrue(noiseCondition(EnvironmentalNoiseParameters.CEDAR_RIVER_MUD, 0.0F), muddySand), sandy)
				),
				ifTrue(
						yBlockCheck(VerticalAnchor.aboveBottom(64 + 55), 0),
						sequence(ifTrue(noiseCondition(EnvironmentalNoiseParameters.CEDAR_RIVER_MUD, 0.0F), mud), muddySand)
				),
				mud
		);
		ConditionSource barelyOutOfWater = waterBlockCheck(-1, 0);
		ConditionSource roughlyShallow = waterStartCheck(-6, -1);

		this.entry("environmental_surface_rule").selects("minecraft:overworld")
				.addModifier(new SurfaceRuleModifier(ifTrue(abovePreliminarySurface(), sequence(
						ifTrue(isPineBarrens, sequence(ifTrue(steep(), stone), ifTrue(surfaceNoiseAbove(Noises.SURFACE, 3.0F), ifTrue(not(noiseRange(EnvironmentalNoiseParameters.PINE_BARRENS_STONE, -1.25F, 1.25F)), stone)), ifTrue(surfaceNoiseAbove(Noises.SURFACE, 2.0F), nearSurfaceStone), ifTrue(not(stoneDepthCheck(-1, true, CaveSurface.FLOOR)), nearSurfaceStone))),
						ifTrue(isPineSlopes, stone),
						ifTrue(isCedarCreek, sequence(ifTrue(ON_FLOOR, ifTrue(barelyOutOfWater, sandyToMud)), ifTrue(roughlyShallow, ifTrue(UNDER_FLOOR, sandyToMud))))
				)), false));
	}

	private static ConditionSource noiseRange(ResourceKey<NormalNoise.NoiseParameters> noise, double low, double high) {
		return noiseCondition(noise, low / 8.25D, high / 8.25D);
	}

	private static ConditionSource surfaceNoiseAbove(ResourceKey<NormalNoise.NoiseParameters> noise, double low) {
		return noiseCondition(noise, low / 8.25D, Double.MAX_VALUE);
	}
}