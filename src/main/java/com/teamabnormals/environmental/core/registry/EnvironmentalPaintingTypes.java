package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalPaintingTypes {
	public static final DeferredRegister<PaintingVariant> PAINTING_TYPES = DeferredRegister.create(ForgeRegistries.PAINTING_VARIANTS, Environmental.MOD_ID);

	public static final RegistryObject<PaintingVariant> SLABFISH = PAINTING_TYPES.register("slabfish", () -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> SNAKE_BLOCK = PAINTING_TYPES.register("snake_block", () -> new PaintingVariant(32, 32));
	public static final RegistryObject<PaintingVariant> SOMETHING_IN_THE_WATER = PAINTING_TYPES.register("something_in_the_water", () -> new PaintingVariant(48, 32));
}
