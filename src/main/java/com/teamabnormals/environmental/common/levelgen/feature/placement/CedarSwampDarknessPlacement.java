package com.teamabnormals.environmental.common.levelgen.feature.placement;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalPlacementModifierTypes;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalNoiseParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.stream.Stream;

public final class CedarSwampDarknessPlacement extends PlacementModifier {
	public static final CedarSwampDarknessPlacement INSTANCE = new CedarSwampDarknessPlacement();
	public static final MapCodec<CedarSwampDarknessPlacement> CODEC = MapCodec.unit(() -> INSTANCE);

	@Override
	public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
		NormalNoise densityNoise = EnvironmentalNoiseParameters.CEDAR_SWAMP_SHADE_RECEIVER.get(context.getLevel().getLevel());
		int originX = pos.getX();
		int originZ = pos.getZ();
		double density = densityNoise.getValue(0.25D * (originX + 8.0D), 0.0D, 0.25D * (originZ + 8.0D));
		if (density < 0.0F) return Stream.empty();
		Stream.Builder<BlockPos> builder = Stream.builder();
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (random.nextDouble() > density) continue;
				int x = originX + i;
				int z = originZ + j;
				int oceanFloorHeight = context.getHeight(Heightmap.Types.OCEAN_FLOOR, x, z);
				if (oceanFloorHeight <= context.getMinBuildHeight()) continue;
				int worldSurfaceHeight = context.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
				if (worldSurfaceHeight - oceanFloorHeight > 0) continue;
				int heightAbove = context.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
				if (heightAbove != oceanFloorHeight) continue;
				// If we found a position where it likely has light coming in
				builder.add(new BlockPos(x, oceanFloorHeight, z));
			}
		}
		return builder.build();
	}

	@Override
	public PlacementModifierType<?> type() {
		return EnvironmentalPlacementModifierTypes.CEDAR_SWAMP_DARKNESS.get();
	}
}
