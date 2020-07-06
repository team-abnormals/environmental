package com.team_abnormals.environmental.core.other;

import java.util.Arrays;

import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;
import com.team_abnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.abnormals_core.core.utils.DataUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;

public class EnvironmentalData {
	public static void registerCompostables() {
		DataUtils.registerCompostable(0.30F, EnvironmentalBlocks.WILLOW_LEAVES.get());
		DataUtils.registerCompostable(0.30F, EnvironmentalBlocks.WILLOW_SAPLING.get());
		DataUtils.registerCompostable(0.30F, EnvironmentalBlocks.WILLOW_LEAF_CARPET.get());
		
		DataUtils.registerCompostable(0.65F, EnvironmentalBlocks.DUCKWEED.get());
		DataUtils.registerCompostable(0.30F, EnvironmentalBlocks.CATTAIL.get());
		DataUtils.registerCompostable(0.65F, EnvironmentalBlocks.TALL_CATTAIL.get());
		
		DataUtils.registerCompostable(0.30F, EnvironmentalItems.RICE.get());
		DataUtils.registerCompostable(0.30F, EnvironmentalItems.CATTAIL_SEEDS.get());
		
		DataUtils.registerCompostable(0.65F, EnvironmentalBlocks.CATTAIL_THATCH.get());
		DataUtils.registerCompostable(0.65F, EnvironmentalBlocks.CATTAIL_THATCH_SLAB.get());
		DataUtils.registerCompostable(0.65F, EnvironmentalBlocks.CATTAIL_THATCH_STAIRS.get());
		DataUtils.registerCompostable(0.65F, EnvironmentalBlocks.CATTAIL_THATCH_VERTICAL_SLAB.get());
		
		DataUtils.registerCompostable(0.85F, EnvironmentalBlocks.DUCKWEED_THATCH.get());
		DataUtils.registerCompostable(0.85F, EnvironmentalBlocks.DUCKWEED_THATCH_SLAB.get());
		DataUtils.registerCompostable(0.85F, EnvironmentalBlocks.DUCKWEED_THATCH_STAIRS.get());
		DataUtils.registerCompostable(0.85F, EnvironmentalBlocks.DUCKWEED_THATCH_VERTICAL_SLAB.get());
		
        DataUtils.registerCompostable(0.75F, EnvironmentalBlocks.BLUE_DELPHINIUM.get());
        DataUtils.registerCompostable(0.75F, EnvironmentalBlocks.WHITE_DELPHINIUM.get());
        DataUtils.registerCompostable(0.75F, EnvironmentalBlocks.PINK_DELPHINIUM.get());
        DataUtils.registerCompostable(0.75F, EnvironmentalBlocks.PURPLE_DELPHINIUM.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.BLUE_WISTERIA_LEAF_CARPET.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.WHITE_WISTERIA_LEAF_CARPET.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.PINK_WISTERIA_LEAF_CARPET.get());
        DataUtils.registerCompostable(0.35F, EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_CARPET.get());
	}
	
