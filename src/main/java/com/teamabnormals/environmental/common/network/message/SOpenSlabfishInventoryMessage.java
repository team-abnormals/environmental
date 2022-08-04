package com.teamabnormals.environmental.common.network.message;

import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.network.ClientNetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SOpenSlabfishInventoryMessage {
	private final int entityId;
	private final int windowId;

	private SOpenSlabfishInventoryMessage(int entityId, int windowId) {
		this.entityId = entityId;
		this.windowId = windowId;
	}

	public SOpenSlabfishInventoryMessage(Slabfish slabfish, int windowId) {
		this(slabfish.getId(), windowId);
	}

	public static void serialize(SOpenSlabfishInventoryMessage message, FriendlyByteBuf buffer) {
		buffer.writeVarInt(message.entityId);
		buffer.writeVarInt(message.windowId);
	}

	public static SOpenSlabfishInventoryMessage deserialize(FriendlyByteBuf buffer) {
		return new SOpenSlabfishInventoryMessage(buffer.readVarInt(), buffer.readVarInt());
	}

	public static void handle(SOpenSlabfishInventoryMessage message, Supplier<NetworkEvent.Context> supplier) {
		supplier.get().enqueueWork(() -> ClientNetworkHandler.handleOpenSlabfishInventory(message));
		supplier.get().setPacketHandled(true);
	}

	@OnlyIn(Dist.CLIENT)
	public int getEntityId() {
		return entityId;
	}

	@OnlyIn(Dist.CLIENT)
	public int getWindowId() {
		return windowId;
	}
}