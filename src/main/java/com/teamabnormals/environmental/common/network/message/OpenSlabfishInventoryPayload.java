package com.teamabnormals.environmental.common.network.message;

import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.network.ClientNetworkHandler;
import com.teamabnormals.environmental.core.Environmental;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenSlabfishInventoryPayload(int entityId, int windowId) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<OpenSlabfishInventoryPayload> TYPE = new CustomPacketPayload.Type<>(Environmental.location("open_slabfish_inventory"));

	public static final StreamCodec<ByteBuf, OpenSlabfishInventoryPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.INT, OpenSlabfishInventoryPayload::entityId,
			ByteBufCodecs.INT, OpenSlabfishInventoryPayload::windowId,
			OpenSlabfishInventoryPayload::new
	);

	public OpenSlabfishInventoryPayload(Slabfish slabfish, int windowId) {
		this(slabfish.getId(), windowId);
	}

	public static void handle(OpenSlabfishInventoryPayload payload, IPayloadContext context) {
		context.enqueueWork(() -> ClientNetworkHandler.handleOpenSlabfishInventory(payload));
	}

	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}