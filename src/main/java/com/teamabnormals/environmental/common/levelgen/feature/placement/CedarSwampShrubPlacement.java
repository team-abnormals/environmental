package com.teamabnormals.environmental.common.levelgen.feature.placement;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalPlacementModifierTypes;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalNoiseParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.stream.Stream;

public class CedarSwampShrubPlacement extends PlacementModifier {
	public static final CedarSwampShrubPlacement INSTANCE = new CedarSwampShrubPlacement();
	public static final MapCodec<CedarSwampShrubPlacement> CODEC = MapCodec.unit(() -> INSTANCE);

	@Override
	public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
		int originX = pos.getX();
		int originZ = pos.getZ();
		WorldGenLevel level = context.getLevel();
		NormalNoise shadeNoise = EnvironmentalNoiseParameters.CEDAR_SWAMP_SHADE_RECEIVER.get(level.getLevel());
		Stream.Builder<BlockPos> builder = Stream.builder();
		for (int i = 0; i < 24; i++) {
			int x = originX + random.nextInt(16);
			int z = originZ + random.nextInt(16);
			double density = shadeNoise.getValue(x, 0.0D, z);
			if (density > 0.0D) density = 0.1D - density * 0.05D;
			else if (density < -0.5D) density = 0.4D - 1.2D * (0.5D + density);
			else density = 0.15D - 0.15D * density;
			if (random.nextDouble() > density) continue;
			builder.add(new BlockPos(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z), z));
		}
		return builder.build();
	}

	@Override
	public PlacementModifierType<?> type() {
		return EnvironmentalPlacementModifierTypes.CEDAR_SWAMP_SHRUB.get();
	}
}
