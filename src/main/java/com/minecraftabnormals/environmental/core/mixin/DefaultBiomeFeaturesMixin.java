package com.minecraftabnormals.environmental.core.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {

	@Redirect(method = { "addLakes" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;addFeature(Lnet/minecraft/world/gen/GenerationStage$Decoration;Lnet/minecraft/world/gen/feature/ConfiguredFeature;)V"))
	private static void addLakes(Biome biome, GenerationStage.Decoration stage, ConfiguredFeature<?, ?> feature) {
	}

	@Redirect(method = { "addDesertLakes" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;addFeature(Lnet/minecraft/world/gen/GenerationStage$Decoration;Lnet/minecraft/world/gen/feature/ConfiguredFeature;)V"))
	private static void addDesertLakes(Biome biome, GenerationStage.Decoration stage, ConfiguredFeature<?, ?> feature) {
	}
}
