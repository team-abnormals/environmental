package com.minecraftabnormals.environmental.core.other;

import java.util.List;

import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalVillagers;
import com.teamabnormals.abnormals_core.core.utils.TradeUtils;
import com.teamabnormals.abnormals_core.core.utils.TradeUtils.ItemsForEmeraldsTrade;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Environmental.MODID)
public class EnvironmentalTrades {

	@SubscribeEvent
	public static void onWandererTradesEvent(WandererTradesEvent event) {
		List<ITrade> trades = event.getGenericTrades();
		List<ITrade> rareTrades = event.getRareTrades();

		trades.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.CATTAIL_SEEDS.get(), 1, 1, 6, 1));
		trades.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.RICE.get(), 1, 1, 12, 1));
		trades.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.DUCKWEED.get(), 1, 2, 6, 1));

		trades.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.WILLOW_SAPLING.get(), 5, 1, 8, 1));
		trades.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.CHERRY_SAPLING.get(), 5, 1, 8, 1));
		trades.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get(), 5, 1, 8, 1));
		trades.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get(), 5, 1, 8, 1));
		trades.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get(), 5, 1, 8, 1));
		trades.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get(), 5, 1, 8, 1));

		trades.add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.CARTWHEEL.get(), 2, 1, 5, 1));
		trades.add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.VIOLET.get(), 1, 1, 12, 1));
		trades.add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.BLUEBELL.get(), 1, 1, 8, 1));
		trades.add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.RED_LOTUS_FLOWER.get(), 1, 1, 7, 1));
		trades.add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get(), 1, 1, 7, 1));
		trades.add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.DIANTHUS.get(), 1, 1, 8, 1));
		trades.add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.YELLOW_HIBISCUS.get(), 1, 1, 12, 1));
		trades.add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.ORANGE_HIBISCUS.get(), 1, 1, 12, 1));
		trades.add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.RED_HIBISCUS.get(), 1, 1, 12, 1));
		trades.add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.PINK_HIBISCUS.get(), 1, 1, 12, 1));
		trades.add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.MAGENTA_HIBISCUS.get(), 1, 1, 12, 1));
		trades.add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.PURPLE_HIBISCUS.get(), 1, 1, 12, 1));

		rareTrades.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.SLABFISH_BUCKET.get(), 5, 1, 4, 1));
		rareTrades.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.WANDERER_BOOTS.get(), 24, 1, 1, 1));
	}

	@SubscribeEvent
	public static void onCarpenterTradesEvent(VillagerTradesEvent event) {
		String[] defaultRemoved = new String[] { "crimson", "warped", "poise" };
		List<ITrade> novice = event.getTrades().get(1);
		List<ITrade> apprentice = event.getTrades().get(2);
		List<ITrade> journeyman = event.getTrades().get(3);
		List<ITrade> expert = event.getTrades().get(4);
		List<ITrade> master = event.getTrades().get(5);

		if (event.getType() == EnvironmentalVillagers.CARPENTER.get()) {

			// NOVICE //
			for (Item item : ItemTags.PLANKS.getAllElements()) {
				if (notOnBlacklist(item, new String[] { "vertical", "crimson", "warped", "poise" })) {
					novice.add(new TradeUtils.EmeraldsForItemsTrade(item, 24, 1, 16, 3));
				}
			}

			for (Item item : ItemTags.field_232912_o_.getAllElements()) {
				if (notOnBlacklist(item, new String[] { "stripped", "_wood" })) {
					novice.add(new TradeUtils.EmeraldsForItemsTrade(item, 6, 1, 16, 4));
				}
			}

			// APPRENTICE //
			for (Item item : ItemTags.SIGNS.getAllElements()) {
				if (notOnBlacklist(item, defaultRemoved)) {
					apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(item, 1, 3, 4, 10));
				}
			}

			for (Item item : ItemTags.WOODEN_TRAPDOORS.getAllElements()) {
				if (notOnBlacklist(item, defaultRemoved)) {
					apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(item, 1, 4, 8, 10));
				}
			}

			// JOURNEYMAN //
			for (Item item : ItemTags.SAPLINGS.getAllElements()) {
				journeyman.add(new TradeUtils.EmeraldsForItemsTrade(item, 8, 1, 16, 10));
			}

			for (Item item : ItemTags.WOODEN_DOORS.getAllElements()) {
				if (notOnBlacklist(item, defaultRemoved)) {
					journeyman.add(new TradeUtils.ItemsForEmeraldsTrade(item, 1, 2, 8, 15));
				}
			}

			// EXPERT //
			expert.add(new TradeUtils.EmeraldsForItemsTrade(Items.STICK, 23, 1, 16, 3));
			expert.add(new TradeUtils.EmeraldsForItemsTrade(Items.IRON_AXE, 1, 6, 8, 10));
			expert.add(new TradeUtils.EmeraldsForItemsTrade(Items.GOLDEN_AXE, 1, 7, 1, 20));

			// MASTER //
			master.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.ARCHITECT_BELT.get(), 24, 1, 1, 5));
		}
	}

	private static boolean notOnBlacklist(Item item, String[] items) {
		for (String name : items) {
			if (item.getRegistryName().toString().contains(name))
				return false;
		}
		return true;
	}

	@SubscribeEvent
	public static void onCeramistTradesEvent(VillagerTradesEvent event) {
		Block[] terracottas = new Block[] { Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA };
		Block[] glazed_terracottas = new Block[] { Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.LIME_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA, Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA, Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA, Blocks.BLACK_GLAZED_TERRACOTTA };
		Block[] terracotta_bricks = new Block[] { EnvironmentalBlocks.WHITE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.ORANGE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.MAGENTA_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.YELLOW_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.LIME_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.PINK_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.GRAY_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CYAN_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.PURPLE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.BLUE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.BROWN_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.GREEN_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.RED_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.BLACK_TERRACOTTA_BRICKS.get() };
		Block[] chisled_terracotta_bricks = new Block[] { EnvironmentalBlocks.CHISELED_WHITE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_ORANGE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_MAGENTA_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_LIGHT_BLUE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_YELLOW_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_LIME_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_PINK_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_GRAY_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_LIGHT_GRAY_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_CYAN_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_PURPLE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_BLUE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_BROWN_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_GREEN_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_RED_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_BLACK_TERRACOTTA_BRICKS.get() };

		List<ITrade> novice = event.getTrades().get(1);
		List<ITrade> apprentice = event.getTrades().get(2);
		List<ITrade> journeyman = event.getTrades().get(3);
		List<ITrade> expert = event.getTrades().get(4);
		List<ITrade> master = event.getTrades().get(5);

		if (event.getType() == EnvironmentalVillagers.CERAMIST.get()) {
			novice.add(new TradeUtils.EmeraldsForItemsTrade(EnvironmentalItems.MUD_BALL.get(), 16, 1, 16, 2));
			novice.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.MUD_BRICK.get(), 1, 10, 16, 1));

			apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(Items.FLOWER_POT, 1, 1, 10, 2));
			apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.SLABFISH_EFFIGY.get(), 1, 1, 10, 2));
			apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.CHISELED_BRICKS.get(), 1, 4, 16, 10));
			apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.CHISELED_MUD_BRICKS.get(), 1, 4, 16, 10));
			if (ModList.get().isLoaded("buzzier_bees"))
				apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(ForgeRegistries.ITEMS.getValue(new ResourceLocation("buzzier_bees", "honey_pot")), 3, 1, 10, 6));

			for (Block terracotta : terracottas)
				journeyman.add(new TradeUtils.EmeraldsForItemsTrade(terracotta, 1, 1, 12, 15));
			for (Block glazed_terracotta : glazed_terracottas)
				journeyman.add(new TradeUtils.EmeraldsForItemsTrade(glazed_terracotta, 1, 1, 12, 15));

			for (Block terracotta_brick : terracotta_bricks)
				expert.add(new TradeUtils.ItemsForEmeraldsTrade(terracotta_brick, 1, 1, 12, 15));
			for (Block chiseled_terracotta_brick : chisled_terracotta_bricks)
				expert.add(new TradeUtils.ItemsForEmeraldsTrade(chiseled_terracotta_brick, 1, 1, 12, 15));

			master.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.ARCHITECT_BELT.get(), 24, 1, 1, 5));
		}
	}

	@SubscribeEvent
	public static void onVillagerTradesEvent(VillagerTradesEvent event) {
		List<ITrade> novice = event.getTrades().get(1);
		List<ITrade> apprentice = event.getTrades().get(2);
		List<ITrade> expert = event.getTrades().get(4);
		List<ITrade> master = event.getTrades().get(5);

		if (event.getType() == VillagerProfession.FARMER) {
			novice.add(new TradeUtils.EmeraldsForItemsTrade(EnvironmentalItems.RICE.get(), 23, 1, 6, 1));
			apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.CHERRIES.get(), 1, 6, 16, 5));
			apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.APPLE_PIE.get(), 1, 5, 12, 5));
			apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.CHERRY_PIE.get(), 1, 6, 12, 5));
		}

		if (event.getType() == VillagerProfession.FISHERMAN) {
			apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.COD_KELP_ROLL.get(), 5, 2, 6, 15));
			apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.SALMON_RICE_CAKE.get(), 5, 2, 6, 15));
			expert.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.PUFFERFISH_RICE_CAKE.get(), 3, 4, 5, 30));
			master.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.TROPICAL_FISH_KELP_ROLL.get(), 3, 4, 5, 30));

			if (ModList.get().isLoaded("upgrade_aquatic")) {
				expert.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.PIKE_KELP_ROLL.get(), 4, 1, 3, 25));
				master.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.LIONFISH_RICE_CAKE.get(), 3, 4, 5, 30));
			}

			if (ModList.get().isLoaded("quark")) {
				expert.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.CRAB_KELP_ROLL.get(), 4, 1, 3, 25));
			}
		}
		
		if (event.getType() == VillagerProfession.MASON) {
			master.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.ARCHITECT_BELT.get(), 24, 1, 1, 5));
		}
	}
}
