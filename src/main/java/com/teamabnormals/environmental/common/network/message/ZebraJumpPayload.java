package com.teamabnormals.environmental.common.network.message;

import com.teamabnormals.environmental.common.entity.animal.zebroid.Zebroid;
import com.teamabnormals.environmental.core.Environmental;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ZebraJumpPayload(float strength) implements CustomPacketPayload {
	public static final CustomPacketPayload.Type<ZebraJumpPayload> TYPE = new CustomPacketPayload.Type<>(Environmental.location("zebra_jump"));

	public static final StreamCodec<ByteBuf, ZebraJumpPayload> STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.FLOAT, ZebraJumpPayload::strength,
			ZebraJumpPayload::new
	);

	public static void handle(ZebraJumpPayload payload, IPayloadContext context) {
		if (context.connection().getDirection() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> {
				if (context.player().getVehicle() instanceof Zebroid zebra) {
					zebra.setJumpStrength(payload.strength);
				}
			});
		}
	}

	@Override
	public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}