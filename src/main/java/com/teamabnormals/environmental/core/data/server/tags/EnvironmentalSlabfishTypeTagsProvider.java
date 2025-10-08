package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.environmental.common.slabfish.SlabfishVariant;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalSlabfishVariantTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.environmental.core.registry.datapack.slabfish.EnvironmentalSlabfishVariants.*;

public class EnvironmentalSlabfishTypeTagsProvider extends TagsProvider<SlabfishVariant> {

	public EnvironmentalSlabfishTypeTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, EnvironmentalRegistries.SLABFISH_VARIANT, provider, Environmental.MOD_ID, helper);
	}

	@Override
	public void addTags(HolderLookup.Provider provider) {
		this.tag(EnvironmentalSlabfishVariantTags.TRANSLUCENT).add(GHOST);
		this.tag(EnvironmentalSlabfishVariantTags.NOT_SOLD_BY_WANDERING_TRADER).add(SNAKE_BLOCK);

		this.tag(EnvironmentalSlabfishVariantTags.COMMON).add(SWAMP, MARSH, MANGROVE);
		this.tag(EnvironmentalSlabfishVariantTags.UNCOMMON).add(
				PLAINS, SAVANNA, DESERT, FOREST, HILL, TAIGA, SNOWY,
				RIVER, BEACH, OCEAN, CAVE,
				SNAKE_BLOCK
		);
		this.tag(EnvironmentalSlabfishVariantTags.RARE).add(
				DARK_FOREST, FLOWER_FOREST, JUNGLE, BADLANDS, MOUNTAIN, CHERRY_GROVE,
				BLOSSOM, PINE, MAPLE,
				RAINFOREST, ASPEN, LAUREL, DUNES, SCRUBLAND,
				WARM_OCEAN, FROZEN_OCEAN,
				DEEPSLATE, LUSH_CAVES, DRIPSTONE_CAVES, NETHER
		);
		this.tag(EnvironmentalSlabfishVariantTags.EPIC).add(
				MUSHROOM, DEEP_DARK, ICE_SPIKES, BAMBOO, KOUSA, SPINY_THICKET,
				SKELETON, DROWNED, NIGHTMARE, TOTEM, GOLEM,
				CRIMSON, WARPED, SOUL_SAND_VALLEY, BASALT_DELTAS,
				END, CHORUS, POISE
		);
		this.tag(EnvironmentalSlabfishVariantTags.LEGENDARY).add(BROWN_MUSHROOM, SKY, WITHER, STRAY, GHOST);
	}
}