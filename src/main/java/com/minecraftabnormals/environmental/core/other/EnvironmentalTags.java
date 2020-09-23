package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class EnvironmentalTags {
    public static class Items {
        public static final ITag.INamedTag<Item> SUSHI = createWrapperTag("sushi");

        public static final ITag.INamedTag<Item> DUCK_BREEDING_ITEMS    = createWrapperTag("duck_breeding_items");
        public static final ITag.INamedTag<Item> DEER_BREEDING_ITEMS    = createWrapperTag("deer_breeding_items");
        public static final ITag.INamedTag<Item> DEER_TEMPTATION_ITEMS    = createWrapperTag("deer_temptation_items");

        public static final ITag.INamedTag<Item> SLABFISH_FOODS             = createWrapperTag("slabfish_foods");
        public static final ITag.INamedTag<Item> SLABFISH_BREEDING_ITEMS    = createWrapperTag("slabfish_breeding_items");
        public static final ITag.INamedTag<Item> SLABFISH_TEMPTATION_ITEMS  = createWrapperTag("slabfish_temptation_items");
    }
    
    private static ITag.INamedTag<Item> createWrapperTag(String tagName) {
    	return ItemTags.makeWrapperTag(Environmental.MODID + ":" + tagName);
    }
}