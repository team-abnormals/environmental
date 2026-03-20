package com.teamabnormals.environmental.common.levelgen.feature.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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

import static java.util.stream.Stream.empty;

public class CedarSwampMushroomPlacement extends PlacementModifier {
	public static final MapCodec<CedarSwampMushroomPlacement> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
		return instance.group(
				Codec.BOOL.fieldOf("red").forGetter(placement -> placement.red),
				Codec.BOOL.fieldOf("compact").forGetter(placement -> placement.compact)
		).apply(instance, CedarSwampMushroomPlacement::new);
	});
	private final boolean red;
	private final boolean compact;

	public CedarSwampMushroomPlacement(boolean red, boolean compact) {
		this.red = red;
		this.compact = compact;
	}

	@Override
	public Stream<BlockPos> getPositions(PlacementContext context, RandomSource random, BlockPos pos) {
		WorldGenLevel level = context.getLevel();
		NormalNoise shadeNoise = EnvironmentalNoiseParameters.CEDAR_SWAMP_SHADE_RECEIVER.get(level.getLevel());
		NormalNoise mushroomsNoise = EnvironmentalNoiseParameters.CEDAR_SWAMP_MUSHROOM_DENSITY_RECEIVER.get(level.getLevel());
		int originX = pos.getX();
		int originZ = pos.getZ();
		double shade = shadeNoise.getValue(0.25D * (originX + 8.0D), 0.0D, 0.25D * (originZ + 8.0D));
		double mushrooms = mushroomsNoise.getValue((originX + 8.0D), 0.0D, (originZ + 8.0D));
		int count;
		if (this.compact) {
			if (shade < 0.0D) {
				if (mushrooms < 0.5D) return empty();
				count = 1;
			} else if (shade < 0.5D) {
				if (mushrooms < 0.0D) return empty();
				if (mushrooms < 0.5D && random.nextInt(16) != 0) return empty();
				count = (int) (1 + 4 * mushrooms * mushrooms);
			} else if (mushrooms < 0.0D) return empty();
			else count = mushrooms < 0.5D ? 2 : 6;
		} else {
			if (shade < 0.0D) {
				if (mushrooms < 0.0D) {
					if (random.nextInt(10) != 0) return empty();
				} else if (mushrooms < 0.5D) {
					if (random.nextInt(7) != 0) return empty();
				} else if (random.nextBoolean()) return empty();
				count = 1;
			} else if (shade < 0.5D) {
				if (mushrooms < 0.0D) {
					if (random.nextInt(6) != 0) return empty();
				} else if (mushrooms < 0.5D) {
					if (random.nextInt(4) != 0) return empty();
				} else if (random.nextBoolean()) return empty();
				count = 1;
			} else {
				if (mushrooms < 0.0D && random.nextBoolean()) return empty();
				count = mushrooms < 0.5D ? 1 : 2;
			}
		}
		if (!this.red && this.compact) count *= 3;
		Stream.Builder<BlockPos> builder = Stream.builder();
		for (int i = 0; i < count; i++) {
			int x = originX + random.nextInt(16);
			int z = originZ + random.nextInt(16);
			builder.add(new BlockPos(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z), z));
		}
		return builder.build();
	}

	@Override
	public PlacementModifierType<?> type() {
		return EnvironmentalPlacementModifierTypes.CEDAR_SWAMP_MUSHROOM.get();
	}
}
