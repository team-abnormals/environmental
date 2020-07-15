package com.team_abnormals.environmental.core.other;

import com.team_abnormals.environmental.core.Environmental;
import com.team_abnormals.environmental.core.registry.EnvironmentalBlocks;
import com.team_abnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.abnormals_core.core.utils.TradeUtils;
import com.teamabnormals.abnormals_core.core.utils.TradeUtils.ItemsForEmeraldsTrade;
import net.minecraft.entity.merchant.villager.VillagerProfession;
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
        event.getGenericTrades().add(new ItemsForEmeraldsTrade(EnvironmentalBlocks.COLUMBINE.get(), 1, 1, 7, 1));
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
    public static void onVillagerTradesEvent(VillagerTradesEvent event) {
        if (event.getType() == VillagerProfession.FARMER) {
            event.getTrades().get(1).add(new TradeUtils.EmeraldsForItemsTrade(EnvironmentalItems.RICE.get(), 23, 1, 6, 1));
        }

        if (event.getType() == VillagerProfession.FISHERMAN) {
            event.getTrades().get(3).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.COD_KELP_ROLL.get(), 5, 2, 6, 15));
            event.getTrades().get(3).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.SALMON_RICE_CAKE.get(), 5, 2, 6, 15));
            event.getTrades().get(4).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.PUFFERFISH_RICE_CAKE.get(), 3, 4, 5, 30));
            event.getTrades().get(5).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.TROPICAL_FISH_KELP_ROLL.get(), 3, 4, 5, 30));

            if (ModList.get().isLoaded("upgrade_aquatic")) {
                event.getTrades().get(4).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.PIKE_KELP_ROLL.get(), 4, 1, 3, 25));
                event.getTrades().get(5).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.LIONFISH_RICE_CAKE.get(), 3, 4, 5, 30));
            }

            if (ModList.get().isLoaded("quark")) {
                event.getTrades().get(4).add(new TradeUtils.ItemsForEmeraldsTrade(EnvironmentalItems.CRAB_KELP_ROLL.get(), 4, 1, 3, 25));
            }
        }
    }
}
