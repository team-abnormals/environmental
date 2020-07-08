package com.team_abnormals.environmental.core.other;

import com.team_abnormals.environmental.core.Environmental;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class EnvironmentalTags {
	public static final ITag.INamedTag<Block> CATTAIL_PLANTABLE_ON 	= BlockTags.makeWrapperTag(Environmental.MODID + ":cattail_plantable_on");
	public static final ITag.INamedTag<Item> SUSHI 					= ItemTags.makeWrapperTag(Environmental.MODID + ":sushi");
	public static final ITag.INamedTag<Fluid> MUD 					= FluidTags.makeWrapperTag(Environmental.MODID + ":mud");
}
