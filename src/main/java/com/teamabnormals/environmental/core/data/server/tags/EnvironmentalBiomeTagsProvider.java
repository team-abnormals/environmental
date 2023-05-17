package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EnvironmentalBiomeTagsProvider extends BiomeTagsProvider {

	public EnvironmentalBiomeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Environmental.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.tag(BiomeTags.IS_FOREST).add(EnvironmentalBiomes.BLOSSOM_WOODS.get(), EnvironmentalBiomes.BLOSSOM_VALLEYS.get());
		this.tag(BiomeTags.HAS_RUINED_PORTAL_SWAMP).add(EnvironmentalBiomes.MARSH.get());
		this.tag(BiomeTags.HAS_SWAMP_HUT).add(EnvironmentalBiomes.MARSH.get());
		this.tag(BiomeTags.IS_OVERWORLD).add(EnvironmentalBiomes.MARSH.get(), EnvironmentalBiomes.BLOSSOM_WOODS.get(), EnvironmentalBiomes.BLOSSOM_VALLEYS.get());
		this.tag(BiomeTags.STRONGHOLD_BIASED_TO).add(EnvironmentalBiomes.MARSH.get(), EnvironmentalBiomes.BLOSSOM_WOODS.get(), EnvironmentalBiomes.BLOSSOM_VALLEYS.get());
		this.tag(BiomeTags.WATER_ON_MAP_OUTLINES).add(EnvironmentalBiomes.MARSH.get());
		this.tag(BiomeTags.HAS_CLOSER_WATER_FOG).add(EnvironmentalBiomes.MARSH.get());

		this.tag(EnvironmentalBiomeTags.HAS_SLABFISH).addTag(Tags.Biomes.IS_SWAMP);
		this.tag(EnvironmentalBiomeTags.HAS_DUCK).add(Biomes.RIVER).addTag(Tags.Biomes.IS_SWAMP);
		this.tag(EnvironmentalBiomeTags.HAS_DEER).addTag(BiomeTags.IS_FOREST);
		this.tag(EnvironmentalBiomeTags.HAS_YAK).addTag(BiomeTags.IS_MOUNTAINS).addTag(BiomeTags.IS_HILL);

		this.tag(EnvironmentalBiomeTags.HAS_HUSK).addTag(Biomes.IS_DESERT);
		this.tag(EnvironmentalBiomeTags.HAS_STRAY).addTag(Tags.Biomes.IS_SNOWY);

		this.tag(EnvironmentalBiomeTags.HAS_SHEEP).addTag(Tags.Biomes.IS_PLAINS);
		this.tag(EnvironmentalBiomeTags.HAS_COW).addTag(Tags.Biomes.IS_PLAINS);
		this.tag(EnvironmentalBiomeTags.HAS_PIG).addTag(BiomeTags.IS_FOREST);
		this.tag(EnvironmentalBiomeTags.HAS_CHICKEN).addTag(BiomeTags.IS_FOREST);

		this.tag(EnvironmentalBiomeTags.HAS_BLUEBELL).addTag(BiomeTags.IS_FOREST);
		this.tag(EnvironmentalBiomeTags.HAS_VIOLET).addTag(BiomeTags.IS_TAIGA);
		this.tag(EnvironmentalBiomeTags.HAS_WARM_HIBISCUS).addTag(BiomeTags.IS_JUNGLE);
		this.tag(EnvironmentalBiomeTags.HAS_COOL_HIBISCUS).addTag(BiomeTags.IS_JUNGLE);
		this.tag(EnvironmentalBiomeTags.HAS_CATTAILS).add(Biomes.RIVER).addTag(Tags.Biomes.IS_SWAMP);
		this.tag(EnvironmentalBiomeTags.HAS_TALL_DEAD_BUSH).addTag(Biomes.IS_DESERT);
		this.tag(EnvironmentalBiomeTags.HAS_TALL_DEAD_BUSH_BADLANDS).addTag(Biomes.IS_BADLANDS);
		this.tag(EnvironmentalBiomeTags.HAS_MYCELIUM_SPROUTS).addTag(Tags.Biomes.IS_MUSHROOM);
		this.tag(EnvironmentalBiomeTags.HAS_MUD_DISK).addTag(Tags.Biomes.IS_SWAMP);
		
		this.tag(EnvironmentalBiomeTags.HAS_FLOWER_FOREST_VEGETATION).add(Biome.FLOWER_FOREST);
		this.tag(EnvironmentalBiomeTags.HAS_PLAINS_VEGETATION).addTag(Tags.Biomes.IS_PLAINS);
		this.tag(EnvironmentalBiomeTags.HAS_SAVANNA_VEGETATION).addTag(BiomeTags.IS_SAVANNA);
		this.tag(EnvironmentalBiomeTags.HAS_JUNGLE_VEGETATION).addTag(BiomeTags.IS_JUNGLE);
		this.tag(EnvironmentalBiomeTags.HAS_SWAMP_VEGETATION).addTag(Tags.Biomes.IS_SWAMP);
		
	}
}
