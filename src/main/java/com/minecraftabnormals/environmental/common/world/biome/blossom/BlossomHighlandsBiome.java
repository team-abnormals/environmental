package com.minecraftabnormals.environmental.common.world.biome.blossom;

import com.minecraftabnormals.environmental.common.world.EnvironmentalBiomeFeatures;
import com.minecraftabnormals.environmental.common.world.EnvironmentalFeatureConfigs;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBiomes;
import com.teamabnormals.abnormals_core.common.world.biome.AbnormalsBiome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.INoiseRandom;

public final class BlossomHighlandsBiome extends AbnormalsBiome {
    public BlossomHighlandsBiome(Biome.Builder builder) {
        super(builder);
    }
    
    @Override
    public void addFeatures() {
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
        EnvironmentalBiomeFeatures.addBlossomBamboo(this);
        EnvironmentalBiomeFeatures.addBlossomVegetation(this);
        EnvironmentalBiomeFeatures.addGrass(this);
        EnvironmentalBiomeFeatures.addShortFlower(EnvironmentalFeatureConfigs.RED_LOTUS_FLOWER, this, 3);

        DefaultBiomeFeatures.addTallGrass(this);
        DefaultBiomeFeatures.addMushrooms(this);

        DefaultBiomeFeatures.addFreezeTopLayer(this);
    }

    @Override
    public void addSpawns() {
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
    
    @Override
    public Biome getHill(INoiseRandom rand) {
        return EnvironmentalBiomes.BLOSSOM_VALLEYS.get();
    }
}