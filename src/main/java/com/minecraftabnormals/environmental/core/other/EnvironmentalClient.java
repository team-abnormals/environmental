package com.minecraftabnormals.environmental.core.other;

import java.util.Arrays;

import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.abnormals_core.core.utils.DataUtils;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = Environmental.MODID, value = Dist.CLIENT)
public class EnvironmentalClient {

	@SubscribeEvent
	public static void renderNameplate(RenderNameplateEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity entity = (LivingEntity) event.getEntity();
			if (entity.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem() == EnvironmentalItems.THIEF_HOOD.get()) {
				event.setResult(Result.DENY);
			}
		}
	}

	public static void setRenderLayers() {
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.SLABFISH_EFFIGY.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.ICE_LANTERN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.ICE_CHAIN.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.SAWMILL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.TALL_DEAD_BUSH.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.YAK_HAIR_BLOCK.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.YAK_HAIR_RUG.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WILLOW_DOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WILLOW_TRAPDOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WILLOW_LADDER.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WILLOW_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WILLOW_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WILLOW_SAPLING.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CHERRY_DOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CHERRY_TRAPDOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CHERRY_LADDER.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CHERRY_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CHERRY_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CHERRY_SAPLING.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.MYCELIUM_SPROUTS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.GIANT_TALL_GRASS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.LARGE_LILY_PAD.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.GIANT_LILY_PAD.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CATTAIL_SPROUTS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CATTAIL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.TALL_CATTAIL.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH_SLAB.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH_STAIRS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH_VERTICAL_SLAB.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_SLAB.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_STAIRS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_VERTICAL_SLAB.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_SLAB.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_STAIRS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_VERTICAL_SLAB.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.CARTWHEEL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.VIOLET.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.RED_LOTUS_FLOWER.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.DIANTHUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.BLUEBELL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.YELLOW_HIBISCUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.ORANGE_HIBISCUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.RED_HIBISCUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PINK_HIBISCUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.MAGENTA_HIBISCUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PURPLE_HIBISCUS.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_CARTWHEEL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_VIOLET.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_RED_LOTUS_FLOWER.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_WHITE_LOTUS_FLOWER.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_DIANTHUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_BLUEBELL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_YELLOW_HIBISCUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_ORANGE_HIBISCUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_RED_HIBISCUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_PINK_HIBISCUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_MAGENTA_HIBISCUS.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_PURPLE_HIBISCUS.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.RICE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.TALL_RICE.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.DUCKWEED.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_CATTAIL.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_WILLOW_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_CHERRY_SAPLING.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WISTERIA_DOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WISTERIA_TRAPDOOR.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WISTERIA_LADDER.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), RenderType.getCutoutMipped());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WHITE_DELPHINIUM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.BLUE_DELPHINIUM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PINK_DELPHINIUM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.BIRD_OF_PARADISE.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_WHITE_DELPHINIUM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_BLUE_DELPHINIUM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_PINK_DELPHINIUM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_PURPLE_DELPHINIUM.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_BIRD_OF_PARADISE.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get(), RenderType.getCutoutMipped());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_LEAF_CARPET.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_CARPET.get(), RenderType.getCutoutMipped());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get(), RenderType.getCutout());

		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_WHITE_WISTERIA_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_BLUE_WISTERIA_SAPLING.get(), RenderType.getCutout());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_PINK_WISTERIA_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(EnvironmentalBlocks.POTTED_PURPLE_WISTERIA_SAPLING.get(), RenderType.getCutout());
	}

	public static void registerBlockColors() {
		BlockColors blockColors = Minecraft.getInstance().getBlockColors();
		ItemColors itemColors = Minecraft.getInstance().getItemColors();

		DataUtils.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.get(0.5D, 1.0D), Arrays.asList(EnvironmentalBlocks.GIANT_TALL_GRASS));
		DataUtils.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.get(0.5D, 1.0D), Arrays.asList(EnvironmentalBlocks.WILLOW_LEAVES, EnvironmentalBlocks.WILLOW_LEAF_CARPET, EnvironmentalBlocks.HANGING_WILLOW_LEAVES));

		DataUtils.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? 2129968 : 7455580, Arrays.asList(EnvironmentalBlocks.LARGE_LILY_PAD, EnvironmentalBlocks.GIANT_LILY_PAD));

		DataUtils.registerBlockItemColor(itemColors, (p_210235_1_, p_210235_2_) -> {
			BlockState blockstate = ((BlockItem) p_210235_1_.getItem()).getBlock().getDefaultState();
			return blockColors.getColor(blockstate, (IBlockDisplayReader) null, (BlockPos) null, p_210235_2_);
		}, Arrays.asList(EnvironmentalBlocks.LARGE_LILY_PAD, EnvironmentalBlocks.GIANT_LILY_PAD));

		DataUtils.registerBlockItemColor(itemColors, (color, items) -> GrassColors.get(0.5D, 1.0D), Arrays.asList(EnvironmentalBlocks.GIANT_TALL_GRASS));
		DataUtils.registerBlockItemColor(itemColors, (color, items) -> FoliageColors.get(0.5D, 1.0D), Arrays.asList(EnvironmentalBlocks.WILLOW_LEAVES, EnvironmentalBlocks.WILLOW_LEAF_CARPET, EnvironmentalBlocks.HANGING_WILLOW_LEAVES));
	}
}
