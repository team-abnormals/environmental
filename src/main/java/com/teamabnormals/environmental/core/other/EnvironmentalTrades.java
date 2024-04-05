package com.teamabnormals.environmental.core.other;

import com.google.common.collect.ImmutableMap;
import com.teamabnormals.blueprint.core.util.TradeUtil;
import com.teamabnormals.blueprint.core.util.TradeUtil.BlueprintTrade;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.HashMap;
import java.util.List;

@EventBusSubscriber(modid = Environmental.MOD_ID)
public class EnvironmentalTrades {

	@SubscribeEvent
	public static void onWandererTradesEvent(WandererTradesEvent event) {
		TradeUtil.addWandererTrades(event,
				new BlueprintTrade(1, EnvironmentalItems.CATTAIL_FLUFF.get(), 1, 6, 1),
				new BlueprintTrade(1, EnvironmentalItems.DUCKWEED.get(), 2, 6, 1),

				new BlueprintTrade(5, EnvironmentalBlocks.WILLOW_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, EnvironmentalBlocks.CHERRY_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, EnvironmentalBlocks.CHEERFUL_CHERRY_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, EnvironmentalBlocks.MOODY_CHERRY_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, EnvironmentalBlocks.PINE_SAPLING.get().asItem(), 1, 8, 1),
				new BlueprintTrade(5, EnvironmentalBlocks.PINECONE.get().asItem(), 1, 8, 1),

				new BlueprintTrade(1, EnvironmentalBlocks.CARTWHEEL.get().asItem(), 1, 5, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.VIOLET.get().asItem(), 1, 12, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.BLUEBELL.get().asItem(), 1, 8, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.RED_LOTUS_FLOWER.get().asItem(), 1, 7, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get().asItem(), 1, 7, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.DIANTHUS.get().asItem(), 1, 8, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.TASSELFLOWER.get().asItem(), 1, 8, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.YELLOW_HIBISCUS.get().asItem(), 1, 12, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.ORANGE_HIBISCUS.get().asItem(), 1, 12, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.RED_HIBISCUS.get().asItem(), 1, 12, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.PINK_HIBISCUS.get().asItem(), 1, 12, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.MAGENTA_HIBISCUS.get().asItem(), 1, 12, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.PURPLE_HIBISCUS.get().asItem(), 1, 12, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.CUP_LICHEN.get().asItem(), 1, 8, 1),
				new BlueprintTrade(1, EnvironmentalBlocks.DWARF_SPRUCE.get().asItem(), 1, 8, 1)
		);

		TradeUtil.addRareWandererTrades(event,
				new BlueprintTrade(5, EnvironmentalItems.SLABFISH_BUCKET.get(), 1, 4, 1),
				new BlueprintTrade(5, EnvironmentalItems.KOI_BUCKET.get(), 1, 4, 1),
				new BlueprintTrade(24, EnvironmentalItems.WANDERER_BOOTS.get(), 1, 1, 1)
		);
	}

	@SubscribeEvent
	public static void onVillagerTradesEvent(VillagerTradesEvent event) {
		TradeUtil.addVillagerTrades(event, VillagerProfession.FARMER, TradeUtil.APPRENTICE,
				new BlueprintTrade(1, EnvironmentalItems.CHERRIES.get(), 6, 16, 5),
				new BlueprintTrade(1, EnvironmentalItems.CHERRY_PIE.get(), 6, 12, 5)
		);

		TradeUtil.addVillagerTrades(event, VillagerProfession.MASON, TradeUtil.MASTER,
				new BlueprintTrade(24, EnvironmentalItems.ARCHITECT_BELT.get(), 1, 1, 5)
		);

		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.NOVICE,
				new BlueprintTrade(EnvironmentalItems.DUCK.get(), 18, 1, 16, 2)
		);

		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.APPRENTICE,
				new BlueprintTrade(1, EnvironmentalItems.COOKED_DUCK.get(), 6, 16, 5)
		);

		TradeUtil.addVillagerTrades(event, VillagerProfession.BUTCHER, TradeUtil.JOURNEYMAN,
				new BlueprintTrade(EnvironmentalItems.VENISON.get(), 12, 1, 16, 20),
				new BlueprintTrade(1, EnvironmentalItems.COOKED_VENISON.get(), 5, 16, 10)
		);

		if (event.getType().equals(VillagerProfession.FISHERMAN)) {
			Int2ObjectMap<List<ItemListing>> trades = event.getTrades();
			for (ItemListing listing : trades.get(TradeUtil.MASTER)) {
				if (listing instanceof VillagerTrades.EmeraldsForVillagerTypeItem trade) {
					HashMap<VillagerType, Item> newTrades = new HashMap<>(trade.trades);

					if (newTrades.get(VillagerType.SWAMP) == Items.DARK_OAK_BOAT) {
						newTrades.replace(VillagerType.SWAMP, EnvironmentalItems.WILLOW_BOAT.getFirst().get());
					}

					trade.trades = ImmutableMap.copyOf(newTrades);
				}
			}
		}
	}
}
