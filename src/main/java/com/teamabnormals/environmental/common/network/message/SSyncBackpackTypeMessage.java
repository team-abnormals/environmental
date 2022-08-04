package com.teamabnormals.environmental.common.network.message;

import com.teamabnormals.environmental.common.network.ClientNetworkHandler;
import com.teamabnormals.environmental.common.slabfish.BackpackType;
import com.teamabnormals.environmental.common.slabfish.SlabfishManager;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * <p>Called during login and play to synchronize backpack types with all clients.</p>
 *
 * @author Ocelot
 */
public class SSyncBackpackTypeMessage implements EnvironmentalLoginMessage {
	private final BackpackType[] backpackTypes;
	private int loginIndex;

	public SSyncBackpackTypeMessage() {
		this(SlabfishManager.get(LogicalSide.SERVER).getAllBackpackTypes());
	}

	private SSyncBackpackTypeMessage(BackpackType[] backpackTypes) {
		this.backpackTypes = backpackTypes;
	}

	public static void serialize(SSyncBackpackTypeMessage msg, FriendlyByteBuf buf) {
		buf.writeVarInt(msg.backpackTypes.length);
		for (int i = 0; i < msg.backpackTypes.length; i++)
			msg.backpackTypes[i].writeTo(buf);
	}

	public static SSyncBackpackTypeMessage deserialize(FriendlyByteBuf buf) {
		BackpackType[] backpackTypes = new BackpackType[buf.readVarInt()];
		for (int i = 0; i < backpackTypes.length; i++)
			backpackTypes[i] = BackpackType.readFrom(buf);
		return new SSyncBackpackTypeMessage(backpackTypes);
	}

	public static void handlePlay(SSyncBackpackTypeMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> ClientNetworkHandler.handleSyncBackpackType(msg, ctx.get()));
		ctx.get().setPacketHandled(true);
	}

	public static void handleLogin(SSyncBackpackTypeMessage msg, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> ClientNetworkHandler.handleSyncBackpackType(msg, ctx.get()));
		Environmental.LOGIN.reply(new CAcknowledgeEnvironmentalMessage(), ctx.get());
		ctx.get().setPacketHandled(true);
	}

	/**
	 * @return The types of backpack received from the server
	 */
	public BackpackType[] getBackpackTypes() {
		return backpackTypes;
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