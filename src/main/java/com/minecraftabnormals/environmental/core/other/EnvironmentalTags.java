package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class EnvironmentalTags {
    public static final ITag.INamedTag<Block> CATTAIL_PLANTABLE_ON = BlockTags.makeWrapperTag(Environmental.MODID + ":cattail_plantable_on");
    public static final ITag.INamedTag<Item> SUSHI = ItemTags.makeWrapperTag(Environmental.MODID + ":sushi");
}