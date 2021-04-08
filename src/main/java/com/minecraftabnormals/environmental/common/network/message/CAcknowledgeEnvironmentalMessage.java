package com.minecraftabnormals.environmental.common.network.message;

import com.minecraftabnormals.environmental.common.network.ServerNetworkHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.FMLHandshakeHandler;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CAcknowledgeEnvironmentalMessage implements EnvironmentalLoginMessage {
	private int loginIndex;

	public CAcknowledgeEnvironmentalMessage() {
	}

	public static void serialize(CAcknowledgeEnvironmentalMessage msg, PacketBuffer buf) {
	}

	public static CAcknowledgeEnvironmentalMessage deserialize(PacketBuffer buf) {
		return new CAcknowledgeEnvironmentalMessage();
	}

	public static void handle(FMLHandshakeHandler __, CAcknowledgeEnvironmentalMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> ServerNetworkHandler.handleClientAcknowledgement(msg, ctx));
		ctx.get().setPacketHandled(true);
	}

	@Override
	public int getLoginIndex() {
		return loginIndex;
	}

	@Override
	public void setLoginIndex(int loginIndex) {
		this.loginIndex = loginIndex;
	}
}