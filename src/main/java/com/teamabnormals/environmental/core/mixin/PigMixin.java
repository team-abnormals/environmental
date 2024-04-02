package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import com.teamabnormals.environmental.core.other.EnvironmentalDataProcessors;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.animal.Pig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Pig.class)
public abstract class PigMixin {

	@Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
	private void getAmbientSound(CallbackInfoReturnable<SoundEvent> info) {
		if (((IDataManager) this).getValue(EnvironmentalDataProcessors.LOOKING_FOR_TRUFFLE)) {
			info.setReturnValue(null);
		}
	}
}
