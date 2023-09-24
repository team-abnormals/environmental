package com.teamabnormals.environmental.common.network.message;

import com.teamabnormals.environmental.common.network.ServerNetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.HandshakeHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CAcknowledgeEnvironmentalMessage extends EnvironmentalLoginMessage {

	public CAcknowledgeEnvironmentalMessage() {
	}

	public static void encode(CAcknowledgeEnvironmentalMessage msg, FriendlyByteBuf buf) {
	}

	public static CAcknowledgeEnvironmentalMessage decode(FriendlyByteBuf buf) {
		return new CAcknowledgeEnvironmentalMessage();
	}

	public static void handle(HandshakeHandler __, CAcknowledgeEnvironmentalMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ServerNetworkHandler.handleClientAcknowledgement(msg, ctx);
	}
}