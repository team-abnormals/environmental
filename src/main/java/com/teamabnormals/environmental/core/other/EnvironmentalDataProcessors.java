package com.teamabnormals.environmental.core.other;

import com.mojang.serialization.Codec;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;

public class EnvironmentalDataProcessors {
	public static final TrackedData<Integer> TRUFFLE_HUNTING_TIME = TrackedData.Builder.create(ByteBufCodecs.INT, () -> 0).enableSaving(Codec.INT.fieldOf("Integer")).build();
	public static final TrackedData<Integer> SNIFF_SOUND_TIME = TrackedData.Builder.create(ByteBufCodecs.INT, () -> 0).build();
	public static final TrackedData<BlockPos> TRUFFLE_POS = TrackedData.Builder.create(BlockPos.STREAM_CODEC, () -> BlockPos.ZERO).enableSaving(BlockPos.CODEC.fieldOf("pos")).build();
	public static final TrackedData<Boolean> HAS_TRUFFLE_TARGET = TrackedData.Builder.create(ByteBufCodecs.BOOL, () -> false).enableSaving(Codec.BOOL.fieldOf("Boolean")).build();
	public static final TrackedData<Boolean> LOOKING_FOR_TRUFFLE = TrackedData.Builder.create(ByteBufCodecs.BOOL, () -> false).build();
	public static final TrackedData<Boolean> IS_MUDDY = TrackedData.Builder.create(ByteBufCodecs.BOOL, () -> false).enableSaving(Codec.BOOL.fieldOf("Boolean")).build();
	public static final TrackedData<Integer> MUD_DRYING_TIME = TrackedData.Builder.create(ByteBufCodecs.INT, () -> 0).enableSaving(Codec.INT.fieldOf("Integer")).build();
	public static final TrackedData<ResourceLocation> MUDDY_PIG_DECORATION = TrackedData.Builder.create(ResourceLocation.STREAM_CODEC, () -> ResourceLocation.parse("empty")).enableSaving(ResourceLocation.CODEC.fieldOf("ResourceLocation")).build();

	public static void registerTrackedData() {
		TrackedDataManager.INSTANCE.registerData(Environmental.location("truffle_hunting_time"), TRUFFLE_HUNTING_TIME);
		TrackedDataManager.INSTANCE.registerData(Environmental.location("sniff_sound_time"), SNIFF_SOUND_TIME);
		TrackedDataManager.INSTANCE.registerData(Environmental.location("truffle_pos"), TRUFFLE_POS);
		TrackedDataManager.INSTANCE.registerData(Environmental.location("has_truffle_target"), HAS_TRUFFLE_TARGET);
		TrackedDataManager.INSTANCE.registerData(Environmental.location("looking_for_truffle"), LOOKING_FOR_TRUFFLE);
		TrackedDataManager.INSTANCE.registerData(Environmental.location("is_muddy"), IS_MUDDY);
		TrackedDataManager.INSTANCE.registerData(Environmental.location("mud_drying_time"), MUD_DRYING_TIME);
		TrackedDataManager.INSTANCE.registerData(Environmental.location("muddy_pig_decoration"), MUDDY_PIG_DECORATION);
	}
}