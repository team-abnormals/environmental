package com.teamabnormals.environmental.common.network.message;

import com.teamabnormals.environmental.common.entity.animal.Zebra;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public final class C2SZebraJumpMessage {
	private float strength;

	public C2SZebraJumpMessage(float strength) {
		this.strength = strength;
	}

	public void serialize(FriendlyByteBuf buf) {
		buf.writeFloat(this.strength);
	}

	public static C2SZebraJumpMessage deserialize(FriendlyByteBuf buf) {
		return new C2SZebraJumpMessage(buf.readFloat());
	}

	public static void handle(C2SZebraJumpMessage message, Supplier<Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		if (context.getDirection().getReceptionSide() == LogicalSide.SERVER) {
			context.enqueueWork(() -> {
				Player player = context.getSender();
				if (player != null) {
					if (player.getVehicle() instanceof Zebra zebra) {
						zebra.setJumpStrength(message.strength);
					}
				}
			});
			context.setPacketHandled(true);
		}
	}
}