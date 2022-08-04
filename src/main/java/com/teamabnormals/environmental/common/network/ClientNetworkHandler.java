package com.teamabnormals.environmental.common.network;

import com.teamabnormals.environmental.client.gui.screens.inventory.SlabfishInventoryScreen;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.inventory.SlabfishInventoryMenu;
import com.teamabnormals.environmental.common.network.message.SOpenSlabfishInventoryMessage;
import com.teamabnormals.environmental.common.network.message.SSyncBackpackTypeMessage;
import com.teamabnormals.environmental.common.network.message.SSyncSlabfishTypeMessage;
import com.teamabnormals.environmental.common.network.message.SSyncSweaterTypeMessage;
import com.teamabnormals.environmental.common.slabfish.ClientSlabfishManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

public class ClientNetworkHandler {
	public static void handleOpenSlabfishInventory(SOpenSlabfishInventoryMessage packet) {
		Player player = Minecraft.getInstance().player;

		if (player != null) {
			Level world = player.level;
			Entity entity = world.getEntity(packet.getEntityId());

			if (entity instanceof Slabfish slabfish) {
				SlabfishInventoryMenu container = new SlabfishInventoryMenu(packet.getWindowId(), player.getInventory(), slabfish.slabfishBackpack, slabfish);
				player.containerMenu = container;
				Minecraft.getInstance().setScreen(new SlabfishInventoryScreen(container, player.getInventory(), slabfish));
			}
		}
	}

	public static void handleSyncSlabfishType(SSyncSlabfishTypeMessage msg, NetworkEvent.Context ctx) {
		ClientSlabfishManager.receive(msg);
	}

	public static void handleSyncSweaterType(SSyncSweaterTypeMessage msg, NetworkEvent.Context ctx) {
		ClientSlabfishManager.receive(msg);
	}

	public static void handleSyncBackpackType(SSyncBackpackTypeMessage msg, NetworkEvent.Context ctx) {
		ClientSlabfishManager.receive(msg);
	}
}
