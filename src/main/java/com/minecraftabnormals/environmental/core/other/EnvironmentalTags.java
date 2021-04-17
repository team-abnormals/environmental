package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class EnvironmentalTags {
	public static class Blocks {
		public static final ITag.INamedTag<Block> GRASS_LIKE = createWrapperBlockTag("grass_like");
	}

	public static class Items {
		public static final ITag.INamedTag<Item> DUCK_FOOD = createWrapperItemTag("duck_food");
		public static final ITag.INamedTag<Item> DEER_FOOD = createWrapperItemTag("deer_food");
		public static final ITag.INamedTag<Item> DEER_TEMPT_ITEMS = createWrapperItemTag("deer_tempt_items");

		public static final ITag.INamedTag<Item> SLABFISH_SNACKS = createWrapperItemTag("slabfish_snacks");
		public static final ITag.INamedTag<Item> SLABFISH_FOOD = createWrapperItemTag("slabfish_food");
		public static final ITag.INamedTag<Item> SLABFISH_TEMPT_ITEMS = createWrapperItemTag("slabfish_tempt_items");
	}

	public static class EntityTypes {
		public static final ITag.INamedTag<EntityType<?>> UNAFFECTED_BY_SERENITY = createWrapperEntityTag("unaffected_by_serenity");
	}

	private static ITag.INamedTag<Block> createWrapperBlockTag(String tagName) {
		return BlockTags.createOptional(new ResourceLocation(Environmental.MOD_ID, tagName));
	}

	private static ITag.INamedTag<Item> createWrapperItemTag(String tagName) {
		return ItemTags.createOptional(new ResourceLocation(Environmental.MOD_ID, tagName));
	}

	private static ITag.INamedTag<EntityType<?>> createWrapperEntityTag(String tagName) {
		return EntityTypeTags.createOptional(new ResourceLocation(Environmental.MOD_ID, tagName));
	}
}