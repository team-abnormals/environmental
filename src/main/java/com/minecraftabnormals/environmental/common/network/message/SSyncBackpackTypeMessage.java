package com.minecraftabnormals.environmental.common.network.message;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import com.minecraftabnormals.environmental.common.network.ClientNetworkHandler;
import com.minecraftabnormals.environmental.common.slabfish.BackpackType;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.core.Environmental;

/**
 * <p>Called during login and play to synchronize backpack types with all clients.</p>
 *
 * @author Ocelot
 */
public class SSyncBackpackTypeMessage implements EnvironmentalLoginMessage
{
    private final BackpackType[] backpackTypes;
    private int loginIndex;

    public SSyncBackpackTypeMessage()
    {
        this(SlabfishManager.get(LogicalSide.SERVER).getAllBackpackTypes());
    }

    private SSyncBackpackTypeMessage(BackpackType[] backpackTypes)
    {
        this.backpackTypes = backpackTypes;
    }

    public static void serialize(SSyncBackpackTypeMessage msg, PacketBuffer buf)
    {
        buf.writeVarInt(msg.backpackTypes.length);
        for (int i = 0; i < msg.backpackTypes.length; i++)
            msg.backpackTypes[i].writeTo(buf);
    }

    public static SSyncBackpackTypeMessage deserialize(PacketBuffer buf)
    {
        BackpackType[] backpackTypes = new BackpackType[buf.readVarInt()];
        for (int i = 0; i < backpackTypes.length; i++)
            backpackTypes[i] = BackpackType.readFrom(buf);
        return new SSyncBackpackTypeMessage(backpackTypes);
    }

    public static void handlePlay(SSyncBackpackTypeMessage msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> ClientNetworkHandler.handleSyncBackpackType(msg, ctx.get()));
        ctx.get().setPacketHandled(true);
    }

    public static void handleLogin(SSyncBackpackTypeMessage msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> ClientNetworkHandler.handleSyncBackpackType(msg, ctx.get()));
        Environmental.LOGIN.reply(new CAcknowledgeEnvironmentalMessage(), ctx.get());
        ctx.get().setPacketHandled(true);
    }

    /**
     * @return The types of backpack received from the server
     */
    public BackpackType[] getBackpackTypes()
    {
        return backpackTypes;
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