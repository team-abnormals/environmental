package com.team_abnormals.environmental.common.world.biome;

import com.team_abnormals.environmental.core.registry.EnvironmentalBiomes;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class MarshBiome extends Biome {
	public MarshBiome() {
		super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG).precipitation(Biome.RainType.RAIN).category(Biome.Category.SWAMP).depth(-0.25F).scale(0.0F).temperature(0.8F).downfall(0.9F).waterColor(6134398).waterFogColor(2569515).parent((String) null));
		this.addStructure(Feature.SWAMP_HUT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
		this.addStructure(Feature.MINESHAFT.withConfiguration(new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL)));
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addStructures(this);
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
		
		DefaultBiomeFeatures.addFossils(this);
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