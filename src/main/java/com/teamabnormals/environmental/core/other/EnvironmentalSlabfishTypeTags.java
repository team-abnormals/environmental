package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

//TODO: Seperate Tag classes & generate
public class EnvironmentalTags {

		public static final TagKey<SlabfishType> TRADEABLE = slabfishTag("tradeable");
		public static final TagKey<SlabfishType> TRANSLUCENT = slabfishTag("translucent");

		public static final TagKey<SlabfishType> COMMON = slabfishTag("rarity/common");
		public static final TagKey<SlabfishType> UNCOMMON = slabfishTag("rarity/uncommon");
		public static final TagKey<SlabfishType> RARE = slabfishTag("rarity/rare");
		public static final TagKey<SlabfishType> EPIC = slabfishTag("rarity/epic");
		public static final TagKey<SlabfishType> LEGENDARY = slabfishTag("rarity/legendary");
	}

	public static TagKey<SlabfishType> slabfishTag(String name) {
		return TagKey.create(EnvironmentalSlabfishTypes.SLABFISH_TYPES_REGISTRY, new ResourceLocation(Environmental.MOD_ID, name));
	}
}