package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Environmental.MOD_ID);

	public static final RegistryObject<SimpleParticleType> CHERRY_BLOSSOM = PARTICLE_TYPES.register("cherry_blossom", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> RED_LOTUS_BLOSSOM = PARTICLE_TYPES.register("red_lotus_blossom", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> WHITE_LOTUS_BLOSSOM = PARTICLE_TYPES.register("white_lotus_blossom", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> PIG_FINDS_TRUFFLE = PARTICLE_TYPES.register("pig_finds_truffle", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> SLABFISH_FINDS_EFFIGY = PARTICLE_TYPES.register("slabfish_finds_effigy", () -> new SimpleParticleType(true));
}
