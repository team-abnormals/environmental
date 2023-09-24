package com.teamabnormals.environmental.common.network.message;

import com.teamabnormals.environmental.common.network.ClientNetworkHandler;
import com.teamabnormals.environmental.common.slabfish.SlabfishManager;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * <p>Called during login and play to synchronize slabfish types with all clients.</p>
 *
 * @author Ocelot
 */
public class SSyncSlabfishTypeMessage extends EnvironmentalLoginMessage {
	private final SlabfishType[] slabfishTypes;

	public SSyncSlabfishTypeMessage() {
		this(SlabfishManager.get(LogicalSide.SERVER).getAllSlabfishTypes());
	}

	private SSyncSlabfishTypeMessage(SlabfishType[] slabfishTypes) {
		this.slabfishTypes = slabfishTypes;
	}

	public static void encode(SSyncSlabfishTypeMessage msg, FriendlyByteBuf buf) {
		buf.writeVarInt(msg.slabfishTypes.length);
		for (int i = 0; i < msg.slabfishTypes.length; i++)
			msg.slabfishTypes[i].writeTo(buf);
	}

	public static SSyncSlabfishTypeMessage decode(FriendlyByteBuf buf) {
		SlabfishType[] slabfishTypes = new SlabfishType[buf.readVarInt()];
		for (int i = 0; i < slabfishTypes.length; i++)
			slabfishTypes[i] = SlabfishType.readFrom(buf);
		return new SSyncSlabfishTypeMessage(slabfishTypes);
	}

	public static void handlePlay(SSyncSlabfishTypeMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> ClientNetworkHandler.handleSyncSlabfishType(msg, ctx.get()));
		ctx.get().setPacketHandled(true);
	}

	public static void handleLogin(SSyncSlabfishTypeMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> ClientNetworkHandler.handleSyncSlabfishType(msg, ctx.get()));
		Environmental.LOGIN.reply(new CAcknowledgeEnvironmentalMessage(), ctx.get());
		ctx.get().setPacketHandled(true);
	}

	/**
	 * @return The types of slabfish received from the server
	 */
	public SlabfishType[] getSlabfishTypes() {
		return slabfishTypes;
	}
}