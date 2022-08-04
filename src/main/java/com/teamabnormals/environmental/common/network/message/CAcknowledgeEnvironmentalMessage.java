package com.teamabnormals.environmental.common.network.message;

import com.teamabnormals.environmental.common.network.ServerNetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.HandshakeHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CAcknowledgeEnvironmentalMessage implements EnvironmentalLoginMessage {
	private int loginIndex;

	public CAcknowledgeEnvironmentalMessage() {
	}

	public static void serialize(CAcknowledgeEnvironmentalMessage msg, FriendlyByteBuf buf) {
	}

	public static CAcknowledgeEnvironmentalMessage deserialize(FriendlyByteBuf buf) {
		return new CAcknowledgeEnvironmentalMessage();
	}

	public static void handle(HandshakeHandler __, CAcknowledgeEnvironmentalMessage msg, Supplier<NetworkEvent.Context> ctx) {
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