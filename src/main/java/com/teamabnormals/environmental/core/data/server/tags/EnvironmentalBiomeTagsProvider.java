package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.blueprint.core.other.tags.BlueprintBiomeTags;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalConstants;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.violetmoon.zeta.multiloader.Env;

import java.util.concurrent.CompletableFuture;

public class EnvironmentalBiomeTagsProvider extends BiomeTagsProvider {

	public EnvironmentalBiomeTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Environmental.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.tag(EnvironmentalBiomes.MARSH,
				BiomeTags.IS_OVERWORLD,
				BiomeTags.HAS_MINESHAFT, BiomeTags.STRONGHOLD_BIASED_TO, BiomeTags.HAS_RUINED_PORTAL_SWAMP, BiomeTags.HAS_SWAMP_HUT,
				BiomeTags.HAS_CLOSER_WATER_FOG, BiomeTags.WATER_ON_MAP_OUTLINES,
				Tags.Biomes.IS_WET_OVERWORLD, Tags.Biomes.IS_SWAMP,
				EnvironmentalBiomeTags.SPAWNS_MUDDY_RABBITS,
				EnvironmentalBiomeTags.HAS_MUD_DISK
		);

		this.tag(EnvironmentalBiomes.BLOSSOM_WOODS,
				BiomeTags.IS_OVERWORLD, BiomeTags.IS_FOREST,
				BiomeTags.HAS_MINESHAFT, BiomeTags.STRONGHOLD_BIASED_TO,
				Tags.Biomes.IS_DENSE_OVERWORLD,
				EnvironmentalBiomeTags.SPAWNS_CHESTNUT_DEER
		);

		this.tag(EnvironmentalBiomes.BLOSSOM_VALLEYS,
				BiomeTags.IS_OVERWORLD, BiomeTags.IS_FOREST,
				BiomeTags.HAS_MINESHAFT, BiomeTags.STRONGHOLD_BIASED_TO,
				BlueprintBiomeTags.IS_GRASSLAND, Tags.Biomes.IS_RARE, Tags.Biomes.IS_PLAINS,
				EnvironmentalBiomeTags.SPAWNS_CHESTNUT_DEER
		);

		this.tag(EnvironmentalBiomes.PINE_BARRENS,
				BiomeTags.IS_OVERWORLD, BiomeTags.IS_TAIGA,
				BiomeTags.HAS_MINESHAFT, BiomeTags.STRONGHOLD_BIASED_TO, BiomeTags.HAS_TRAIL_RUINS,
				Tags.Biomes.IS_COLD_OVERWORLD, Tags.Biomes.IS_CONIFEROUS,
				EnvironmentalBiomeTags.IS_PINE_BARRENS, EnvironmentalBiomeTags.HAS_LOG_CABIN
		);

		this.tag(EnvironmentalBiomes.SNOWY_PINE_BARRENS,
				BiomeTags.IS_OVERWORLD, BiomeTags.IS_TAIGA,
				BiomeTags.HAS_MINESHAFT, BiomeTags.STRONGHOLD_BIASED_TO, BiomeTags.HAS_TRAIL_RUINS,
				BiomeTags.SPAWNS_COLD_VARIANT_FROGS,
				Tags.Biomes.IS_COLD_OVERWORLD, Tags.Biomes.IS_SNOWY, Tags.Biomes.IS_CONIFEROUS,
				EnvironmentalBiomeTags.IS_PINE_BARRENS, EnvironmentalBiomeTags.HAS_LOG_CABIN
		);

		this.tag(EnvironmentalBiomes.OLD_GROWTH_PINE_BARRENS,
				BiomeTags.IS_OVERWORLD, BiomeTags.IS_TAIGA,
				BiomeTags.HAS_MINESHAFT, BiomeTags.STRONGHOLD_BIASED_TO, BiomeTags.HAS_TRAIL_RUINS,
				Tags.Biomes.IS_COLD_OVERWORLD, Tags.Biomes.IS_CONIFEROUS, Tags.Biomes.IS_DENSE_OVERWORLD, Tags.Biomes.IS_RARE,
				EnvironmentalBiomeTags.IS_PINE_BARRENS, EnvironmentalBiomeTags.HAS_LOG_CABIN
		);

		this.tag(EnvironmentalBiomes.SNOWY_OLD_GROWTH_PINE_BARRENS,
				BiomeTags.IS_OVERWORLD, BiomeTags.IS_TAIGA,
				BiomeTags.HAS_MINESHAFT, BiomeTags.STRONGHOLD_BIASED_TO, BiomeTags.HAS_TRAIL_RUINS,
				BiomeTags.SPAWNS_COLD_VARIANT_FROGS,
				Tags.Biomes.IS_COLD_OVERWORLD, Tags.Biomes.IS_SNOWY, Tags.Biomes.IS_CONIFEROUS, Tags.Biomes.IS_DENSE_OVERWORLD, Tags.Biomes.IS_RARE,
				EnvironmentalBiomeTags.IS_PINE_BARRENS, EnvironmentalBiomeTags.HAS_LOG_CABIN
		);

