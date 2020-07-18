package com.team_abnormals.environmental.core.registry;

import com.team_abnormals.environmental.client.render.SlabfishRenderer;
import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.core.Environmental;
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
//	public static final RegistryObject<EntityType<AxolotlEntity>> AXOLOTL = HELPER.createLivingEntity("axolotl", AxolotlEntity::new, EntityClassification.CREATURE, 0.6F, 0.5F));

    @OnlyIn(Dist.CLIENT)
    public static void registerRendering() {
        RenderingRegistry.registerEntityRenderingHandler((EntityType<? extends SlabfishEntity>) SLABFISH.get(), SlabfishRenderer::new);
        //RenderingRegistry.registerEntityRenderingHandler((EntityType<? extends AxolotlEntity>)AXOLOTL.get(), AxolotlRenderer::new);
    }

    public static void addEntitySpawns() {
        ForgeRegistries.BIOMES.getValues().stream().forEach(EnvironmentalEntities::processSpawning);

        EntitySpawnPlacementRegistry.register(EnvironmentalEntities.SLABFISH.get(), EntitySpawnPlacementRegistry.PlacementType.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canAnimalSpawn);

    }

    private static void processSpawning(Biome biome) {
        if (biome.getCategory() == Category.SWAMP) {
            biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(EnvironmentalEntities.SLABFISH.get(), 50, 2, 4));
        }
    }

    public static void setupAttributes() {
        GlobalEntityTypeAttributes.put(SLABFISH.get(), SlabfishEntity.registerAttributes().func_233813_a_());
    }
}