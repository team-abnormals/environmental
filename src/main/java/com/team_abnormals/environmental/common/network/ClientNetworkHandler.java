package com.team_abnormals.environmental.common.network;

import com.team_abnormals.environmental.client.gui.screen.inventory.SlabfishInventoryScreen;
import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.common.inventory.container.SlabfishInventoryContainer;
import com.team_abnormals.environmental.common.network.message.CAcknowledgeEnvironmentalMessage;
import com.team_abnormals.environmental.common.network.message.SOpenSlabfishInventoryMessage;
import com.team_abnormals.environmental.common.network.message.SSyncSlabfishTypeMessage;
import com.team_abnormals.environmental.core.Environmental;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientNetworkHandler
{
    public static void handleOpenSlabfishInventory(SOpenSlabfishInventoryMessage packet)
    {
        PlayerEntity player = Minecraft.getInstance().player;

        if (player != null)
        {
            World world = player.world;
            Entity entity = world.getEntityByID(packet.getEntityId());

            if (entity instanceof SlabfishEntity)
            {
                SlabfishEntity slabfish = (SlabfishEntity) entity;

                SlabfishInventoryContainer container = new SlabfishInventoryContainer(packet.getWindowId(), player.inventory, slabfish.slabfishBackpack, slabfish);
                player.openContainer = container;
                Minecraft.getInstance().displayGuiScreen(new SlabfishInventoryScreen(container, player.inventory, slabfish));
            }
        }
    }

    public static void handleSyncSlabfishType(SSyncSlabfishTypeMessage msg, NetworkEvent.Context ctx)
    {
        System.out.println("Received slabfish types. Sending response to server.");
        Environmental.LOGIN.reply(new CAcknowledgeEnvironmentalMessage(), ctx);
    }
}
