package com.minecraftabnormals.environmental.core.other;

import java.util.List;

import com.minecraftabnormals.environmental.core.Environmental;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalItems;
import com.minecraftabnormals.environmental.core.registry.EnvironmentalVillagers;
import com.teamabnormals.abnormals_core.core.utils.TradeUtils;
import com.teamabnormals.abnormals_core.core.utils.TradeUtils.ItemsForEmeraldsTrade;

import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades.ITrade;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Environmental.MODID)
public class EnvironmentalTrades {

    @SubscribeEvent
    public static void onWandererTradesEvent(WandererTradesEvent event) {
        event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.CATTAIL_SEEDS.get(), 1, 1, 6, 1));

        event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.WILLOW_SAPLING.get(), 5, 1, 8, 1));
        event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get(), 5, 1, 8, 1));
        event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get(), 5, 1, 8, 1));
        event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get(), 5, 1, 8, 1));
        event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get(), 5, 1, 8, 1));

        event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.RICE.get(), 1, 1, 12, 1));
        event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.DUCKWEED.get(), 1, 2, 6, 1));
        event.getRareTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.SLABFISH_BUCKET.get(), 5, 1, 4, 1));

        event.getGenericTrades().add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.CARTWHEEL.get(), 2, 1, 5, 1));
        event.getGenericTrades().add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.VIOLET.get(), 1, 1, 12, 1));
        event.getGenericTrades().add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.BLUEBELL.get(), 1, 1, 8, 1));
        event.getGenericTrades().add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.LOTUS_FLOWER.get(), 1, 1, 7, 1));
        event.getGenericTrades().add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.DIANTHUS.get(), 1, 1, 8, 1));
        event.getGenericTrades().add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.YELLOW_HIBISCUS.get(), 1, 1, 12, 1));
        event.getGenericTrades().add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.ORANGE_HIBISCUS.get(), 1, 1, 12, 1));
        event.getGenericTrades().add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.RED_HIBISCUS.get(), 1, 1, 12, 1));
        event.getGenericTrades().add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.PINK_HIBISCUS.get(), 1, 1, 12, 1));
        event.getGenericTrades().add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.MAGENTA_HIBISCUS.get(), 1, 1, 12, 1));
        event.getGenericTrades().add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.PURPLE_HIBISCUS.get(), 1, 1, 12, 1));

        event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.BLUE_DELPHINIUM.get(), 2, 1, 6, 1));
        event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.PINK_DELPHINIUM.get(), 2, 1, 6, 1));
        event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), 2, 1, 6, 1));
        event.getGenericTrades().add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalBlocks.WHITE_DELPHINIUM.get(), 2, 1, 6, 1));
	}
	
	@SubscribeEvent
	public static void onCarpenterTradesEvent(VillagerTradesEvent event) {
		String[] defaultRemoved = new String[] {"crimson", "warped", "poise"};
		List<ITrade> novice = event.getTrades().get(1);
		List<ITrade> apprentice = event.getTrades().get(2);
		List<ITrade> journeyman = event.getTrades().get(3);
		List<ITrade> expert = event.getTrades().get(4);
		List<ITrade> master = event.getTrades().get(5);

		if(event.getType() == EnvironmentalVillagers.CARPENTER.get()) {
			
			// NOVICE //			
			for(Item item : ItemTags.PLANKS.getAllElements()) {
				if (notOnBlacklist(item, new String[] {"vertical", "crimson", "warped", "poise"})) {
					novice.add(new TradeUtils.EmeraldsForItemsTrade(item, 24, 1, 16, 3));
				}
			}
			
			for(Item item : ItemTags.field_232912_o_.getAllElements()) {
				if (notOnBlacklist(item, new String[] {"stripped", "_wood"})) {
					novice.add(new TradeUtils.EmeraldsForItemsTrade(item, 6, 1, 16, 4));
				}
			}
			
			// APPRENTICE //
			for(Item item : ItemTags.SIGNS.getAllElements()) {
				if (notOnBlacklist(item, defaultRemoved)) {
					apprentice.add(new TradeUtils.EmeraldsForItemsTrade(item, 6, 1, 4, 10));
				}
			}
			
			for(Item item : ItemTags.WOODEN_TRAPDOORS.getAllElements()) {
				if (notOnBlacklist(item, defaultRemoved)) {
					apprentice.add(new TradeUtils.ItemsForEmeraldsTrade(item, 1, 6, 4, 10));
				}
			}
			
			// JOURNEYMAN //
			for(Item item : ItemTags.SAPLINGS.getAllElements()) {
				journeyman.add(new TradeUtils.EmeraldsForItemsTrade(item, 8, 1, 16, 10));
			}
			
			for(Item item : ItemTags.WOODEN_DOORS.getAllElements()) {
				if (notOnBlacklist(item, defaultRemoved)) {
					journeyman.add(new TradeUtils.ItemsForEmeraldsTrade(item, 1, 3, 5, 15));
				}
			}
			
			// EXPERT //
			expert.add(new TradeUtils.EmeraldsForItemsTrade(Items.STICK, 23, 1, 16, 3));
			expert.add(new TradeUtils.EmeraldsForItemsTrade(Items.IRON_AXE, 1, 6, 8, 10));
			expert.add(new TradeUtils.EmeraldsForItemsTrade(Items.GOLDEN_AXE, 1, 7, 1, 20));

			// MASTER //
			master.add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.CONSTRUCTOR_BELT.get(), 24, 1, 4, 5));
		}
	}
	
	private static boolean notOnBlacklist(Item item, String[] items) {
		for (String name : items) {
			if (item.getRegistryName().toString().contains(name)) return false;
		}
		return true;
	}
	
	@SubscribeEvent
	public static void onCeramistTradesEvent(VillagerTradesEvent event) {
		List<ITrade> novice = event.getTrades().get(1);
		if(event.getType() == EnvironmentalVillagers.CERAMIST.get()) {
			novice.add(new TradeUtils.EmeraldsForItemsTrade(Items.ACACIA_BOAT, 23, 1, 6, 1));
			
		}
	}
	
	@SubscribeEvent
	public static void onVillagerTradesEvent(VillagerTradesEvent event) {
		if(event.getType() == VillagerProfession.FARMER) {
			event.getTrades().get(1).add(new TradeUtils.EmeraldsForItemsTrade(EnvironmentalItems.RICE.get(), 23, 1, 6, 1));
		}
		
		if(event.getType() == VillagerProfession.FISHERMAN) {
			event.getTrades().get(3).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.COD_KELP_ROLL.get(), 5, 2, 6, 15));
			event.getTrades().get(3).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.SALMON_RICE_CAKE.get(), 5, 2, 6, 15));
			event.getTrades().get(4).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.PUFFERFISH_RICE_CAKE.get(), 3, 4, 5, 30));
			event.getTrades().get(5).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.TROPICAL_FISH_KELP_ROLL.get(), 3, 4, 5, 30));
			
			if(ModList.get().isLoaded("upgrade_aquatic")) {
				event.getTrades().get(4).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.PIKE_KELP_ROLL.get(), 4, 1, 3, 25));
				event.getTrades().get(5).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.LIONFISH_RICE_CAKE.get(), 3, 4, 5, 30));	
			}
			
			if(ModList.get().isLoaded("quark")) {
				event.getTrades().get(4).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.CRAB_KELP_ROLL.get(), 4, 1, 3, 25));
			}
		}
    }
}