		this.tag(EnvironmentalBiomes.PINE_SLOPES,
				BiomeTags.IS_OVERWORLD, BiomeTags.IS_MOUNTAIN,
				BiomeTags.HAS_MINESHAFT, BiomeTags.STRONGHOLD_BIASED_TO,
				Tags.Biomes.IS_COLD_OVERWORLD, Tags.Biomes.IS_CONIFEROUS, Tags.Biomes.IS_SLOPE,
				EnvironmentalBiomeTags.SPAWNS_GRAY_RABBITS
		);

		this.tag(EnvironmentalBiomeTags.HAS_SLABFISH).addTag(Tags.Biomes.IS_SWAMP);
		this.tag(EnvironmentalBiomeTags.HAS_DUCK).addTag(Tags.Biomes.IS_SWAMP);
		this.tag(EnvironmentalBiomeTags.HAS_DEER).addTag(BiomeTags.IS_FOREST);
		this.tag(EnvironmentalBiomeTags.WITHOUT_DEER).add(Biomes.GROVE).addOptional(EnvironmentalConstants.ASPEN_PARKLAND).addOptional(EnvironmentalConstants.GRIMWOODS);
		this.tag(EnvironmentalBiomeTags.HAS_MUDDY_PIG).addTag(Tags.Biomes.IS_SWAMP);
		this.tag(EnvironmentalBiomeTags.HAS_REINDEER).addTag(BlueprintBiomeTags.IS_ICY).addTag(BiomeTags.IS_TAIGA).add(Biomes.GROVE);
		this.tag(EnvironmentalBiomeTags.HAS_TAPIR).add(Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE);
		this.tag(EnvironmentalBiomeTags.HAS_YAK).add(Biomes.STONY_PEAKS).addTag(BiomeTags.IS_HILL);
		this.tag(EnvironmentalBiomeTags.HAS_ZEBRA).addTag(BiomeTags.IS_SAVANNA);

		this.tag(EnvironmentalBiomeTags.HAS_SHEEP).addTag(BlueprintBiomeTags.IS_GRASSLAND).addTag(BiomeTags.IS_SAVANNA);
		this.tag(EnvironmentalBiomeTags.HAS_COW).addTag(BlueprintBiomeTags.IS_GRASSLAND).addTag(BiomeTags.IS_SAVANNA);
		this.tag(EnvironmentalBiomeTags.HAS_PIG).addTag(BiomeTags.IS_FOREST).addTag(Tags.Biomes.IS_SWAMP);
		this.tag(EnvironmentalBiomeTags.HAS_CHICKEN).addTag(BiomeTags.IS_FOREST).addTag(BiomeTags.IS_TAIGA).addTag(BiomeTags.IS_JUNGLE);

		this.tag(EnvironmentalBiomeTags.HAS_CATTAILS).add(Biomes.RIVER, Biomes.SWAMP, Biomes.MANGROVE_SWAMP);
		this.tag(EnvironmentalBiomeTags.HAS_CUP_LICHEN).addTag(BiomeTags.IS_TAIGA);
		this.tag(EnvironmentalBiomeTags.HAS_SPARSE_DWARF_SPRUCE).addTag(BiomeTags.IS_TAIGA);
		this.tag(EnvironmentalBiomeTags.HAS_BLUEBELL).add(Biomes.DARK_FOREST);
		this.tag(EnvironmentalBiomeTags.HAS_VIOLET).addTag(BiomeTags.IS_TAIGA);
		this.tag(EnvironmentalBiomeTags.HAS_TASSELFLOWER).addTag(BiomeTags.IS_SAVANNA);
		this.tag(EnvironmentalBiomeTags.HAS_BIRD_OF_PARADISE).addTag(BiomeTags.IS_JUNGLE).addTag(EnvironmentalBiomeTags.IS_RAINFOREST);
		this.tag(EnvironmentalBiomeTags.HAS_HIBISCUS).add(Biomes.JUNGLE);

		this.tag(EnvironmentalBiomeTags.HAS_MUD_DISK).add(Biomes.SWAMP);

		this.tag(EnvironmentalBiomeTags.SPAWNS_GRAY_DEER).add(Biomes.DARK_FOREST);

		this.tag(EnvironmentalBiomeTags.IS_RAINFOREST);
		this.tag(EnvironmentalBiomeTags.IS_DUNES);
	}

	@SafeVarargs
	private void tag(ResourceKey<Biome> biome, TagKey<Biome>... tags) {
		for (TagKey<Biome> key : tags) {
			tag(key).add(biome);
		}
	}
}