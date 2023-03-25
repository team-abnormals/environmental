package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class DelphiniumWisteriaTreeFeature extends WisteriaTreeFeature {

	public DelphiniumWisteriaTreeFeature(Codec<TreeConfiguration> config) {
		super(config);
	}

	@Override
	public void doPostPlace(FeaturePlaceContext<TreeConfiguration> context) {
		BlockState state = context.config().foliageProvider.getState(context.random(), context.origin());
		if (state.is(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get())) {
			state = EnvironmentalBlocks.WHITE_DELPHINIUM.get().defaultBlockState();
		} else if (state.is(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get())) {
			state = EnvironmentalBlocks.BLUE_DELPHINIUM.get().defaultBlockState();
		} else if (state.is(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get())) {
			state = EnvironmentalBlocks.PURPLE_DELPHINIUM.get().defaultBlockState();
		} else {
			state = EnvironmentalBlocks.PINK_DELPHINIUM.get().defaultBlockState();
		}

		Feature.RANDOM_PATCH.place(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(state))), context.level(), context.chunkGenerator(), context.random(), context.origin());
	}
}