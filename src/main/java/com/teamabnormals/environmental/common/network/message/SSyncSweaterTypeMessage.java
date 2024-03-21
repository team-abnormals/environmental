package com.teamabnormals.environmental.common.network.message;

import com.teamabnormals.environmental.common.network.ClientNetworkHandler;
import com.teamabnormals.environmental.common.slabfish.SlabfishManager;
import com.teamabnormals.environmental.common.slabfish.SweaterType;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * <p>Called during login and play to synchronize sweater types with all clients.</p>
 *
 * @author Ocelot
 */
public class SSyncSweaterTypeMessage extends EnvironmentalLoginMessage {
	private final SweaterType[] sweaterTypes;

	public SSyncSweaterTypeMessage() {
		this(SlabfishManager.get(LogicalSide.SERVER).getAllSweaterTypes());
	}

	private SSyncSweaterTypeMessage(SweaterType[] sweaterTypes) {
		this.sweaterTypes = sweaterTypes;
	}

	public static void encode(SSyncSweaterTypeMessage msg, FriendlyByteBuf buf) {
		buf.writeVarInt(msg.sweaterTypes.length);
		for (int i = 0; i < msg.sweaterTypes.length; i++)
			msg.sweaterTypes[i].writeTo(buf);
	}

	public static SSyncSweaterTypeMessage decode(FriendlyByteBuf buf) {
		SweaterType[] slabfishTypes = new SweaterType[buf.readVarInt()];
		for (int i = 0; i < slabfishTypes.length; i++)
			slabfishTypes[i] = SweaterType.readFrom(buf);
		return new SSyncSweaterTypeMessage(slabfishTypes);
	}

	public static void handlePlay(SSyncSweaterTypeMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> ClientNetworkHandler.handleSyncSweaterType(msg, ctx.get()));
		ctx.get().setPacketHandled(true);
	}

	public static void handleLogin(SSyncSweaterTypeMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> ClientNetworkHandler.handleSyncSweaterType(msg, ctx.get()));
		Environmental.LOGIN.reply(new CAcknowledgeEnvironmentalMessage(), ctx.get());
		ctx.get().setPacketHandled(true);
	}

	/**
	 * @return The types of sweater received from the server
	 */
	public SweaterType[] getSweaterTypes() {
		return sweaterTypes;
	}
}