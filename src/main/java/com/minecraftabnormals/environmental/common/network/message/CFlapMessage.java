package com.minecraftabnormals.environmental.common.network.message;

import java.util.function.Supplier;

import com.minecraftabnormals.environmental.common.network.ServerNetworkHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CFlapMessage
{
    public CFlapMessage()
    {
    }

    public static void serialize(CFlapMessage msg, PacketBuffer buf)
    {
    }

    public static CFlapMessage deserialize(PacketBuffer buf)
    {
        return new CFlapMessage();
    }

    public static void handle(CFlapMessage msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> ServerNetworkHandler.handleFlap(msg, ctx));
        ctx.get().setPacketHandled(true);
    }
}