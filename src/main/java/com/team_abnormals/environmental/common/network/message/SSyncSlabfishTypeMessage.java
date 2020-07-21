package com.team_abnormals.environmental.common.network.message;

import com.team_abnormals.environmental.common.network.ClientNetworkHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.FMLHandshakeHandler;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SSyncSlabfishTypeMessage implements EnvironmentalLoginMessage
{
    private int loginIndex;

    public SSyncSlabfishTypeMessage()
    {
    }

    public static void serialize(SSyncSlabfishTypeMessage msg, PacketBuffer buf)
    {
    }

    public static SSyncSlabfishTypeMessage deserialize(PacketBuffer buf)
    {
        return new SSyncSlabfishTypeMessage();
    }

    public static void handle(FMLHandshakeHandler __, SSyncSlabfishTypeMessage msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> ClientNetworkHandler.handleSyncSlabfishType(msg, ctx.get()));
        ctx.get().setPacketHandled(true);
    }

    @Override
    public int getLoginIndex()
    {
        return loginIndex;
    }

    @Override
    public void setLoginIndex(int loginIndex)
    {
        this.loginIndex = loginIndex;
    }
}