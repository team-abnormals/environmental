package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class EnvironmentalTags {
    public static class Items {
        public static final ITag.INamedTag<Item> SUSHI                  = ItemTags.makeWrapperTag(Environmental.MODID + ":sushi");

        public static final ITag.INamedTag<Item> DUCK_BREEDING_ITEMS    = ItemTags.makeWrapperTag(Environmental.MODID + ":duck_breeding_items");
        
        public static final ITag.INamedTag<Item> SLABFISH_FOODS             = ItemTags.makeWrapperTag(Environmental.MODID + ":slabfish_foods");
        public static final ITag.INamedTag<Item> SLABFISH_BREEDING_ITEMS    = ItemTags.makeWrapperTag(Environmental.MODID + ":slabfish_breeding_items");
        public static final ITag.INamedTag<Item> SLABFISH_TEMPTATION_ITEMS  = ItemTags.makeWrapperTag(Environmental.MODID + ":slabfish_temptation_items");
    }
    
    public static class Blocks {
        public static final ITag.INamedTag<Block> CATTAIL_PLANTABLE_ON = BlockTags.makeWrapperTag(Environmental.MODID + ":cattail_plantable_on");
    }
}