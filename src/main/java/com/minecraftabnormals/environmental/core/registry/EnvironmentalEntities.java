package com.minecraftabnormals.environmental.core.registry;

import java.util.ArrayList;
import java.util.List;

import com.minecraftabnormals.environmental.client.render.*;
import com.minecraftabnormals.environmental.common.entity.*;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.abnormals_core.core.utils.RegistryHelper;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = Environmental.MODID, bus = EventBusSubscriber.Bus.MOD)
public class EnvironmentalEntities {
    public static final RegistryHelper HELPER = Environmental.REGISTRY_HELPER;

    public static final RegistryObject<EntityType<SlabfishEntity>> SLABFISH = HELPER.createLivingEntity("slabfish", SlabfishEntity::new, EntityClassification.CREATURE, 0.45F, 0.9F);
    public static final RegistryObject<EntityType<DuckEntity>> DUCK = HELPER.createLivingEntity("duck", DuckEntity::new, EntityClassification.CREATURE, 0.5F, 0.8F);
    public static final RegistryObject<EntityType<DeerEntity>> DEER = HELPER.createLivingEntity("deer", DeerEntity::new, EntityClassification.CREATURE, 1.1F, 1.8F);
    public static final RegistryObject<EntityType<YakEntity>> YAK = HELPER.createLivingEntity("yak", YakEntity::new, EntityClassification.CREATURE, 1.0F, 1.5F);
	public static final RegistryObject<EntityType<KoiEntity>> KOI = HELPER.createLivingEntity("koi", KoiEntity::new, EntityClassification.WATER_AMBIENT, 0.75F, 0.4F);

	public static final RegistryObject<EntityType<DuckEggEntity>> DUCK_EGG = HELPER.createEntity("duck_egg", DuckEggEntity::new, DuckEggEntity::new, EntityClassification.MISC, 0.25F, 0.25F);
	public static final RegistryObject<EntityType<MudBallEntity>> MUD_BALL = HELPER.createEntity("mud_ball", MudBallEntity::new, MudBallEntity::new, EntityClassification.MISC, 0.25F, 0.25F);


    @OnlyIn(Dist.CLIENT)
    public static void registerRendering() {
        RenderingRegistry.registerEntityRenderingHandler(SLABFISH.get(), SlabfishRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(DUCK.get(), DuckRenderer::new);
		RenderingRegistry.registerEntityRenderingHandler(DEER.get(), DeerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(YAK.get(), YakRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(KOI.get(), KoiRenderer::new);
        
        RenderingRegistry.registerEntityRenderingHandler(DUCK_EGG.get(), ProjectileItemRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(MUD_BALL.get(), ProjectileItemRenderer::new);
    }

    public static void registerSpawns() {
        ForgeRegistries.BIOMES.getValues().stream().forEach(EnvironmentalEntities::processSpawning);
        if(EnvironmentalConfig.COMMON.limitFarmAnimalSpawns.get()) {
            ForgeRegistries.BIOMES.getValues().stream().forEach(EnvironmentalEntities::removeSpawns);
        }

        EntitySpawnPlacementRegistry.register(EnvironmentalEntities.SLABFISH.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DuckEntity::canDuckSpawn);
        EntitySpawnPlacementRegistry.register(EnvironmentalEntities.YAK.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
        EntitySpawnPlacementRegistry.register(EnvironmentalEntities.DUCK.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, DuckEntity::canDuckSpawn);
        EntitySpawnPlacementRegistry.register(EnvironmentalEntities.DEER.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);
    }

    private static void processSpawning(Biome biome) {
        if (biome.getCategory() == Category.SWAMP) {
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(EnvironmentalEntities.SLABFISH.get(), 8, 2, 4));
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(EnvironmentalEntities.DUCK.get(), 5, 3, 5));
        }
        
        if (biome.getCategory() == Category.RIVER) {
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(EnvironmentalEntities.DUCK.get(), 6, 2, 4));
        }
        
        if (biome.getCategory() == Category.FOREST) {
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(EnvironmentalEntities.DEER.get(), 12, 1, 4));
        }
		
		if(biome.getCategory() == Category.EXTREME_HILLS)
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(YAK.get(), 23, 2, 4));
    }
    
    private static void removeSpawns(Biome biome) {
        List<Biome.SpawnListEntry> entrysToRemove = new ArrayList<>();
        for(Biome.SpawnListEntry entry : biome.getSpawns(EntityClassification.CREATURE)) {
            if(biome.getCategory() != Biome.Category.FOREST) {
                if (entry.entityType == EntityType.PIG || entry.entityType == EntityType.CHICKEN) {
                    entrysToRemove.add(entry);
                }
            }
            if(biome.getCategory() != Biome.Category.PLAINS) {
                if (entry.entityType == EntityType.COW || entry.entityType == EntityType.SHEEP) {
                    entrysToRemove.add(entry);
                }
            }
        };
        biome.getSpawns(EntityClassification.CREATURE).removeAll(entrysToRemove);
    }

    public static void registerAttributes() {
        GlobalEntityTypeAttributes.put(SLABFISH.get(), SlabfishEntity.registerAttributes().create());
        GlobalEntityTypeAttributes.put(DEER.get(), DeerEntity.registerAttributes().create());
        GlobalEntityTypeAttributes.put(DUCK.get(), DuckEntity.registerAttributes().create());
        GlobalEntityTypeAttributes.put(YAK.get(), YakEntity.registerAttributes().create());
        GlobalEntityTypeAttributes.put(KOI.get(), KoiEntity.registerAttributes().create());
    }
}