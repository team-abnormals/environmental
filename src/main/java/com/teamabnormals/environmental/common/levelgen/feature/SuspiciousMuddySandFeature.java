package com.teamabnormals.environmental.common.levelgen.feature;

import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.other.EnvironmentalLootTables;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;

public class SuspiciousMuddySandFeature extends Feature<NoneFeatureConfiguration> {

	public SuspiciousMuddySandFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos origin = context.origin();
		WorldGenLevel level = context.level();
		if (!level.getBlockState(origin).is(EnvironmentalBlocks.MUDDY_SAND.get())) return false;
		level.setBlock(origin, EnvironmentalBlocks.SUSPICIOUS_MUDDY_SAND.get().defaultBlockState(), 2);
		ResourceKey<LootTable> lootTable;
		RandomSource random = context.random();
		if (random.nextInt(4) == 0) {
			lootTable = random.nextInt(100) < 94 ? BuiltInLootTables.FISHING_JUNK : BuiltInLootTables.FISHING_TREASURE;
		} else {
			lootTable = EnvironmentalLootTables.CEDAR_BOG_ORE;
		}
		level.getBlockEntity(origin, BlockEntityType.BRUSHABLE_BLOCK).ifPresent(blockEntity -> blockEntity.setLootTable(lootTable, origin.asLong()));
		return true;
	}

}
