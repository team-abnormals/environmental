package com.minecraftabnormals.environmental.core.mixin;

import com.minecraftabnormals.environmental.core.registry.EnvironmentalBiomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {

	@Inject(at = @At("RETURN"), method = "getGrassColor", cancellable = true)
	private void getGrassColor(double posX, double posZ, CallbackInfoReturnable<Integer> info) {
		ResourceLocation biome = ((Biome) (Object) this).getRegistryName();
		if (biome.equals(EnvironmentalBiomes.MARSH.get().getRegistryName()) || biome.equals(EnvironmentalBiomes.MUSHROOM_MARSH.get().getRegistryName())) {
			double d0 = Biome.INFO_NOISE.noiseAt(posX * 0.0225D, posZ * 0.0225D, false);
			info.setReturnValue(d0 < -0.1D ? 6263617 : 6195253);
		}
	}
}
