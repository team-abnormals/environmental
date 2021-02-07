package com.minecraftabnormals.environmental.core.mixin;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {

	@Redirect(method = "withLavaAndWaterLakes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/BiomeGenerationSettings$Builder;withFeature(Lnet/minecraft/world/gen/GenerationStage$Decoration;Lnet/minecraft/world/gen/feature/ConfiguredFeature;)Lnet/minecraft/world/biome/BiomeGenerationSettings$Builder;"))
	private static BiomeGenerationSettings.Builder addLakes(BiomeGenerationSettings.Builder biome, GenerationStage.Decoration stage, ConfiguredFeature<?, ?> feature) {
		return biome;
	}
}
