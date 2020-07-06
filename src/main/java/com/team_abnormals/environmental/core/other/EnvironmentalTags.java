package com.team_abnormals.environmental.core.other;

import com.team_abnormals.environmental.core.Environmental;

import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class EnvironmentalTags {
	public static final Tag<Block> CATTAIL_PLANTABLE_ON = new BlockTags.Wrapper(new ResourceLocation(Environmental.MODID, "cattail_plantable_on"));
	public static final Tag<Item> SUSHI = new ItemTags.Wrapper(new ResourceLocation(Environmental.MODID, "sushi"));
	public static final Tag<Fluid> MUD = new FluidTags.Wrapper(new ResourceLocation(Environmental.MODID, "mud"));
}
