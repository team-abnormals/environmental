package com.teamabnormals.environmental.common.network;

import com.teamabnormals.environmental.common.network.message.CAcknowledgeEnvironmentalMessage;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class ServerNetworkHandler {
	private static final Logger LOGGER = LogManager.getLogger();

	public static void handleClientAcknowledgement(CAcknowledgeEnvironmentalMessage packet, Supplier<NetworkEvent.Context> ctx) {
		LOGGER.debug("Received acknowledgement from client");
		ctx.get().setPacketHandled(true);
	}
}
