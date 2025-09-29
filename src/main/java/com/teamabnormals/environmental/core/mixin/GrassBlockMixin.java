package com.teamabnormals.environmental.core.mixin;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;
import java.util.Random;

@Mixin(GrassBlock.class)
public abstract class GrassBlockMixin extends Block {

	public GrassBlockMixin(Properties properties) {
		super(properties);
	}

	@WrapOperation(method = "performBonemeal", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/BiomeGenerationSettings;getFlowerFeatures()Ljava/util/List;"))
	private List<ConfiguredFeature<?, ?>> getFlowerFeatures(BiomeGenerationSettings settings, Operation<List<ConfiguredFeature<?, ?>>> original) {
		return settings.features().stream().flatMap(HolderSet::stream).map(Holder::value).flatMap(PlacedFeature::getFeatures).filter((configuredFeature) -> configuredFeature.feature() == Feature.FLOWER).collect(ImmutableList.toImmutableList());
	}

	@WrapOperation(method = "performBonemeal", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"))
	private Object get(List list, int index, Operation<Object> original) {
		return list.get(new Random().nextInt(list.size()));
	}
}