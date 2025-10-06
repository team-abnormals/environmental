package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.core.util.registry.EntitySubRegistryHelper;
import com.teamabnormals.environmental.common.entity.animal.Duck;
import com.teamabnormals.environmental.common.entity.animal.PineconeGolem;
import com.teamabnormals.environmental.common.entity.animal.Tapir;
import com.teamabnormals.environmental.common.entity.animal.deer.AbstractDeer;
import com.teamabnormals.environmental.common.entity.animal.deer.Deer;
import com.teamabnormals.environmental.common.entity.animal.deer.Reindeer;
import com.teamabnormals.environmental.common.entity.animal.koi.Koi;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.entity.animal.yak.Yak;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zebra;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zonkey;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zorse;
import com.teamabnormals.environmental.common.entity.projectile.ThrownDuckEgg;
import com.teamabnormals.environmental.common.entity.projectile.ThrownMudBall;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.neoforged.neoforge.registries.DeferredHolder;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalEntityTypes {
	public static final EntitySubRegistryHelper ENTITY_TYPES = Environmental.REGISTRY_HELPER.getEntitySubHelper();

	public static final DeferredHolder<EntityType<?>, EntityType<Slabfish>> SLABFISH = ENTITY_TYPES.createEntity("slabfish", Slabfish::new, MobCategory.CREATURE, 0.45F, 0.9F);
	public static final DeferredHolder<EntityType<?>, EntityType<Duck>> DUCK = ENTITY_TYPES.createEntity("duck", Duck::new, MobCategory.CREATURE, 0.5F, 0.75F);
	public static final DeferredHolder<EntityType<?>, EntityType<Deer>> DEER = ENTITY_TYPES.createEntity("deer", Deer::new, MobCategory.CREATURE, 0.8F, 1.6F);
	public static final DeferredHolder<EntityType<?>, EntityType<Reindeer>> REINDEER = ENTITY_TYPES.createEntity("reindeer", Reindeer::new, MobCategory.CREATURE, 0.8F, 1.6F);
	public static final DeferredHolder<EntityType<?>, EntityType<Yak>> YAK = ENTITY_TYPES.createEntity("yak", Yak::new, MobCategory.CREATURE, 1.2F, 1.4F);
	public static final DeferredHolder<EntityType<?>, EntityType<Koi>> KOI = ENTITY_TYPES.createEntity("koi", Koi::new, MobCategory.WATER_AMBIENT, 0.75F, 0.4F);
	public static final DeferredHolder<EntityType<?>, EntityType<Tapir>> TAPIR = ENTITY_TYPES.createEntity("tapir", Tapir::new, MobCategory.CREATURE, 0.9F, 0.98F);
	public static final DeferredHolder<EntityType<?>, EntityType<Zebra>> ZEBRA = ENTITY_TYPES.createEntity("zebra", Zebra::new, MobCategory.CREATURE, 1.3964844F, 1.5F);
	public static final DeferredHolder<EntityType<?>, EntityType<Zorse>> ZORSE = ENTITY_TYPES.createEntity("zorse", Zorse::new, MobCategory.CREATURE, 1.3964844F, 1.6F);
	public static final DeferredHolder<EntityType<?>, EntityType<Zonkey>> ZONKEY = ENTITY_TYPES.createEntity("zonkey", Zonkey::new, MobCategory.CREATURE, 1.3964844F, 1.5F);
	public static final DeferredHolder<EntityType<?>, EntityType<PineconeGolem>> PINECONE_GOLEM = ENTITY_TYPES.createEntity("pinecone_golem", PineconeGolem::new, MobCategory.MISC, 0.65F, 0.65F);

	public static final DeferredHolder<EntityType<?>, EntityType<ThrownDuckEgg>> DUCK_EGG = ENTITY_TYPES.createEntity("duck_egg", ThrownDuckEgg::new, MobCategory.MISC, 0.25F, 0.25F);
	public static final DeferredHolder<EntityType<?>, EntityType<ThrownMudBall>> MUD_BALL = ENTITY_TYPES.createEntity("mud_ball", ThrownMudBall::new, MobCategory.MISC, 0.25F, 0.25F);

	@SubscribeEvent
	public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
		event.register(SLABFISH.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Duck::checkDuckSpawnRules, Operation.AND);
		event.register(YAK.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, Operation.AND);
		event.register(DUCK.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Duck::checkDuckSpawnRules, Operation.AND);
		event.register(DEER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractDeer::checkDeerSpawnRules, Operation.AND);
		event.register(REINDEER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AbstractDeer::checkDeerSpawnRules, Operation.AND);
		event.register(KOI.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Koi::canKoiSpawn, Operation.AND);
		event.register(TAPIR.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, Operation.AND);
		event.register(ZEBRA.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, Operation.AND);
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(SLABFISH.get(), Slabfish.registerAttributes().build());
		event.put(DEER.get(), Deer.registerAttributes().build());
		event.put(REINDEER.get(), Reindeer.registerAttributes().build());
		event.put(DUCK.get(), Duck.registerAttributes().build());
		event.put(YAK.get(), Yak.registerAttributes().build());
		event.put(KOI.get(), Koi.registerAttributes().build());
		event.put(TAPIR.get(), Tapir.createAttributes().build());
		event.put(ZEBRA.get(), Zebra.createAttributes().build());
		event.put(ZORSE.get(), Zorse.createAttributes().build());
		event.put(ZONKEY.get(), Zonkey.createAttributes().build());
		event.put(PINECONE_GOLEM.get(), PineconeGolem.createAttributes().build());
	}
}