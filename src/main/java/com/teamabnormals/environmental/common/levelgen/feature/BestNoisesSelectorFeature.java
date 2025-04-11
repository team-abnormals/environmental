package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.levelgen.feature.configurations.BestNoisesSelectorFeatureConfiguration;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.Comparator;
import java.util.List;

public class BestNoisesSelectorFeature extends Feature<BestNoisesSelectorFeatureConfiguration> {

	public BestNoisesSelectorFeature(Codec<BestNoisesSelectorFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<BestNoisesSelectorFeatureConfiguration> context) {
		BestNoisesSelectorFeatureConfiguration config = context.config();
		RandomSource random = context.random();
		WorldGenLevel level = context.level();
		ChunkGenerator chunkgenerator = context.chunkGenerator();
		BlockPos origin = context.origin();

		NormalNoise[] noises = config.getNoises(level);
		List<Pair<Integer, Double>> values = Lists.newArrayList();

		for (int i = 0; i < noises.length; i++)
			values.add(Pair.of(i, noises[i].getValue(origin.getX(), origin.getY(), origin.getZ())));

		values.sort(Comparator.comparingDouble(pair -> -pair.getSecond()));

		if (config.blending <= 0.0D)
			return config.features.get(values.get(0).getFirst()).value().place(level, chunkgenerator, random, origin);

		double bestvalue = values.get(0).getSecond();
		double blendthreshold = bestvalue - config.blending;

		DoubleList validvalues = new DoubleArrayList();

		for (int i = 0; i < noises.length; i++) {
			double d0 = (values.get(i).getSecond() - blendthreshold) / config.blending;
			if (d0 >= 0.0D)
				validvalues.add(d0);
		}

		int validcount = validvalues.size();
		int featureindex = validcount - 1;
		double sum = validvalues.doubleStream().sum();
		double randomvalue = random.nextDouble();

		for (int i = 0; i < validcount; i++) {
			double d0 = validvalues.getDouble(i) / sum;
			if (randomvalue < d0) {
				featureindex = i;
				break;
			} else {
				randomvalue -= d0;
			}
		}

		return config.features.get(values.get(featureindex).getFirst()).value().place(level, chunkgenerator, random, origin);
	}
}