package com.team_abnormals.environmental.common.network;

import com.team_abnormals.environmental.client.gui.screen.inventory.SlabfishInventoryScreen;
import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.common.inventory.container.SlabfishInventoryContainer;
import com.team_abnormals.environmental.common.network.message.SOpenSlabfishInventoryMessage;
import com.team_abnormals.environmental.common.network.message.SSyncBackpackTypeMessage;
import com.team_abnormals.environmental.common.network.message.SSyncSlabfishTypeMessage;
import com.team_abnormals.environmental.common.network.message.SSyncSweaterTypeMessage;
import com.team_abnormals.environmental.common.slabfish.ClientSlabfishManager;
import com.team_abnormals.environmental.common.slabfish.SlabfishLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

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
        ClientSlabfishManager.receive(msg);
    }

    public static void handleSyncSweaterType(SSyncSweaterTypeMessage msg, NetworkEvent.Context ctx)
    {
        ClientSlabfishManager.receive(msg);
    }

    public static void handleSyncBackpackType(SSyncBackpackTypeMessage msg, NetworkEvent.Context ctx)
    {
        ClientSlabfishManager.receive(msg);
    }
}
