package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MuddyEdgesFeature extends Feature<NoneFeatureConfiguration> {

	public MuddyEdgesFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		int originX = origin.getX();
		int originZ = origin.getZ();
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		RandomSource random = context.random();
		for (int i = 0; i < 16; i++) {
			pos.setX(originX + i);
			for (int j = 0; j < 16; j++) {
				pos.setZ(originZ + j);
				int groundY = level.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX(), pos.getZ()) - 1;
				pos.setY(groundY);
				BlockState state = level.getBlockState(pos);
				if (!state.is(EnvironmentalBlocks.MUDDY_PODZOL.get()) || random.nextInt(3) == 0) continue;
				for (Direction waterAdjacentDirection : Direction.Plane.HORIZONTAL) {
					if (!level.getBlockState(pos.relative(waterAdjacentDirection)).getFluidState().is(FluidTags.WATER))
						continue;
					level.setBlock(pos, Blocks.MUD.defaultBlockState(), 2);
					if (random.nextBoolean()) {
						for (Direction adjacent : Direction.Plane.HORIZONTAL) {
							BlockPos relative = pos.relative(adjacent);
							if (adjacent != waterAdjacentDirection && level.getBlockState(relative).is(EnvironmentalBlocks.MUDDY_PODZOL.get()))
								level.setBlock(relative, Blocks.MUD.defaultBlockState(), 2);
						}
					}
					break;
				}
			}
		}
		return true;
	}

}
