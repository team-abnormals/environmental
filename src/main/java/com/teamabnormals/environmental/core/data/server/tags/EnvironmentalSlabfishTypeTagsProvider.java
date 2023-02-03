package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.environmental.common.slabfish.BuiltInSlabfishTypes;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalSlabfishTypeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishTypes;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EnvironmentalSlabfishTypeTagsProvider extends TagsProvider<SlabfishType> implements BuiltInSlabfishTypes {

	public EnvironmentalSlabfishTypeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, BuiltinRegistries.ACCESS.registryOrThrow(EnvironmentalSlabfishTypes.SLABFISH_TYPES_REGISTRY), Environmental.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.tag(EnvironmentalSlabfishTypeTags.TRANSLUCENT).add(GHOST);
		this.tag(EnvironmentalSlabfishTypeTags.UNTRADABLE).addTag(EnvironmentalSlabfishTypeTags.SECRET);
		this.tag(EnvironmentalSlabfishTypeTags.SECRET).add(BAGEL, BMO, CAMERON, EVE, GORE, JACKSON, MINION, OCELOT, RENREN, SMELLY, SNAKE_BLOCK);

		// Dunes, Rainforest, Maple, Poise
		this.tag(EnvironmentalSlabfishTypeTags.COMMON).add(SWAMP, MARSH);
		this.tag(EnvironmentalSlabfishTypeTags.UNCOMMON).add(BEACH, DESERT, FOREST, HILL, OCEAN, PLAINS, RIVER, SAVANNA, TAIGA).addTag(EnvironmentalSlabfishTypeTags.SECRET);
		this.tag(EnvironmentalSlabfishTypeTags.RARE).add(BLOSSOM, CAVE, DARK_FOREST, FLOWER_FOREST, JUNGLE, NETHER, WARM_OCEAN, FROZEN_OCEAN, BADLANDS, BAMBOO);
		this.tag(EnvironmentalSlabfishTypeTags.EPIC).add(CHORUS, CRIMSON, DROWNED, END, MUSHROOM, NIGHTMARE, SKELETON, SOUL_SAND_VALLEY, TOTEM, WARPED);
		this.tag(EnvironmentalSlabfishTypeTags.LEGENDARY).add(BASALT_DELTAS, BROWN_MUSHROOM, ICE_SPIKES, SKY, WITHER, STRAY, GHOST);
	}

	@Override
	public String getName() {
		return "Slabfish Type Tags";
	}
}