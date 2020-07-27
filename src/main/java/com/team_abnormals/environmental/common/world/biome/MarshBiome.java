package com.team_abnormals.environmental.common.world.biome;

import com.team_abnormals.environmental.common.world.EnvironmentalBiomeFeatures;
import com.team_abnormals.environmental.core.registry.EnvironmentalBiomes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class MarshBiome extends Biome {
    public MarshBiome() {

        super((new Biome.Builder())
                .surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)
                .precipitation(Biome.RainType.RAIN)
                .category(Biome.Category.SWAMP)
                .depth(-0.25F)
                .scale(0.0F)
                .temperature(0.8F)
                .downfall(0.9F)
                .func_235097_a_((new BiomeAmbience.Builder())
                        .setWaterColor(6134398)
                        .setWaterFogColor(2302743)
                        .setFogColor(12638463)
                        .setMoodSound(MoodSoundAmbience.field_235027_b_)
                        .build()).parent((String) null));

        this.func_235063_a_(DefaultBiomeFeatures.SWAMP_HUT);
        this.func_235063_a_(DefaultBiomeFeatures.MINESHAFT_NORMAL);
        this.func_235063_a_(DefaultBiomeFeatures.RUINED_PORTAL_SWAMP);

        DefaultBiomeFeatures.addCarvers(this);
        DefaultBiomeFeatures.addMonsterRooms(this);
        DefaultBiomeFeatures.addStoneVariants(this);
        DefaultBiomeFeatures.addOres(this);
        DefaultBiomeFeatures.addSwampClayDisks(this);
        EnvironmentalBiomeFeatures.addMarshPools(this);

        EnvironmentalBiomeFeatures.addDenseCattails(this);
        EnvironmentalBiomeFeatures.addMarshVegetation(this);
        EnvironmentalBiomeFeatures.addDuckweed(this, 0.1F);
        DefaultBiomeFeatures.addMushrooms(this);
        DefaultBiomeFeatures.addExtraReedsAndPumpkins(this);
        DefaultBiomeFeatures.addTallGrass(this);
        DefaultBiomeFeatures.addVeryDenseGrass(this);
        EnvironmentalBiomeFeatures.addRice(this);
        EnvironmentalBiomeFeatures.addGiantTallGrass(this, 50);

        DefaultBiomeFeatures.func_235191_ai_(this);
        DefaultBiomeFeatures.addFreezeTopLayer(this);

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


    @Override
    public Biome getHill(net.minecraft.world.gen.INoiseRandom rand) {
        int chance = rand.random(4);
        return chance == 0 ? EnvironmentalBiomes.MUSHROOM_MARSH.get() : EnvironmentalBiomes.MARSH.get();
    }
}