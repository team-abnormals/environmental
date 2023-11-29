package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.client.particle.CattailFluffParticle;
import com.teamabnormals.environmental.client.particle.CherryBlossomParticle;
import com.teamabnormals.environmental.client.particle.LotusBlossomParticle;
import com.teamabnormals.environmental.client.particle.PigFindsTruffleParticle;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EnvironmentalParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Environmental.MOD_ID);

	public static final RegistryObject<SimpleParticleType> CHERRY_BLOSSOM = PARTICLE_TYPES.register("cherry_blossom", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> CHEERFUL_CHERRY_BLOSSOM = PARTICLE_TYPES.register("cheerful_cherry_blossom", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> MOODY_CHERRY_BLOSSOM = PARTICLE_TYPES.register("moody_cherry_blossom", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> RED_LOTUS_BLOSSOM = PARTICLE_TYPES.register("red_lotus_blossom", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> WHITE_LOTUS_BLOSSOM = PARTICLE_TYPES.register("white_lotus_blossom", () -> new SimpleParticleType(false));
	public static final RegistryObject<SimpleParticleType> PIG_FINDS_TRUFFLE = PARTICLE_TYPES.register("pig_finds_truffle", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> TAPIR_FINDS_FLORA = PARTICLE_TYPES.register("tapir_finds_flora", () -> new SimpleParticleType(true));
	public static final RegistryObject<SimpleParticleType> CATTAIL_FLUFF = PARTICLE_TYPES.register("cattail_fluff", () -> new SimpleParticleType(false));

	@SubscribeEvent
	public static void registerParticleFactorys(RegisterParticleProvidersEvent event) {
		event.register(EnvironmentalParticleTypes.CHERRY_BLOSSOM.get(), CherryBlossomParticle.Factory::new);
		event.register(EnvironmentalParticleTypes.CHEERFUL_CHERRY_BLOSSOM.get(), CherryBlossomParticle.Factory::new);
		event.register(EnvironmentalParticleTypes.MOODY_CHERRY_BLOSSOM.get(), CherryBlossomParticle.Factory::new);
		event.register(EnvironmentalParticleTypes.RED_LOTUS_BLOSSOM.get(), LotusBlossomParticle.Factory::new);
		event.register(EnvironmentalParticleTypes.WHITE_LOTUS_BLOSSOM.get(), LotusBlossomParticle.Factory::new);
		event.register(EnvironmentalParticleTypes.PIG_FINDS_TRUFFLE.get(), PigFindsTruffleParticle.Factory::new);
		event.register(EnvironmentalParticleTypes.TAPIR_FINDS_FLORA.get(), PigFindsTruffleParticle.Factory::new);
		event.register(EnvironmentalParticleTypes.CATTAIL_FLUFF.get(), CattailFluffParticle.Factory::new);
	}
}
