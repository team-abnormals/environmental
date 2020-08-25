package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnvironmentalParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Environmental.MODID);

    public static final RegistryObject<BasicParticleType> KILN_SMOKE        = PARTICLE_TYPES.register("kiln_smoke", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> CHERRY_BLOSSOM    = PARTICLE_TYPES.register("cherry_blossom", () -> new BasicParticleType(false));
    public static final RegistryObject<BasicParticleType> LOTUS_BLOSSOM     = PARTICLE_TYPES.register("lotus_blossom", () -> new BasicParticleType(true));
}
