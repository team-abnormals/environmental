package com.minecraftabnormals.environmental.common.network.message;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import com.minecraftabnormals.environmental.common.network.ClientNetworkHandler;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishType;
import com.minecraftabnormals.environmental.core.Environmental;

/**
 * <p>Called during login and play to synchronize slabfish types with all clients.</p>
 *
 * @author Ocelot
 */
public class SSyncSlabfishTypeMessage implements EnvironmentalLoginMessage
{
    private final SlabfishType[] slabfishTypes;
    private int loginIndex;

    public SSyncSlabfishTypeMessage()
    {
        this(SlabfishManager.get(LogicalSide.SERVER).getAllSlabfishTypes());
    }

    private SSyncSlabfishTypeMessage(SlabfishType[] slabfishTypes)
    {
        this.slabfishTypes = slabfishTypes;
    }

    public static void serialize(SSyncSlabfishTypeMessage msg, PacketBuffer buf)
    {
        buf.writeVarInt(msg.slabfishTypes.length);
        for (int i = 0; i < msg.slabfishTypes.length; i++)
            msg.slabfishTypes[i].writeTo(buf);
    }

    public static SSyncSlabfishTypeMessage deserialize(PacketBuffer buf)
    {
        SlabfishType[] slabfishTypes = new SlabfishType[buf.readVarInt()];
        for (int i = 0; i < slabfishTypes.length; i++)
            slabfishTypes[i] = SlabfishType.readFrom(buf);
        return new SSyncSlabfishTypeMessage(slabfishTypes);
    }

    public static void handlePlay(SSyncSlabfishTypeMessage msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> ClientNetworkHandler.handleSyncSlabfishType(msg, ctx.get()));
        ctx.get().setPacketHandled(true);
    }

    public static void handleLogin(SSyncSlabfishTypeMessage msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> ClientNetworkHandler.handleSyncSlabfishType(msg, ctx.get()));
        Environmental.LOGIN.reply(new CAcknowledgeEnvironmentalMessage(), ctx.get());
        ctx.get().setPacketHandled(true);
    }

    /**
     * @return The types of slabfish received from the server
     */
    public SlabfishType[] getSlabfishTypes()
    {
        return slabfishTypes;
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