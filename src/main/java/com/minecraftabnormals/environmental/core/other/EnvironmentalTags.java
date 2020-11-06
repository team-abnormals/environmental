package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class EnvironmentalTags {
    public static class Items {
        public static final ITag.INamedTag<Item> SUSHI = createWrapperItemTag("sushi");

        public static final ITag.INamedTag<Item> DUCK_BREEDING_ITEMS    = createWrapperItemTag("duck_breeding_items");
        public static final ITag.INamedTag<Item> DEER_BREEDING_ITEMS    = createWrapperItemTag("deer_breeding_items");
        public static final ITag.INamedTag<Item> DEER_TEMPTATION_ITEMS    = createWrapperItemTag("deer_temptation_items");

        public static final ITag.INamedTag<Item> SLABFISH_FOODS             = createWrapperItemTag("slabfish_foods");
        public static final ITag.INamedTag<Item> SLABFISH_BREEDING_ITEMS    = createWrapperItemTag("slabfish_breeding_items");
        public static final ITag.INamedTag<Item> SLABFISH_TEMPTATION_ITEMS  = createWrapperItemTag("slabfish_temptation_items");
    }
    
    public static class EntityTypes {
    
        public static final ITag.INamedTag<EntityType<?>> SERENITY_WHITELIST = createWrapperEntityTag("serenity_whitelist");
    }
    
    private static ITag.INamedTag<Item> createWrapperItemTag(String tagName) {
    	return ItemTags.makeWrapperTag(Environmental.MODID + ":" + tagName);
    }
    
    private static ITag.INamedTag<EntityType<?>> createWrapperEntityTag(String tagName) {
        return EntityTypeTags.func_232896_a_(Environmental.MODID + ":" + tagName);
    }
}