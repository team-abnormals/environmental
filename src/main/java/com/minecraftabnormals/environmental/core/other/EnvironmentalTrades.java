package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.abnormals_core.core.util.TradeUtil;
import com.minecraftabnormals.abnormals_core.core.util.TradeUtil.AbnormalsTrade;
import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.EnvironmentalConfig;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalVillagers;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalTrades {

	@SubscribeEvent
	public static void onWandererTradesEvent(WandererTradesEvent event) {
		TradeUtil.addWandererTrades(event,
				new AbnormalsTrade(1, EnvironmentalItems.CATTAIL_SEEDS.get(), 1, 6, 1),
				new AbnormalsTrade(1, EnvironmentalItems.DUCKWEED.get(), 2, 6, 1),

				new AbnormalsTrade(5, EnvironmentalBlocks.WILLOW_SAPLING.get().asItem(), 1, 8, 1),
				new AbnormalsTrade(5, EnvironmentalBlocks.CHERRY_SAPLING.get().asItem(), 1, 8, 1),
				new AbnormalsTrade(5, EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get().asItem(), 1, 8, 1),
				new AbnormalsTrade(5, EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get().asItem(), 1, 8, 1),
				new AbnormalsTrade(5, EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get().asItem(), 1, 8, 1),
				new AbnormalsTrade(5, EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get().asItem(), 1, 8, 1),

				new AbnormalsTrade(1, EnvironmentalBlocks.CARTWHEEL.get().asItem(), 1, 5, 1),
				new AbnormalsTrade(1, EnvironmentalBlocks.VIOLET.get().asItem(), 1, 12, 1),
				new AbnormalsTrade(1, EnvironmentalBlocks.BLUEBELL.get().asItem(), 1, 8, 1),
				new AbnormalsTrade(1, EnvironmentalBlocks.RED_LOTUS_FLOWER.get().asItem(), 1, 7, 1),
				new AbnormalsTrade(1, EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get().asItem(), 1, 7, 1),
				new AbnormalsTrade(1, EnvironmentalBlocks.DIANTHUS.get().asItem(), 1, 8, 1),
				new AbnormalsTrade(1, EnvironmentalBlocks.YELLOW_HIBISCUS.get().asItem(), 1, 12, 1),
				new AbnormalsTrade(1, EnvironmentalBlocks.ORANGE_HIBISCUS.get().asItem(), 1, 12, 1),
				new AbnormalsTrade(1, EnvironmentalBlocks.RED_HIBISCUS.get().asItem(), 1, 12, 1),
				new AbnormalsTrade(1, EnvironmentalBlocks.PINK_HIBISCUS.get().asItem(), 1, 12, 1),
				new AbnormalsTrade(1, EnvironmentalBlocks.MAGENTA_HIBISCUS.get().asItem(), 1, 12, 1),
				new AbnormalsTrade(1, EnvironmentalBlocks.PURPLE_HIBISCUS.get().asItem(), 1, 12, 1)
		);

		if (EnvironmentalConfig.COMMON.generateRice.get())
			TradeUtil.addWandererTrades(event, new AbnormalsTrade(1, EnvironmentalItems.RICE.get(), 1, 12, 1));

		TradeUtil.addRareWandererTrades(event,
				new AbnormalsTrade(5, EnvironmentalItems.SLABFISH_BUCKET.get(), 1, 4, 1),
				new AbnormalsTrade(24, EnvironmentalItems.WANDERER_BOOTS.get(), 1, 1, 1)
		);
	}

	@SubscribeEvent
	public static void onVillagerTradesEvent(VillagerTradesEvent event) {
		if (EnvironmentalConfig.COMMON.generateRice.get())
			TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.NOVICE, new AbnormalsTrade(EnvironmentalItems.RICE.get(), 23, 1, 6, 1));

		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.APPRENTICE,
				new AbnormalsTrade(1, EnvironmentalItems.CHERRIES.get(), 6, 16, 5),
				new AbnormalsTrade(1, EnvironmentalItems.APPLE_PIE.get(), 5, 12, 5),
				new AbnormalsTrade(1, EnvironmentalItems.CHERRY_PIE.get(), 6, 12, 5)
		);

		TradeUtil.addVillagerTrades(event, VillagerProfession.FISHERMAN, TradeUtil.APPRENTICE,
				new AbnormalsTrade(5, EnvironmentalItems.COD_KELP_ROLL.get(), 2, 6, 15),
				new AbnormalsTrade(5, EnvironmentalItems.SALMON_RICE_CAKE.get(), 2, 6, 15)
		);
		TradeUtil.addVillagerTrades(event, VillagerProfession.FISHERMAN, TradeUtil.EXPERT,
				new AbnormalsTrade(3, EnvironmentalItems.PUFFERFISH_RICE_CAKE.get(), 4, 5, 30)
		);
		TradeUtil.addVillagerTrades(event, VillagerProfession.FISHERMAN, TradeUtil.MASTER,
				new AbnormalsTrade(3, EnvironmentalItems.TROPICAL_FISH_KELP_ROLL.get(), 4, 5, 30),
				new AbnormalsTrade(3, EnvironmentalItems.KOI_KELP_ROLL.get(), 5, 5, 30)
		);

		TradeUtil.addCompatVillagerTrades(event, "upgrade_aquatic", VillagerProfession.FISHERMAN, TradeUtil.EXPERT,
				new AbnormalsTrade(4, EnvironmentalItems.PIKE_KELP_ROLL.get(), 1, 3, 25)
		);
		TradeUtil.addCompatVillagerTrades(event, "upgrade_aquatic", VillagerProfession.FISHERMAN, TradeUtil.MASTER,
				new AbnormalsTrade(3, EnvironmentalItems.LIONFISH_RICE_CAKE.get(), 4, 5, 30)
		);
		TradeUtil.addCompatVillagerTrades(event, "quark", VillagerProfession.FISHERMAN, TradeUtil.EXPERT,
				new AbnormalsTrade(4, EnvironmentalItems.CRAB_KELP_ROLL.get(), 1, 3, 25)
		);

		TradeUtil.addVillagerTrades(event, VillagerProfession.MASON, TradeUtil.MASTER,
				new AbnormalsTrade(24, EnvironmentalItems.ARCHITECT_BELT.get(), 1, 1, 5)
		);

		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.NOVICE,
				new AbnormalsTrade(EnvironmentalItems.DUCK.get(), 18, 1, 16, 2)
		);
		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.APPRENTICE,
				new AbnormalsTrade(1, EnvironmentalItems.COOKED_DUCK.get(), 6, 16, 5)
		);
		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.JOURNEYMAN,
				new AbnormalsTrade(EnvironmentalItems.VENISON.get(), 12, 1, 16, 20),
				new AbnormalsTrade(1, EnvironmentalItems.COOKED_VENISON.get(), 5, 16, 10)
		);
	}

	@SubscribeEvent
	public static void onCarpenterTradesEvent(VillagerTradesEvent event) {
		String[] defaultRemoved = new String[]{"crimson", "warped", "poise", "aspen", "grimwood", "morado", "kousa"};

		for (Item item : ItemTags.PLANKS.getAllElements()) {
			if (notOnBlacklist(item, new String[]{"vertical", "stained"}, defaultRemoved)) {
				TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CARPENTER.get(), TradeUtil.NOVICE, new AbnormalsTrade(item, 24, 1, 16, 3));
			}
		}

		for (Item item : ItemTags.LOGS.getAllElements()) {
			if (notOnBlacklist(item, new String[]{"stripped", "_wood",}, defaultRemoved)) {
				TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CARPENTER.get(), TradeUtil.NOVICE, new AbnormalsTrade(item, 6, 1, 16, 4));
			}
		}

		for (Item item : ItemTags.SIGNS.getAllElements()) {
			if (notOnBlacklist(item, defaultRemoved)) {
				TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CARPENTER.get(), TradeUtil.APPRENTICE, new AbnormalsTrade(1, item, 3, 4, 10));
			}
		}

		for (Item item : ItemTags.WOODEN_TRAPDOORS.getAllElements()) {
			if (notOnBlacklist(item, defaultRemoved)) {
				TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CARPENTER.get(), TradeUtil.APPRENTICE, new AbnormalsTrade(1, item, 4, 8, 10));
			}
		}

		for (Item item : ItemTags.SAPLINGS.getAllElements()) {
			if (notOnBlacklist(item, defaultRemoved)) {
				TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CARPENTER.get(), TradeUtil.JOURNEYMAN, new AbnormalsTrade(item, 8, 1, 16, 10));
			}
		}

		for (Item item : ItemTags.WOODEN_DOORS.getAllElements()) {
			if (notOnBlacklist(item, defaultRemoved)) {
				TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CARPENTER.get(), TradeUtil.JOURNEYMAN, new AbnormalsTrade(1, item, 2, 8, 15));
			}
		}

		TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CARPENTER.get(), TradeUtil.EXPERT,
				new AbnormalsTrade(Items.STICK, 23, 1, 16, 3),
				new AbnormalsTrade(Items.IRON_AXE, 1, 6, 8, 10),
				new AbnormalsTrade(Items.GOLDEN_AXE, 1, 7, 1, 20)
		);

		TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CERAMIST.get(), TradeUtil.MASTER, new AbnormalsTrade(24, EnvironmentalItems.ARCHITECT_BELT.get(), 1, 1, 5));
	}

	@SubscribeEvent
	public static void onCeramistTradesEvent(VillagerTradesEvent event) {
		Block[] terracottas = new Block[]{Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA};
		Block[] glazed_terracottas = new Block[]{Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA, Blocks.LIME_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA, Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA, Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA, Blocks.BLACK_GLAZED_TERRACOTTA};
		Block[] terracotta_bricks = new Block[]{EnvironmentalBlocks.WHITE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.ORANGE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.MAGENTA_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.LIGHT_BLUE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.YELLOW_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.LIME_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.PINK_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.GRAY_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.LIGHT_GRAY_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CYAN_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.PURPLE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.BLUE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.BROWN_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.GREEN_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.RED_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.BLACK_TERRACOTTA_BRICKS.get()};
		Block[] chiseled_terracotta_bricks = new Block[]{EnvironmentalBlocks.CHISELED_WHITE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_ORANGE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_MAGENTA_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_LIGHT_BLUE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_YELLOW_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_LIME_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_PINK_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_GRAY_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_LIGHT_GRAY_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_CYAN_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_PURPLE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_BLUE_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_BROWN_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_GREEN_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_RED_TERRACOTTA_BRICKS.get(), EnvironmentalBlocks.CHISELED_BLACK_TERRACOTTA_BRICKS.get()};

		TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CERAMIST.get(), TradeUtil.NOVICE,
				new AbnormalsTrade(EnvironmentalItems.MUD_BALL.get(), 16, 1, 16, 2),
				new AbnormalsTrade(1, EnvironmentalItems.MUD_BRICK.get(), 10, 16, 1)
		);
		TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CERAMIST.get(), TradeUtil.APPRENTICE,
				new AbnormalsTrade(1, Items.FLOWER_POT, 1, 10, 2),
				new AbnormalsTrade(1, EnvironmentalBlocks.SLABFISH_EFFIGY.get().asItem(), 1, 10, 2),
				new AbnormalsTrade(1, EnvironmentalBlocks.CHISELED_BRICKS.get().asItem(), 4, 16, 10)
		);

		for (Block terracotta : terracottas)
			TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CERAMIST.get(), TradeUtil.JOURNEYMAN, new AbnormalsTrade(terracotta.asItem(), 1, 1, 12, 15));
		for (Block glazed_terracotta : glazed_terracottas)
			TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CERAMIST.get(), TradeUtil.JOURNEYMAN, new AbnormalsTrade(glazed_terracotta.asItem(), 1, 1, 12, 15));

		for (Block terracotta_brick : terracotta_bricks)
			TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CERAMIST.get(), TradeUtil.EXPERT, new AbnormalsTrade(1, terracotta_brick.asItem(), 1, 12, 15));
		for (Block chiseled_terracotta_brick : chiseled_terracotta_bricks)
			TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CERAMIST.get(), TradeUtil.EXPERT, new AbnormalsTrade(1, chiseled_terracotta_brick.asItem(), 1, 12, 15));

		TradeUtil.addVillagerTrades(event, EnvironmentalVillagers.CERAMIST.get(), TradeUtil.MASTER, new AbnormalsTrade(24, EnvironmentalItems.ARCHITECT_BELT.get(), 1, 1, 5));

		TradeUtil.addCompatVillagerTrades(event, "buzzier_bees", EnvironmentalVillagers.CERAMIST.get(), TradeUtil.APPRENTICE,
				new AbnormalsTrade(3, ForgeRegistries.ITEMS.getValue(new ResourceLocation("buzzier_bees", "honey_pot")), 1, 10, 6)
		);
	}

	private static boolean notOnBlacklist(Item item, String[] items) {
		for (String name : items) {
			if (item.getRegistryName().toString().contains(name))
				return false;
		}
		return true;
	}

	private static boolean notOnBlacklist(Item item, String[] items, String[] items2) {
		for (String name : items) {
			if (item.getRegistryName().toString().contains(name))
				return false;
		}
		for (String name : items2) {
			if (item.getRegistryName().toString().contains(name))
				return false;
		}
		return true;
	}
}
