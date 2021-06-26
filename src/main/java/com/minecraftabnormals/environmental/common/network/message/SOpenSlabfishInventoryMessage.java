package com.minecraftabnormals.environmental.common.network.message;

import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.network.ClientNetworkHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SOpenSlabfishInventoryMessage {
	private int entityId;
	private int windowId;

	private SOpenSlabfishInventoryMessage(int entityId, int windowId) {
		this.entityId = entityId;
		this.windowId = windowId;
	}

	public SOpenSlabfishInventoryMessage(SlabfishEntity slabfishEntity, int windowId) {
		this(slabfishEntity.getId(), windowId);
	}

	public static void serialize(SOpenSlabfishInventoryMessage message, PacketBuffer buffer) {
		buffer.writeVarInt(message.entityId);
		buffer.writeVarInt(message.windowId);
	}

	public static SOpenSlabfishInventoryMessage deserialize(PacketBuffer buffer) {
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