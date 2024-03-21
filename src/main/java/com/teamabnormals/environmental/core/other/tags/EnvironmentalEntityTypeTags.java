package com.teamabnormals.environmental.core.other.tags;

import com.teamabnormals.blueprint.core.util.TagUtil;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class EnvironmentalEntityTypeTags {
	public static final TagKey<EntityType<?>> UNAFFECTED_BY_SERENITY = entityTypeTag("unaffected_by_serenity");
	public static final TagKey<EntityType<?>> DEER = entityTypeTag("deer");
	public static final TagKey<EntityType<?>> SCARES_DEER = entityTypeTag("scares_deer");
	public static final TagKey<EntityType<?>> SCARES_TRUSTING_DEER = entityTypeTag("scares_trusting_deer");
	public static final TagKey<EntityType<?>> ZEBRAS_DONT_KICK = entityTypeTag("zebras_dont_kick");

	private static TagKey<EntityType<?>> entityTypeTag(String tagName) {
		return TagUtil.entityTypeTag(Environmental.MOD_ID, tagName);
	}
}