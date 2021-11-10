package com.minecraftabnormals.environmental.core.other;

import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.DataProcessors;
import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.TrackedData;
import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.TrackedDataManager;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class EnvironmentalDataProcessors {
	public static final TrackedData<Integer> TRUFFLE_HUNTING_TIME = TrackedData.Builder.create(DataProcessors.INT, () -> 0).enableSaving().build();
	public static final TrackedData<Integer> SNIFF_SOUND_TIME = TrackedData.Builder.create(DataProcessors.INT, () -> 0).build();
	public static final TrackedData<BlockPos> TRUFFLE_POS = TrackedData.Builder.create(DataProcessors.POS, () -> BlockPos.ZERO).enableSaving().build();
	public static final TrackedData<Boolean> HAS_TRUFFLE_TARGET = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).enableSaving().build();
	public static final TrackedData<Boolean> LOOKING_FOR_TRUFFLE = TrackedData.Builder.create(DataProcessors.BOOLEAN, () -> false).build();

	public static void registerTrackedData() {
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "truffle_hunting_time"), TRUFFLE_HUNTING_TIME);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "sniff_sound_time"), SNIFF_SOUND_TIME);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "truffle_pos"), TRUFFLE_POS);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "has_truffle_target"), HAS_TRUFFLE_TARGET);
		TrackedDataManager.INSTANCE.registerData(new ResourceLocation(Environmental.MOD_ID, "looking_for_truffle"), LOOKING_FOR_TRUFFLE);
	}
}