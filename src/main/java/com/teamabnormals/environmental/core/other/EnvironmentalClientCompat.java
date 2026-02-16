package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.client.model.DynamicItemModel;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalKoiVariants;
import com.teamabnormals.environmental.core.registry.datapack.slabfish.EnvironmentalSlabfishVariants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EnvironmentalClientCompat {

	public static void register() {
		EnvironmentalItems.setupTabEditors();
		EnvironmentalBlocks.setupTabEditors();
		registerRenderLayers();
	}

	@SubscribeEvent
	public static void registerAdditional(ModelEvent.RegisterAdditional event) {
		DynamicItemModel.register(event, "slabfish_bucket");
		DynamicItemModel.register(event, "koi_bucket");
	}

	@SubscribeEvent
	public static void modifyBakingResult(ModelEvent.ModifyBakingResult event) {
		DynamicItemModel.bake(event, EnvironmentalItems.SLABFISH_BUCKET.getId(), "slabfish_bucket", ModelResourceLocation.standalone(EnvironmentalSlabfishVariants.SWAMP.location().withPrefix("item/slabfish_bucket/")), DynamicItemModel.fishBucket());
		DynamicItemModel.bake(event, EnvironmentalItems.KOI_BUCKET.getId(), "koi_bucket", ModelResourceLocation.standalone(EnvironmentalKoiVariants.KOHAKU.location().withPrefix("item/koi_bucket/")), DynamicItemModel.fishBucket());
	}

	private static void registerRenderLayers() {
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.SLABFISH_EFFIGY.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.YAK_HAIR_BLOCK.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.YAK_HAIR_RUG.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MYCELIUM_SPROUTS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GIANT_TALL_GRASS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CACTUS_BOBBLE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CUP_LICHEN.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_CUP_LICHEN.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.TREE_LICHEN.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.SHRUB.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_SHRUB.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.FLOWERING_SHRUB.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_FLOWERING_SHRUB.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE_PLANT.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE_TORCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE_PLANT_TORCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE_SOUL_TORCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE_PLANT_SOUL_TORCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE_REDSTONE_TORCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE_PLANT_REDSTONE_TORCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE_ENDER_TORCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE_PLANT_ENDER_TORCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE_CUPRIC_TORCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DWARF_SPRUCE_PLANT_CUPRIC_TORCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_DWARF_SPRUCE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_SPROUT.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_STALK.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_CATTAIL.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH_SLAB.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH_STAIRS.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_SLAB.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_STAIRS.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_SLAB.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_STAIRS.get(), RenderType.cutout());

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
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_WILLOW_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_TRAPDOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_LADDER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PINE_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINE_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CEDAR_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CEDAR_TRAPDOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CEDAR_LADDER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CEDAR_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CEDAR_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_CEDAR_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CEDAR_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PLUM_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PLUM_TRAPDOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PLUM_LADDER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PLUM_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PLUM_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PLUM_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PLUM_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHEERFUL_PLUM_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHEERFUL_PLUM_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_CHEERFUL_PLUM_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHEERFUL_PLUM_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MOODY_PLUM_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MOODY_PLUM_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_MOODY_PLUM_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MOODY_PLUM_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_TRAPDOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_LADDER.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_WHITE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_WHITE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_BLUE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_BLUE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PINK_WISTERIA_SAPLING.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PINK_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PURPLE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PURPLE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_PILE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MUDGLASS.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MUDGLASS_PANE.get(), RenderType.translucent());
	}

	@SubscribeEvent
	public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
		event.register((state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.get(0.5D, 1.0D),
				EnvironmentalBlocks.GIANT_TALL_GRASS.get(),
				EnvironmentalBlocks.SHRUB.get(), EnvironmentalBlocks.FLOWERING_SHRUB.get(),
				EnvironmentalBlocks.POTTED_SHRUB.get(), EnvironmentalBlocks.POTTED_FLOWERING_SHRUB.get()
		);
		event.register((state, level, pos, tintIndex) -> level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.get(0.5D, 1.0D),
				EnvironmentalBlocks.WILLOW_LEAVES.get(), EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(), EnvironmentalBlocks.WILLOW_LEAF_PILE.get(),
				EnvironmentalBlocks.PINE_LEAVES.get(), EnvironmentalBlocks.PINE_LEAF_PILE.get()
		);
	}

	@SubscribeEvent
	public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
		BlockColors blockColors = Minecraft.getInstance().getBlockColors();
		event.register((item, tintIndex) -> GrassColor.get(0.5D, 1.0D), EnvironmentalBlocks.GIANT_TALL_GRASS);
		event.register((item, tintIndex) -> tintIndex == 0 ? GrassColor.get(0.5D, 1.0D) : -1, EnvironmentalBlocks.SHRUB, EnvironmentalBlocks.FLOWERING_SHRUB);
		event.register((item, tintIndex) -> 7578444, EnvironmentalBlocks.PINE_LEAVES, EnvironmentalBlocks.PINE_LEAF_PILE);
		event.register((item, tintIndex) -> 6975545, EnvironmentalBlocks.WILLOW_LEAVES, EnvironmentalBlocks.HANGING_WILLOW_LEAVES, EnvironmentalBlocks.WILLOW_LEAF_PILE);
	}
}
