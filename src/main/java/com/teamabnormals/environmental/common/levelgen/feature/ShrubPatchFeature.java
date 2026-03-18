package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.block.ShrubBlock;
import com.teamabnormals.environmental.common.levelgen.feature.configurations.ShrubPatchConfiguration;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalNoiseParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ShrubPatchFeature extends Feature<ShrubPatchConfiguration> {

	public ShrubPatchFeature(Codec<ShrubPatchConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<ShrubPatchConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos pos = context.origin();
		ShrubPatchConfiguration configuration = context.config();
		RandomSource random = context.random();
		BlockState belowState = level.getBlockState(pos.below());
		int originX = pos.getX();
		int originZ = pos.getZ();
		int successfulPlacements = 0;
		boolean canFlower = true;
		if (configuration.noiseBasedFlowering()) {
			double floweringProbabilityOverride = EnvironmentalNoiseParameters.SHRUB_FLOWER_POWER_RECEIVER.get(level.getLevel()).getValue(originX, 0.0D, originZ);
			if (floweringProbabilityOverride < 0.0D) canFlower = random.nextInt(10) == 0;
			else canFlower = random.nextDouble() < 0.1D + 1.8D * floweringProbabilityOverride;
		}
		boolean capRadius = false;
		if (isAcidicSoil(belowState)) {
			if (placeShrub(level, pos, 4, canFlower && (configuration.flowersOnSand() && random.nextInt(5) != 0) || configuration.shouldFlowerInLight(level, random, originX, originZ)))
				successfulPlacements++;
		} else {
			if ((!belowState.is(Blocks.MUD) && !belowState.is(EnvironmentalBlocks.MUDDY_PODZOL)) && (!belowState.is(BlockTags.DIRT) || random.nextBoolean()))
				return false;
			capRadius = true;
			if (placeShrub(level, pos, 4, configuration.shouldFlowerInLight(level, random, originX, originZ)))
				successfulPlacements++;
		}
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		int originY = pos.getY();
		int extraRadius = configuration.extraRadius();
		int fullRadius = capRadius ? 3 : 3 + extraRadius;
		int fullRadiusSquared = fullRadius * fullRadius;
		for (int y = -3; y <= 3; y++) {
			mutable.setY(originY + y);
			for (int x = -fullRadius; x <= fullRadius; x++) {
				mutable.setX(originX + x);
				for (int z = -fullRadius; z <= fullRadius; z++) {
					mutable.setZ(originZ + z);
					BlockState state = level.getBlockState(mutable);
					boolean flowering = false;
					if (canFlower) {
						if (isAcidicSoil(state)) {
							flowering = configuration.flowersOnSand() && random.nextInt(5) != 0;
						} else if (!state.is(BlockTags.DIRT)) continue;
						flowering |= configuration.shouldFlowerInLight(level, random, mutable.getX(), mutable.getZ());
					} else if (!isAcidicSoil(state) && !state.is(BlockTags.DIRT))
						continue;
					int horizontalDistanceSquared = x * x + z * z;
					if (horizontalDistanceSquared > fullRadiusSquared) continue;
					int size;
					if (horizontalDistanceSquared <= 1) {
						size = random.nextInt(4) == 0 ? 4 : 3;
					} else if (horizontalDistanceSquared == 2) {
						size = random.nextInt(6) == 0 ? 1 : random.nextInt(4) == 0 ? 3 : 2;
					} else if (horizontalDistanceSquared <= 9) {
						if (random.nextInt(6) == 0) continue;
						size = random.nextInt(3) == 0 ? 2 : 1;
					} else if (random.nextBoolean()) {
						continue;
					} else size = 1;
					int tempY = mutable.getY();
					if (placeShrub(level, mutable.setY(tempY + 1), size, flowering))
						successfulPlacements++;
					mutable.setY(tempY);
				}
			}
		}
		return successfulPlacements > 0;
	}

	private static boolean isAcidicSoil(BlockState state) {
		return state.is(BlockTags.SAND) || state.is(EnvironmentalBlocks.MUDDY_SAND);
	}

	private static boolean isShrub(BlockState state) {
		return state.is(EnvironmentalBlocks.FLOWERING_SHRUB) || state.is(EnvironmentalBlocks.SHRUB);
	}

	private static boolean placeShrub(WorldGenLevel level, BlockPos pos, int size, boolean flowering) {
		BlockState state = level.getBlockState(pos);
		if (state.is(EnvironmentalBlocks.FLOWERING_SHRUB)) {
			flowering = true;
		} else if (!state.isAir() && !state.is(EnvironmentalBlocks.SHRUB)) return false;
		BlockPos above = pos.above();
		BlockState aboveState = level.getBlockState(above);
		if (isShrub(aboveState)) {
			if (aboveState.getValue(ShrubBlock.SIZE) > size) return false;
		} else if (!aboveState.isAir()) {
			size = 1;
		}
		BlockState shrubState = (flowering ? EnvironmentalBlocks.FLOWERING_SHRUB.get() : EnvironmentalBlocks.SHRUB.get()).defaultBlockState().setValue(ShrubBlock.SIZE, size);
		if (size > 3) {
			BlockPos highPos = pos.above(2);
			BlockState highTopState = level.getBlockState(highPos);
			if (isShrub(highTopState)) return false;
			if (highTopState.isAir()) {
				if (ShrubBlock.isWalledIn(level, above) || ShrubBlock.isWalledIn(level, highPos))
					shrubState = shrubState.setValue(ShrubBlock.CENTERED, true);
				level.setBlock(pos, shrubState.setValue(ShrubBlock.PART, ShrubBlock.Part.BOTTOM), 2);
				level.setBlock(above, shrubState.setValue(ShrubBlock.PART, ShrubBlock.Part.MIDDLE), 2);
				level.setBlock(highPos, shrubState.setValue(ShrubBlock.PART, ShrubBlock.Part.TOP), 2);
				return true;
			}
			size = 2;
		}
		level.setBlock(pos, shrubState.setValue(ShrubBlock.PART, ShrubBlock.Part.BOTTOM), 2);
		if (size > 1)
			level.setBlock(above, shrubState.setValue(ShrubBlock.PART, ShrubBlock.Part.TOP), 2);
		return true;
	}

}
