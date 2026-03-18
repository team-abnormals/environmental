package com.teamabnormals.environmental.common.levelgen.feature.placement;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalPlacementModifierTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

import java.util.stream.Stream;

public final class CedarSwampShrubRiverPlacement extends PlacementModifier {
	public static final CedarSwampShrubRiverPlacement INSTANCE = new CedarSwampShrubRiverPlacement();
	public static final MapCodec<CedarSwampShrubRiverPlacement> CODEC = MapCodec.unit(() -> INSTANCE);

	@Override
	public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
		int originX = pos.getX();
		int originZ = pos.getZ();
		WorldGenLevel level = context.getLevel();
		DensityFunction weirdnessFunction = level.getLevel().getChunkSource().randomState().sampler().weirdness();
		Stream.Builder<BlockPos> builder = Stream.builder();
		for (int i = 0; i < 20; i++) {
			int x = originX + random.nextInt(16);
			int z = originZ + random.nextInt(16);
			int height = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
			if (height > 65) continue;
			double density = Math.abs(weirdnessFunction.compute(new DensityFunction.SinglePointContext(x, height, z)));
			if (density > 0.1D) continue;
			if (density < 0.05D) density = 0.6D;
			else density = 0.3D + 10.0D * (0.1D - density);
			if (random.nextDouble() > density) continue;
			builder.add(new BlockPos(x, height, z));
		}
		return builder.build();
	}

	@Override
	public PlacementModifierType<?> type() {
		return EnvironmentalPlacementModifierTypes.CEDAR_SWAMP_SHRUB_RIVER.get();
	}
}
