package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.abnormals_core.core.util.registry.EntitySubRegistryHelper;
import com.minecraftabnormals.environmental.client.render.*;
import com.minecraftabnormals.environmental.common.entity.*;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalEntities {
	public static final EntitySubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getEntitySubHelper();

	public static final RegistryObject<EntityType<SlabfishEntity>> SLABFISH = HELPER.createLivingEntity("slabfish", SlabfishEntity::new, EntityClassification.CREATURE, 0.45F, 0.9F);
	public static final RegistryObject<EntityType<DuckEntity>> DUCK = HELPER.createLivingEntity("duck", DuckEntity::new, EntityClassification.CREATURE, 0.5F, 0.8F);
	public static final RegistryObject<EntityType<DeerEntity>> DEER = HELPER.createLivingEntity("deer", DeerEntity::new, EntityClassification.CREATURE, 1.1F, 1.8F);
	public static final RegistryObject<EntityType<YakEntity>> YAK = HELPER.createLivingEntity("yak", YakEntity::new, EntityClassification.CREATURE, 1.0F, 1.5F);
	public static final RegistryObject<EntityType<KoiEntity>> KOI = HELPER.createLivingEntity("koi", KoiEntity::new, EntityClassification.WATER_AMBIENT, 0.75F, 0.4F);
	public static final RegistryObject<EntityType<FennecFoxEntity>> FENNEC_FOX = HELPER.createLivingEntity("fennec_fox", FennecFoxEntity::new, EntityClassification.CREATURE, 0.75F, 0.5F);

	public static final RegistryObject<EntityType<DuckEggEntity>> DUCK_EGG = HELPER.createEntity("duck_egg", DuckEggEntity::new, DuckEggEntity::new, EntityClassification.MISC, 0.25F, 0.25F);
	public static final RegistryObject<EntityType<MudBallEntity>> MUD_BALL = HELPER.createEntity("mud_ball", MudBallEntity::new, MudBallEntity::new, EntityClassification.MISC, 0.25F, 0.25F);


	public static void registerSpawns() {
		EntitySpawnPlacementRegistry.register(SLABFISH.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DuckEntity::canDuckSpawn);
		EntitySpawnPlacementRegistry.register(YAK.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		EntitySpawnPlacementRegistry.register(DUCK.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DuckEntity::canDuckSpawn);
		EntitySpawnPlacementRegistry.register(DEER.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
		EntitySpawnPlacementRegistry.register(KOI.get(), EntitySpawnPlacementRegistry.PlacementType.IN_WATER, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, KoiEntity::canKoiSpawn);
		EntitySpawnPlacementRegistry.register(FENNEC_FOX.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::checkAnimalSpawnRules);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SLABFISH.get(), SlabfishEntity.registerAttributes().build());
		event.put(DEER.get(), DeerEntity.registerAttributes().build());
		event.put(DUCK.get(), DuckEntity.registerAttributes().build());
		event.put(YAK.get(), YakEntity.registerAttributes().build());
		event.put(KOI.get(), KoiEntity.registerAttributes().build());
		event.put(FENNEC_FOX.get(), FoxEntity.createAttributes().build());
	}

	public static void registerRendering() {
		RenderingRegistry.registerEntityRenderingHandler(SLABFISH.get(), SlabfishRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(DUCK.get(), DuckRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(DEER.get(), DeerRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(YAK.get(), YakRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(KOI.get(), KoiRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(FENNEC_FOX.get(), FennecFoxRenderer::new);

		RenderingRegistry.registerEntityRenderingHandler(DUCK_EGG.get(), ProjectileItemRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(MUD_BALL.get(), ProjectileItemRenderer::new);
	}
}