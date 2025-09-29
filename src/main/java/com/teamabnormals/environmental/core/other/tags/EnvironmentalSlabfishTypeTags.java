package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class EnvironmentalSlabfishTypeTags {
	public static final TagKey<SlabfishType> NOT_SOLD_BY_WANDERING_TRADER = slabfishTypeTag("not_sold_by_wandering_trader");
	public static final TagKey<SlabfishType> TRANSLUCENT = slabfishTypeTag("translucent");

	public static final TagKey<SlabfishType> COMMON = slabfishTypeTag("rarity/common");
	public static final TagKey<SlabfishType> UNCOMMON = slabfishTypeTag("rarity/uncommon");
	public static final TagKey<SlabfishType> RARE = slabfishTypeTag("rarity/rare");
	public static final TagKey<SlabfishType> EPIC = slabfishTypeTag("rarity/epic");
	public static final TagKey<SlabfishType> LEGENDARY = slabfishTypeTag("rarity/legendary");

	public static TagKey<SlabfishType> slabfishTypeTag(String name) {
		return TagKey.create(EnvironmentalRegistries.SLABFISH_TYPE, Environmental.location(name));
	}
}