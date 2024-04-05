package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

public class EnvironmentalClientCompat {

	public static void registerClientCompat() {
		registerRenderLayers();
		registerBlockColors();
		registerItemProperties();
	}

	private static void registerItemProperties() {
		ItemProperties.register(EnvironmentalItems.KOI_BUCKET.get(), new ResourceLocation(Environmental.MOD_ID, "variant"), (stack, world, entity, hash) -> stack.getOrCreateTag().getInt("BucketVariantTag"));
	}

	private static void registerRenderLayers() {
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.SLABFISH_EFFIGY.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.YAK_HAIR_BLOCK.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.YAK_HAIR_RUG.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MYCELIUM_SPROUTS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GIANT_TALL_GRASS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.LARGE_LILY_PAD.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GIANT_LILY_PAD.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CACTUS_BOBBLE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CUP_LICHEN.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_CUP_LICHEN.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.TALL_DWARF_SPRUCE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_DWARF_SPRUCE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_SPROUT.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_STALK.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_CATTAIL.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH_SLAB.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH_STAIRS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH_VERTICAL_SLAB.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_SLAB.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_STAIRS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_VERTICAL_SLAB.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_SLAB.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_STAIRS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_VERTICAL_SLAB.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CARTWHEEL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.VIOLET.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.RED_LOTUS_FLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DIANTHUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUEBELL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.TASSELFLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.YELLOW_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.ORANGE_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.RED_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MAGENTA_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.YELLOW_WALL_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.ORANGE_WALL_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.RED_WALL_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WALL_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MAGENTA_WALL_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WALL_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BIRD_OF_PARADISE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.HIBISCUS_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.HIBISCUS_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.HIBISCUS_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_CARTWHEEL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_VIOLET.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_RED_LOTUS_FLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_WHITE_LOTUS_FLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_DIANTHUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_BLUEBELL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_YELLOW_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_ORANGE_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_TASSELFLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_RED_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PINK_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_MAGENTA_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PURPLE_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_BIRD_OF_PARADISE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_TRAPDOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_LADDER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_POST.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.STRIPPED_WILLOW_POST.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_WILLOW_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_TRAPDOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_LADDER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_POST.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.STRIPPED_PINE_POST.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PINE_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_TRAPDOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_LADDER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_POST.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.STRIPPED_CHERRY_POST.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_CHERRY_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHEERFUL_CHERRY_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHEERFUL_CHERRY_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHEERFUL_CHERRY_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHEERFUL_CHERRY_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_CHEERFUL_CHERRY_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHEERFUL_CHERRY_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MOODY_CHERRY_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MOODY_CHERRY_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MOODY_CHERRY_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MOODY_CHERRY_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_MOODY_CHERRY_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MOODY_CHERRY_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_TRAPDOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_LADDER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_POST.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.STRIPPED_WISTERIA_POST.get(), RenderType.cutoutMipped());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_WHITE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_WHITE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_BLUE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_BLUE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PINK_WISTERIA_SAPLING.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PINK_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PURPLE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PURPLE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_PILE.get(), RenderType.cutout());
	}

	private static void registerBlockColors() {
		BlockColors blockColors = Minecraft.getInstance().getBlockColors();
		ItemColors itemColors = Minecraft.getInstance().getItemColors();

		List<RegistryObject<Block>> grassColors = Arrays.asList(EnvironmentalBlocks.GIANT_TALL_GRASS);
		List<RegistryObject<Block>> foliageColors = Arrays.asList(
				EnvironmentalBlocks.WILLOW_LEAVES, EnvironmentalBlocks.WILLOW_LEAF_CARPET, EnvironmentalBlocks.HANGING_WILLOW_LEAVES, EnvironmentalBlocks.WILLOW_HEDGE, EnvironmentalBlocks.WILLOW_LEAF_PILE,
				EnvironmentalBlocks.PINE_LEAVES, EnvironmentalBlocks.PINE_LEAF_CARPET, EnvironmentalBlocks.PINE_HEDGE, EnvironmentalBlocks.PINE_LEAF_PILE
		);
		List<RegistryObject<Block>> waterLilyColors = Arrays.asList(EnvironmentalBlocks.LARGE_LILY_PAD, EnvironmentalBlocks.GIANT_LILY_PAD);

		List<RegistryObject<Block>> willowFoliageItemColors = Arrays.asList(
				EnvironmentalBlocks.WILLOW_LEAVES, EnvironmentalBlocks.WILLOW_LEAF_CARPET, EnvironmentalBlocks.HANGING_WILLOW_LEAVES, EnvironmentalBlocks.WILLOW_HEDGE, EnvironmentalBlocks.WILLOW_LEAF_PILE
		);
		List<RegistryObject<Block>> pineFoliageItemColors = Arrays.asList(
				EnvironmentalBlocks.PINE_LEAVES, EnvironmentalBlocks.PINE_LEAF_CARPET, EnvironmentalBlocks.PINE_HEDGE, EnvironmentalBlocks.PINE_LEAF_PILE
		);

		DataUtil.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0.5D, 1.0D), grassColors);
		DataUtil.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.get(0.5D, 1.0D), foliageColors);
		DataUtil.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? 2129968 : 7181897, waterLilyColors);

		DataUtil.registerBlockItemColor(itemColors, (color, items) -> GrassColor.get(0.5D, 1.0D), grassColors);
		DataUtil.registerBlockItemColor(itemColors, (color, items) -> 7315015, pineFoliageItemColors);
		DataUtil.registerBlockItemColor(itemColors, (color, items) -> 6975545, willowFoliageItemColors);
		DataUtil.registerBlockItemColor(itemColors, (block, tintIndex) -> {
			BlockState blockstate = ((BlockItem) block.getItem()).getBlock().defaultBlockState();
			return blockColors.getColor(blockstate, null, null, tintIndex);
		}, waterLilyColors);

	}
}