	public static void registerFlammables() {
		DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_LEAVES.get(), 30, 60);
		DataUtils.registerFlammable(EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(), 30, 60);
		DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_LOG.get(), 5, 5);
		DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_WOOD.get(), 5, 5);
		DataUtils.registerFlammable(EnvironmentalBlocks.STRIPPED_WILLOW_LOG.get(), 5, 5);
		DataUtils.registerFlammable(EnvironmentalBlocks.STRIPPED_WILLOW_WOOD.get(), 5, 5);
		DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_PLANKS.get(), 5, 20);
		DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_SLAB.get(), 5, 20);
		DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_STAIRS.get(), 5, 20);
		DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_FENCE.get(), 5, 20);
		DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_FENCE_GATE.get(), 5, 20);
		DataUtils.registerFlammable(EnvironmentalBlocks.VERTICAL_WILLOW_PLANKS.get(), 5, 20);
		DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_LEAF_CARPET.get(), 30, 60);
		DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_VERTICAL_SLAB.get(), 5, 20);
		DataUtils.registerFlammable(EnvironmentalBlocks.WILLOW_BOOKSHELF.get(), 5, 20);
		DataUtils.registerFlammable(EnvironmentalBlocks.GIANT_TALL_GRASS.get(), 60, 100);
		
		DataUtils.registerFlammable(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_LOG.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_WOOD.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.STRIPPED_WISTERIA_LOG.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.STRIPPED_WISTERIA_WOOD.get(), 5, 5);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_PLANKS.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_SLAB.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_STAIRS.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_FENCE.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_FENCE_GATE.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.BLUE_DELPHINIUM.get(), 60, 100);
        DataUtils.registerFlammable(EnvironmentalBlocks.WHITE_DELPHINIUM.get(), 60, 100);
        DataUtils.registerFlammable(EnvironmentalBlocks.PINK_DELPHINIUM.get(), 60, 100);
        DataUtils.registerFlammable(EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), 60, 100);
        DataUtils.registerFlammable(EnvironmentalBlocks.VERTICAL_WISTERIA_PLANKS.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_CARPET.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_CARPET.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.PINK_WISTERIA_LEAF_CARPET.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_CARPET.get(), 30, 60);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_VERTICAL_SLAB.get(), 5, 20);
        DataUtils.registerFlammable(EnvironmentalBlocks.WISTERIA_BOOKSHELF.get(), 5, 20);
	}
	
	public static void setRenderLayers() {
		boolean fancy = Minecraft.isFancyGraphicsEnabled();

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WILLOW_DOOR.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WILLOW_TRAPDOOR.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WILLOW_LADDER.get(),RenderType.getCutout());
		
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WILLOW_LEAVES.get(),RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(),RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WILLOW_LEAF_CARPET.get(),RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WILLOW_SAPLING.get(),RenderType.getCutout());
		
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.GIANT_TALL_GRASS.get(),RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CATTAIL_SPROUTS.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CATTAIL.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.TALL_CATTAIL.get(),RenderType.getCutout());
		
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_SLAB.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_STAIRS.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_VERTICAL_SLAB.get(),RenderType.getCutout());
		
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_SLAB.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_STAIRS.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_VERTICAL_SLAB.get(),RenderType.getCutout());
		
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.RICE.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.TALL_RICE.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.DUCKWEED.get(),RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_CATTAIL.get(),RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_WILLOW_SAPLING.get(),RenderType.getCutout());
		    	
    	RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WISTERIA_DOOR.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WISTERIA_TRAPDOOR.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WISTERIA_LADDER.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), fancy ? RenderType.getCutoutMipped() : RenderType.getSolid());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), fancy ? RenderType.getCutoutMipped() : RenderType.getSolid());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), fancy ? RenderType.getCutoutMipped() : RenderType.getSolid());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), fancy ? RenderType.getCutoutMipped() : RenderType.getSolid());
		
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WHITE_DELPHINIUM.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.BLUE_DELPHINIUM.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PINK_DELPHINIUM.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get(), fancy ? RenderType.getCutoutMipped() : RenderType.getSolid());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get(), fancy ? RenderType.getCutoutMipped() : RenderType.getSolid());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get(), fancy ? RenderType.getCutoutMipped() : RenderType.getSolid());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get(), fancy ? RenderType.getCutoutMipped() : RenderType.getSolid());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get(), RenderType.getCutoutMipped());
		
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_WHITE_WISTERIA_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_BLUE_WISTERIA_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_PINK_WISTERIA_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_PURPLE_WISTERIA_SAPLING.get(), RenderType.getCutoutMipped());
	}
	
	public static void registerBlockColors() {
        BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        DataUtils.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.get(0.5D, 1.0D), Arrays.asList(EnvironmentalBlocks.GIANT_TALL_GRASS));
        DataUtils.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.get(0.5D, 1.0D), Arrays.asList(
        		EnvironmentalBlocks.WILLOW_LEAVES, EnvironmentalBlocks.WILLOW_LEAF_CARPET, EnvironmentalBlocks.HANGING_WILLOW_LEAVES));

        ItemColors itemColors = Minecraft.getInstance().getItemColors();
        DataUtils.registerBlockItemColor(itemColors, (color, items) -> GrassColors.get(0.5D, 1.0D), Arrays.asList(EnvironmentalBlocks.GIANT_TALL_GRASS));
        DataUtils.registerBlockItemColor(itemColors, (color, items) -> FoliageColors.get(0.5D, 1.0D), Arrays.asList(
        		EnvironmentalBlocks.WILLOW_LEAVES, EnvironmentalBlocks.WILLOW_LEAF_CARPET, EnvironmentalBlocks.HANGING_WILLOW_LEAVES));
    }
}
