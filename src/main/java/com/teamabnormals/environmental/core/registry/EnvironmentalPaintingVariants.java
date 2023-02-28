package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalPaintingVariants {
	public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Environmental.MOD_ID);

	public static final RegistryObject<PaintingVariant> SLABFISH = PAINTING_VARIANTS.register("slabfish", () -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> SNAKE_BLOCK = PAINTING_VARIANTS.register("snake_block", () -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> ARCHIVE = PAINTING_VARIANTS.register("archive", () -> new PaintingVariant(64, 48));
}
