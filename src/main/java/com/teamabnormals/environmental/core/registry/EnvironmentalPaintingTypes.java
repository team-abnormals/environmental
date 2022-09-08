package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalPaintingTypes {
	public static final DeferredRegister<Motive> PAINTING_TYPES = DeferredRegister.create(ForgeRegistries.PAINTING_TYPES, Environmental.MOD_ID);

	public static final RegistryObject<Motive> SLABFISH = PAINTING_TYPES.register("slabfish", () -> new Motive(32, 32));
	public static final RegistryObject<Motive> SNAKE_BLOCK = PAINTING_TYPES.register("snake_block", () -> new Motive(32, 32));
	public static final RegistryObject<Motive> SOMETHING_IN_THE_WATER = PAINTING_TYPES.register("something_in_the_water", () -> new Motive(48, 32));
}
