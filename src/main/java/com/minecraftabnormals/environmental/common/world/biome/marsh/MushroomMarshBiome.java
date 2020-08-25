package com.minecraftabnormals.environmental.common.world.biome.marsh;

import com.minecraftabnormals.environmental.common.world.EnvironmentalBiomeFeatures;
import com.minecraftabnormals.environmental.common.world.biome.AbnormalsBiome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class MushroomMarshBiome extends AbnormalsBiome {
    public MushroomMarshBiome(Biome.Builder builder) {
        super(builder);
    }

    @Override
    public void addFeatures() {
        this.func_235063_a_(DefaultBiomeFeatures.SWAMP_HUT); // SWAMP_HUT
        this.func_235063_a_(DefaultBiomeFeatures.MINESHAFT_NORMAL); // MINESHAFT
        this.func_235063_a_(DefaultBiomeFeatures.RUINED_PORTAL_SWAMP); // RUINED_PORTAL
        
        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addSwampClayDisks(this);
        EnvironmentalBiomeFeatures.addMarshPools(this);

        EnvironmentalBiomeFeatures.addDenseCattails(this);
        EnvironmentalBiomeFeatures.addMarshVegetation(this);
        DefaultBiomeFeatures.addMushrooms(this);
        EnvironmentalBiomeFeatures.addMarshMushrooms(this);
        EnvironmentalBiomeFeatures.addDuckweed(this, 0.1F);
        DefaultBiomeFeatures.addExtraReedsAndPumpkins(this);
        DefaultBiomeFeatures.addTallGrass(this);
        DefaultBiomeFeatures.addVeryDenseGrass(this);
        EnvironmentalBiomeFeatures.addRice(this);
        EnvironmentalBiomeFeatures.addGiantTallGrass(this, 50);

        DefaultBiomeFeatures.func_235191_ai_(this);
        DefaultBiomeFeatures.addFreezeTopLayer(this);
    }

    @Override
    public void addSpawns() {
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.PIG, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
        this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.COW, 8, 4, 4));
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

    @OnlyIn(Dist.CLIENT)
    public int getGrassColor(double posX, double posZ) {
        double d0 = INFO_NOISE.noiseAt(posX * 0.0225D, posZ * 0.0225D, false);
        return d0 < -0.1D ? 6263617 : 6195253;
    }

    @OnlyIn(Dist.CLIENT)
    public int getFoliageColor() {
        return 5468214;
    }
}