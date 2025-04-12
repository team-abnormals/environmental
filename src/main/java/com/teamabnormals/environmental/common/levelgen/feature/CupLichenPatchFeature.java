package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.block.CupLichenBlock;
import com.teamabnormals.environmental.common.levelgen.feature.configurations.CupLichenPatchConfiguration;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class CupLichenPatchFeature extends Feature<CupLichenPatchConfiguration> {

	public CupLichenPatchFeature(Codec<CupLichenPatchConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<CupLichenPatchConfiguration> context) {
		CupLichenPatchConfiguration config = context.config();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		BlockPos origin = context.origin();

		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		boolean placed = false;
		int xzspread = config.xzSpread() + 1;
		int yspread = config.ySpread() + 1;

		for(int i = 0; i < config.tries(); ++i) {
			mutable.setWithOffset(origin, random.nextInt(xzspread) - random.nextInt(xzspread), random.nextInt(yspread) - random.nextInt(yspread), random.nextInt(xzspread) - random.nextInt(xzspread));
			BlockState offsetstate = level.getBlockState(mutable);
			if (EnvironmentalBlocks.CUP_LICHEN.get().defaultBlockState().canSurvive(level, mutable)) {
				if (level.isEmptyBlock(mutable)) {
					level.setBlock(mutable, EnvironmentalBlocks.CUP_LICHEN.get().defaultBlockState().setValue(CupLichenBlock.CUPS, 1), 2);
					placed = true;
				} else if (offsetstate.is(EnvironmentalBlocks.CUP_LICHEN.get())) {
					int cups = offsetstate.getValue(CupLichenBlock.CUPS);
					if (cups < 4) {
						level.setBlock(mutable, offsetstate.setValue(CupLichenBlock.CUPS, cups + 1), 2);
						placed = true;
					}
				}
			}
		}

		return placed;
	}
}