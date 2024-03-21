package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class EnvironmentalBlockTags {
	public static final TagKey<Block> GRASS_LIKE = blockTag("grass_like");
	public static final TagKey<Block> DEER_SPAWNABLE_ON = blockTag("deer_spawnable_on");
	public static final TagKey<Block> WATER_ANIMALS_SPAWNABLE_ON = blockTag("water_animals_spawnable_on");
	public static final TagKey<Block> CUP_LICHEN_PLANTABLE_ON = blockTag("cup_lichen_plantable_on");
	public static final TagKey<Block> CACTUS_BOBBLE_PLANTABLE_ON = blockTag("cactus_bobble_plantable_on");
	public static final TagKey<Block> CATTAIL_PLANTABLE_ON = blockTag("cattail_plantable_on");
	public static final TagKey<Block> MUD_REPLACEABLES = blockTag("mud_replaceables");

	public static final TagKey<Block> HIBISCUSES = blockTag("hibiscuses");
	public static final TagKey<Block> WALL_HIBISCUSES = blockTag("wall_hibiscuses");

	public static final TagKey<Block> WILLOW_LOGS = blockTag("willow_logs");
	public static final TagKey<Block> PINE_LOGS = blockTag("pine_logs");
	public static final TagKey<Block> WISTERIA_LOGS = blockTag("wisteria_logs");
	public static final TagKey<Block> CHERRY_LOGS = blockTag("cherry_logs");

	private static TagKey<Block> blockTag(String tagName) {
		return TagUtil.blockTag(Environmental.MOD_ID, tagName);
	}
}