package com.minecraftabnormals.environmental.core.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.GrassBlock;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@Mixin(GrassBlock.class)
public abstract class GrassBlockMixin extends Block {

	public GrassBlockMixin(Properties properties) {
		super(properties);
	}

	@Redirect(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/BiomeGenerationSettings;getFlowerFeatures()Ljava/util/List;"))
	private List<ConfiguredFeature<?, ?>> getFlowerFeatures(BiomeGenerationSettings biomeGenerationSettings) {
		return biomeGenerationSettings.getFeatures().stream().flatMap(Collection::stream).map(Supplier::get).flatMap(ConfiguredFeature::func_242768_d).filter((configuredFeature) -> configuredFeature.feature == Feature.FLOWER).collect(ImmutableList.toImmutableList());
	}

	@Redirect(method = "grow", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"))
	private Object get(List list, int index) {
		return list.get(new Random().nextInt(list.size()));
	}
}
