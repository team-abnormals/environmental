package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.blueprint.core.other.tags.BlueprintBiomeTags;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EnvironmentalBiomeTagsProvider extends BiomeTagsProvider {

	public EnvironmentalBiomeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Environmental.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.tag(EnvironmentalBiomes.MARSH.get(),
				BiomeTags.IS_OVERWORLD,
				BiomeTags.HAS_MINESHAFT, BiomeTags.STRONGHOLD_BIASED_TO, BiomeTags.HAS_RUINED_PORTAL_SWAMP, BiomeTags.HAS_SWAMP_HUT,
				BiomeTags.HAS_CLOSER_WATER_FOG, BiomeTags.WATER_ON_MAP_OUTLINES,
				Tags.Biomes.IS_WET_OVERWORLD, Tags.Biomes.IS_SWAMP,
				EnvironmentalBiomeTags.ONLY_ALLOWS_MUDDY_RABBITS,
				EnvironmentalBiomeTags.HAS_MUD_DISK
		);

		this.tag(EnvironmentalBiomes.BLOSSOM_WOODS.get(),
				BiomeTags.IS_OVERWORLD, BiomeTags.IS_FOREST,
				BiomeTags.HAS_MINESHAFT, BiomeTags.STRONGHOLD_BIASED_TO,
				Tags.Biomes.IS_DENSE_OVERWORLD
		);

		this.tag(EnvironmentalBiomes.BLOSSOM_VALLEYS.get(),
				BiomeTags.IS_OVERWORLD, BiomeTags.IS_FOREST,
				BiomeTags.HAS_MINESHAFT, BiomeTags.STRONGHOLD_BIASED_TO,
				BlueprintBiomeTags.IS_GRASSLAND, Tags.Biomes.IS_RARE, Tags.Biomes.IS_PLAINS
		);

		this.tag(EnvironmentalBiomes.PINE_BARRENS.get(),
				BiomeTags.IS_OVERWORLD, BiomeTags.IS_TAIGA,
				BiomeTags.HAS_MINESHAFT, BiomeTags.STRONGHOLD_BIASED_TO, BiomeTags.HAS_RUINED_PORTAL_STANDARD,
				Tags.Biomes.IS_COLD_OVERWORLD, Tags.Biomes.IS_CONIFEROUS,
				EnvironmentalBiomeTags.HAS_PINE_CABIN
		);

		this.tag(EnvironmentalBiomeTags.HAS_SLABFISH).addTag(Tags.Biomes.IS_SWAMP);
		this.tag(EnvironmentalBiomeTags.HAS_DUCK).addTag(Tags.Biomes.IS_SWAMP);
		this.tag(EnvironmentalBiomeTags.HAS_DEER).addTag(BiomeTags.IS_FOREST);
		this.tag(EnvironmentalBiomeTags.WITHOUT_DEER).add(Biomes.GROVE).addOptional(new ResourceLocation("atmospheric", "aspen_parkland")).addOptional(new ResourceLocation("atmospheric", "grimwoods"));
		this.tag(EnvironmentalBiomeTags.HAS_REINDEER).addTag(BlueprintBiomeTags.IS_ICY).addTag(BiomeTags.IS_TAIGA).add(Biomes.GROVE);
		this.tag(EnvironmentalBiomeTags.HAS_YAK).add(Biomes.STONY_PEAKS).addTag(BiomeTags.IS_HILL);
		this.tag(EnvironmentalBiomeTags.HAS_ZEBRA).addTag(BiomeTags.IS_SAVANNA);

		this.tag(EnvironmentalBiomeTags.HAS_SHEEP).addTag(BlueprintBiomeTags.IS_GRASSLAND).addTag(BiomeTags.IS_SAVANNA);
		this.tag(EnvironmentalBiomeTags.HAS_COW).addTag(BlueprintBiomeTags.IS_GRASSLAND).addTag(BiomeTags.IS_SAVANNA);
		this.tag(EnvironmentalBiomeTags.HAS_PIG).addTag(BiomeTags.IS_FOREST).addTag(Tags.Biomes.IS_SWAMP);
		this.tag(EnvironmentalBiomeTags.HAS_CHICKEN).addTag(BiomeTags.IS_FOREST).addTag(BiomeTags.IS_TAIGA).addTag(BiomeTags.IS_JUNGLE);

		this.tag(EnvironmentalBiomeTags.HAS_CATTAILS).add(Biomes.RIVER, Biomes.SWAMP, Biomes.MANGROVE_SWAMP);
		this.tag(EnvironmentalBiomeTags.HAS_BLUEBELL).add(Biomes.DARK_FOREST);
		this.tag(EnvironmentalBiomeTags.HAS_VIOLET).addTag(BiomeTags.IS_TAIGA);
		this.tag(EnvironmentalBiomeTags.HAS_TASSELFLOWER).addTag(BiomeTags.IS_SAVANNA);
		this.tag(EnvironmentalBiomeTags.HAS_BIRD_OF_PARADISE).addTag(BiomeTags.IS_JUNGLE).addOptionalTag(new ResourceLocation("atmospheric", "is_rainforest"));
		this.tag(EnvironmentalBiomeTags.HAS_HIBISCUS).add(Biomes.JUNGLE);

		this.tag(EnvironmentalBiomeTags.HAS_MUD_DISK).add(Biomes.SWAMP);
	}

	@SafeVarargs
	private void tag(Biome biome, TagKey<Biome>... tags) {
		for (TagKey<Biome> key : tags) {
			tag(key).add(biome);
		}
	}
}