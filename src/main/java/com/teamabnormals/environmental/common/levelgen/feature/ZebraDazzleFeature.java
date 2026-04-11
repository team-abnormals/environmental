package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zebra;
import com.teamabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ZebraDazzleFeature extends Feature<NoneFeatureConfiguration> {

	public ZebraDazzleFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos pos = context.origin();
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		List<Pair<Zebra, Vec3>> zebras = Lists.newArrayList();

		int spawnedZebras = 0;
		int zebraCount = EnvironmentalConfig.COMMON.zebraGroupSize.get()
				+ EnvironmentalConfig.COMMON.zebraExtraSpawns.get()
				+ EnvironmentalConfig.COMMON.zebraExtraSpawns.get()
				+ EnvironmentalConfig.COMMON.zebraExtraSpawns.get();

		for (int i = 0; i < 64; ++i) {
			int spawnRange = 8;
			double d0 = (double) pos.getX() + (random.nextDouble() - random.nextDouble()) * (double) spawnRange + 0.5D;
			double d2 = (double) pos.getZ() + (random.nextDouble() - random.nextDouble()) * (double) spawnRange + 0.5D;
			BlockPos spawnPos = level.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos((int) d0, pos.getY(), (int) d2));
			double d1 = spawnPos.getY();

			if (level.noCollision(EnvironmentalEntityTypes.ZEBRA.get().getAABB(d0, d1, d2))) {
				if (spawnedZebras < zebraCount && level.getBlockState(spawnPos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON)) { // && SpawnPlacements.checkSpawnRules(EnvironmentalEntityTypes.ZEBRA.get(), level, MobSpawnType.STRUCTURE, BlockPos.containing(d0, d1, d2), level.getRandom())) {
					Zebra zebra = EnvironmentalEntityTypes.ZEBRA.get().create(level.getLevel());
					if (zebra != null) {
						zebras.add(Pair.of(zebra, new Vec3(d0, d1, d2)));
						spawnedZebras++;
					}
				}
			}
		}

		/* Check if zebra group size is equal to or greater than size set in config.
		   previously checked for greater than 16? magic numbers bad!*/
		if (zebras.size() >= EnvironmentalConfig.COMMON.zebraGroupSize.get()) {
			for (Pair<Zebra, Vec3> pair : zebras) {
				Zebra zebra = pair.getFirst();
				Vec3 zebraPos = pair.getSecond();
				zebra.moveTo(zebraPos.x(), zebraPos.y(), zebraPos.z(), level.getRandom().nextFloat() * 360.0F, 0.0F);
				zebra.finalizeSpawn(level, level.getCurrentDifficultyAt(zebra.blockPosition()), MobSpawnType.STRUCTURE, null, null);
				zebra.setBaby(random.nextFloat() < 0.1F);
				level.addFreshEntity(zebra);
				zebra.spawnAnim();
			}
			return true;
		}

		return false;
	}

}