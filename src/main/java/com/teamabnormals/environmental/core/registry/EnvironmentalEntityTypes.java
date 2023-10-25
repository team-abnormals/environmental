package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.core.util.registry.EntitySubRegistryHelper;
import com.teamabnormals.environmental.common.entity.animal.*;
import com.teamabnormals.environmental.common.entity.animal.deer.AbstractDeer;
import com.teamabnormals.environmental.common.entity.animal.deer.Deer;
import com.teamabnormals.environmental.common.entity.animal.deer.Reindeer;
import com.teamabnormals.environmental.common.entity.animal.koi.Koi;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.entity.projectile.ThrownDuckEgg;
import com.teamabnormals.environmental.common.entity.projectile.ThrownMudBall;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.RegistryObject;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalEntityTypes {
	public static final EntitySubRegistryHelper HELPER = Environmental.REGISTRY_HELPER.getEntitySubHelper();

	public static final RegistryObject<EntityType<Slabfish>> SLABFISH = HELPER.createLivingEntity("slabfish", Slabfish::new, MobCategory.CREATURE, 0.45F, 0.9F);
	public static final RegistryObject<EntityType<Duck>> DUCK = HELPER.createLivingEntity("duck", Duck::new, MobCategory.CREATURE, 0.5F, 0.8F);
	public static final RegistryObject<EntityType<Deer>> DEER = HELPER.createLivingEntity("deer", Deer::new, MobCategory.CREATURE, 0.8F, 1.6F);
	public static final RegistryObject<EntityType<Reindeer>> REINDEER = HELPER.createLivingEntity("reindeer", Reindeer::new, MobCategory.CREATURE, 0.8F, 1.6F);
	public static final RegistryObject<EntityType<Yak>> YAK = HELPER.createLivingEntity("yak", Yak::new, MobCategory.CREATURE, 1.0F, 1.5F);
	public static final RegistryObject<EntityType<Koi>> KOI = HELPER.createLivingEntity("koi", Koi::new, MobCategory.WATER_AMBIENT, 0.75F, 0.4F);
	public static final RegistryObject<EntityType<FennecFox>> FENNEC_FOX = HELPER.createLivingEntity("fennec_fox", FennecFox::new, MobCategory.CREATURE, 0.75F, 0.5F);
	public static final RegistryObject<EntityType<Tapir>> TAPIR = HELPER.createLivingEntity("tapir", Tapir::new, MobCategory.CREATURE, 0.9F, 0.9F);
	public static final RegistryObject<EntityType<Zebra>> ZEBRA = HELPER.createLivingEntity("zebra", Zebra::new, MobCategory.CREATURE, 1.3964844F, 1.5F);

	public static final RegistryObject<EntityType<ThrownDuckEgg>> DUCK_EGG = HELPER.createEntity("duck_egg", ThrownDuckEgg::new, ThrownDuckEgg::new, MobCategory.MISC, 0.25F, 0.25F);
	public static final RegistryObject<EntityType<ThrownMudBall>> MUD_BALL = HELPER.createEntity("mud_ball", ThrownMudBall::new, ThrownMudBall::new, MobCategory.MISC, 0.25F, 0.25F);

	public static void registerSpawns() {
		SpawnPlacements.register(SLABFISH.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Duck::checkDuckSpawnRules);
		SpawnPlacements.register(YAK.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(DUCK.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Duck::checkDuckSpawnRules);
		SpawnPlacements.register(DEER.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractDeer::checkDeerSpawnRules);
		SpawnPlacements.register(REINDEER.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractDeer::checkDeerSpawnRules);
		SpawnPlacements.register(KOI.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Koi::canKoiSpawn);
		SpawnPlacements.register(FENNEC_FOX.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(TAPIR.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
		SpawnPlacements.register(ZEBRA.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SLABFISH.get(), Slabfish.registerAttributes().build());
		event.put(DEER.get(), Deer.registerAttributes().build());
		event.put(REINDEER.get(), Reindeer.registerAttributes().build());
		event.put(DUCK.get(), Duck.registerAttributes().build());
		event.put(YAK.get(), Yak.registerAttributes().build());
		event.put(KOI.get(), Koi.registerAttributes().build());
		event.put(FENNEC_FOX.get(), Fox.createAttributes().build());
		event.put(TAPIR.get(), Tapir.createAttributes().build());
		event.put(ZEBRA.get(), Zebra.createAttributes().build());
	}
}