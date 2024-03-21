package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.common.world.storage.tracking.DataProcessors;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedData;
import com.teamabnormals.blueprint.common.world.storage.tracking.TrackedDataManager;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class EnvironmentalDataProcessors {
	public static final TrackedData<Integer> TRUFFLE_HUNTING_TIME = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().build();
	public static final TrackedData<Integer> SNIFF_SOUND_TIME = TrackedData.Builder.create(DataProcessors.INT, () -> 0).build();
	public static final TrackedData<BlockPos> TRUFFLE_POS = TrackedData.Builder.create(DataProcessors.POS, () -> BlockPos.ZERO).enableSaving().build();
	public static final TrackedData<Boolean> HAS_TRUFFLE_TARGET = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).enableSaving().build();
	public static final TrackedData<Boolean> LOOKING_FOR_TRUFFLE = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).build();
	public static final TrackedData<Boolean> IS_MUDDY = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).enableSaving().build();
	public static final TrackedData<Integer> MUD_DRYING_TIME = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().build();
	public static final TrackedData<ResourceLocation> MUDDY_PIG_DECORATION = TrackedData.Builder.create(DataProcessors.RESOURCE_LOCATION, () -> new ResourceLocation("empty")).enableSaving().build();

	public static void registerTrackedData() {
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "truffle_hunting_time"), TRUFFLE_HUNTING_TIME);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "sniff_sound_time"), SNIFF_SOUND_TIME);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "truffle_pos"), TRUFFLE_POS);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "has_truffle_target"), HAS_TRUFFLE_TARGET);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "looking_for_truffle"), LOOKING_FOR_TRUFFLE);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "is_muddy"), IS_MUDDY);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "mud_drying_time"), MUD_DRYING_TIME);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "muddy_pig_decoration"), MUDDY_PIG_DECORATION);
	}
}