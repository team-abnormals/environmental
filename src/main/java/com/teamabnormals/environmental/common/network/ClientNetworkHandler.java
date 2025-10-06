package com.teamabnormals.environmental.common.network;

import com.teamabnormals.environmental.client.gui.screens.inventory.SlabfishInventoryScreen;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.inventory.SlabfishInventoryMenu;
import com.teamabnormals.environmental.common.network.message.OpenSlabfishInventoryPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ClientNetworkHandler {
	public static void handleOpenSlabfishInventory(OpenSlabfishInventoryPayload packet) {
		Player player = Minecraft.getInstance().player;

		if (player != null) {
			Level world = player.level();
			Entity entity = world.getEntity(packet.entityId());

			if (entity instanceof Slabfish slabfish) {
				SlabfishInventoryMenu container = new SlabfishInventoryMenu(packet.windowId(), player.getInventory(), slabfish.slabfishBackpack, slabfish);
				player.containerMenu = container;
				Minecraft.getInstance().setScreen(new SlabfishInventoryScreen(container, player.getInventory(), slabfish));
			}
		}
	}
}
