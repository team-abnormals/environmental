package com.minecraftabnormals.environmental.common.world.biome;

import com.minecraftabnormals.environmental.common.world.EnvironmentalBiomeFeatures;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public final class CherryBlossomForestBiome extends Biome {
    public CherryBlossomForestBiome() {
        super((new Biome.Builder())
        		.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)
        		.precipitation(Biome.RainType.RAIN).category(Biome.Category.FOREST)
        		.depth(0.1F)
        		.scale(0.2F)
        		.temperature(0.75F)
        		.downfall(0.8F)
        		.func_235097_a_((new BiomeAmbience.Builder())
        				.setWaterColor(5216182)
        				.setWaterFogColor(335411)
        				.setFogColor(12638463)
        				.setMoodSound(MoodSoundAmbience.field_235027_b_)
        				.build())
        		.parent((String)null));

        DefaultBiomeFeatures.func_235196_b_(this);
        this.func_235063_a_(DefaultBiomeFeatures.RUINED_PORTAL_STANDARD);
        DefaultBiomeFeatures.addMonsterRooms(this);

        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addSedimentDisks(this);
        
        DefaultBiomeFeatures.addLakes(this);
        DefaultBiomeFeatures.addSprings(this);
        
        EnvironmentalBiomeFeatures.addCherryTrees(this);
        EnvironmentalBiomeFeatures.addBlossomVegetation(this);
        EnvironmentalBiomeFeatures.addGrass(this);
        
        DefaultBiomeFeatures.addTallGrass(this);
        DefaultBiomeFeatures.addMushrooms(this);

        DefaultBiomeFeatures.addFreezeTopLayer(this);

        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.PIG, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.COW, 8, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.PANDA, 80, 1, 2));
        
        this.addSpawn(EntityClassification.WATER_AMBIENT, new Biome.SpawnListEntry(EntityType.SALMON, 15, 1, 5));
        
        this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
        
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
        this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 1, 1, 1));
    }

//    @Override
//    public Biome getHill(net.minecraft.world.gen.INoiseRandom rand) {
//        int chance = rand.random(4);
//        return chance == 0 ? EnvironmentalBiomes.MUSHROOM_MARSH.get() : EnvironmentalBiomes.MARSH.get();
//    }
}