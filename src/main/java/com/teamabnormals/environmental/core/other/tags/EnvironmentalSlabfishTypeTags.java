package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.environmental.common.slabfish.SlabfishVariant;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.tags.TagKey;

public class EnvironmentalSlabfishTypeTags {
	public static final TagKey<SlabfishVariant> NOT_SOLD_BY_WANDERING_TRADER = slabfishTypeTag("not_sold_by_wandering_trader");
	public static final TagKey<SlabfishVariant> TRANSLUCENT = slabfishTypeTag("translucent");

	public static final TagKey<SlabfishVariant> COMMON = slabfishTypeTag("rarity/common");
	public static final TagKey<SlabfishVariant> UNCOMMON = slabfishTypeTag("rarity/uncommon");
	public static final TagKey<SlabfishVariant> RARE = slabfishTypeTag("rarity/rare");
	public static final TagKey<SlabfishVariant> EPIC = slabfishTypeTag("rarity/epic");
	public static final TagKey<SlabfishVariant> LEGENDARY = slabfishTypeTag("rarity/legendary");

	public static TagKey<SlabfishVariant> slabfishTypeTag(String name) {
		return TagKey.create(EnvironmentalRegistries.SLABFISH_TYPE, Environmental.location(name));
	}
}