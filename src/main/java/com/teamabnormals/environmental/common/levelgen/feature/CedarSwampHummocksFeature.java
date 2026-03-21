package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalFeatures;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalNoiseParameters;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class CedarSwampHummocksFeature extends Feature<NoneFeatureConfiguration> {

	public CedarSwampHummocksFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		BlockPos origin = context.origin();
		int originX = origin.getX();
		int originZ = origin.getZ();
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
		RandomSource random = context.random();
		NormalNoise hummocksNoise = EnvironmentalNoiseParameters.CEDAR_SWAMP_HUMMOCKS_RECEIVER.get(level.getLevel());
		DensityFunction weirdnessFunction = level.getLevel().getChunkSource().randomState().sampler().weirdness();
		for (int i = 0; i < 16; i++) {
			pos.setX(originX + i);
			for (int j = 0; j < 16; j++) {
				pos.setZ(originZ + j);
				int groundY = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, pos.getX(), pos.getZ());
				if (groundY != 62) continue;
				if (!level.getBlockState(pos.setY(groundY - 1)).is(Blocks.MUD)
					|| !level.getBlockState(pos.setY(groundY + 1)).isAir()
					|| !level.getBlockState(pos.setY(groundY)).is(Blocks.WATER)
				) continue;
				double hummocks = Math.abs(hummocksNoise.getValue(pos.getX(), 0.0D, pos.getZ()));
				if (hummocks < 0.425) continue;
				level.setBlock(pos, (hummocks < 0.8 ? Blocks.MUD : EnvironmentalBlocks.MUDDY_PODZOL.get()).defaultBlockState(), 2);
				if (hummocks < 0.9) continue;
				if (Math.abs(weirdnessFunction.compute(new DensityFunction.SinglePointContext(pos.getX(), pos.getY(), pos.getZ()))) > 0.11D)
					continue;
				EnvironmentalFeatures.SMOOTHCAP_MOSS.get().place(NoneFeatureConfiguration.INSTANCE, level, context.chunkGenerator(), random, pos);
			}
		}
		return true;
	}

}
