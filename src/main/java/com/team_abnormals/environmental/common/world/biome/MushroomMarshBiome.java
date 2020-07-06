package com.team_abnormals.environmental.common.world.biome;

import com.team_abnormals.environmental.common.world.EnvironmentalBiomeFeatures;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class MushroomMarshBiome extends Biome {
	public MushroomMarshBiome() {
		super((new Biome.Builder())
				.surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG)
				.precipitation(Biome.RainType.RAIN)
				.category(Biome.Category.SWAMP)
				.depth(-0.25F)
				.scale(0.0F)
				.temperature(0.8F)
				.downfall(0.9F)
				.func_235097_a_((new BiomeAmbience.Builder())
				.func_235246_b_(6134398)  // waterColor
				.func_235248_c_(2302743)  // waterFogColor
				.func_235239_a_(12638463) // fogColor
				.func_235243_a_(MoodSoundAmbience.field_235027_b_)
				.func_235238_a_()).parent((String) null));

		this.func_235063_a_(DefaultBiomeFeatures.field_235172_j_); // SWAMP_HUT
		this.func_235063_a_(DefaultBiomeFeatures.field_235150_b_); // MINESHAFT
		this.func_235063_a_(DefaultBiomeFeatures.field_235130_B_); // RUINED_PORTAL
		
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