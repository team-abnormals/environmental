package com.teamabnormals.environmental.common.levelgen.feature.placement;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalPlacementModifierTypes;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalNoiseParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.stream.Stream;

public final class CedarSwampTreePlacement extends PlacementModifier {
	public static final CedarSwampTreePlacement INSTANCE = new CedarSwampTreePlacement();
	public static final MapCodec<CedarSwampTreePlacement> CODEC = MapCodec.unit(() -> INSTANCE);

	@Override
	public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
		int area = 16 * 16;
		int[] available = new int[area];
		int[] positions = new int[area];
		for (int i = 0; i < area; i++) {
			available[i] = i;
			positions[i] = i;
		}
		int originX = pos.getX();
		int originZ = pos.getZ();
		NormalNoise densityNoise = EnvironmentalNoiseParameters.CEDAR_SWAMP_SHADE_RECEIVER.get(context.getLevel().getLevel());
		int remaining = 20;
		Stream.Builder<BlockPos> builder = Stream.builder();
		while (remaining-- > 0 && area > 0) {
			int index = available[random.nextInt(area)];
			int x = index & 15;
			int z = index >> 4;
			int placeX = originX + x;
			int placeZ = originZ + z;
			double density = densityNoise.getValue(placeX * 0.25D, 0.0D, placeZ * 0.25D);
			if (density < 0.0D && random.nextDouble() > 0.2D + (1.0D + density) * 0.8D) continue;
			builder.add(new BlockPos(placeX, 0, placeZ));
			if (removeIndex(available, positions, area, index))
				area--;
			for (int dz = -1; dz <= 1; dz++) {
				int zz = z + dz;
				if (zz < 0 || zz > 15) continue;
				int zzShifted = zz << 4;
				for (int dx = -1;  dx <= 1; dx++) {
					int xx = x + dx;
					if (xx >= 0 && xx <= 15 && removeIndex(available, positions, area, zzShifted | xx))
						area--;
				}
			}
		}
		return builder.build();
	}

	@Override
	public PlacementModifierType<?> type() {
		return EnvironmentalPlacementModifierTypes.CEDAR_SWAMP_TREE.get();
	}

	private static boolean removeIndex(int[] available, int[] positions, int area, int index) {
		int pos = positions[index];
		if (pos < 0 || pos >= area) return false;
		int lastIndex = available[area - 1];
		available[pos] = lastIndex;
		positions[lastIndex] = pos;
		positions[index] = -1;
		return true;
	}
}
