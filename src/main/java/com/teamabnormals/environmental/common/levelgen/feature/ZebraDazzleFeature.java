package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.common.entity.animal.Zebra;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.WorldGenLevel;
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
		for (int i = 0; i < 64; ++i) {
			int spawnRange = 8;
			double d0 = (double) pos.getX() + (random.nextDouble() - random.nextDouble()) * (double) spawnRange + 0.5D;
			double d1 = pos.getY() + random.nextInt(3) - 1;
			double d2 = (double) pos.getZ() + (random.nextDouble() - random.nextDouble()) * (double) spawnRange + 0.5D;
			if (level.noCollision(EnvironmentalEntityTypes.ZEBRA.get().getAABB(d0, d1, d2))) {
				if (spawnedZebras < 24 && SpawnPlacements.checkSpawnRules(EnvironmentalEntityTypes.ZEBRA.get(), level, MobSpawnType.STRUCTURE, new BlockPos(d0, d1, d2), level.getRandom())) {
					Zebra zebra = EnvironmentalEntityTypes.ZEBRA.get().create(level.getLevel());
					if (zebra != null) {
						zebras.add(Pair.of(zebra, new Vec3(d0, d1, d2)));
						spawnedZebras++;
					}
				}
			}
		}

		if (zebras.size() > 16) {
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