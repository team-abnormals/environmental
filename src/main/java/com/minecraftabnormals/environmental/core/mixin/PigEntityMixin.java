package com.minecraftabnormals.environmental.core.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.minecraftabnormals.abnormals_core.common.world.storage.tracking.IDataManager;
import com.minecraftabnormals.environmental.core.other.EnvironmentalDataProcessors;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

@Mixin(PigEntity.class)
public abstract class PigEntityMixin extends AnimalEntity {

	protected PigEntityMixin(EntityType<? extends AnimalEntity> type, World worldIn) {
		super(type, worldIn);
	}
	
	@Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
	private void getAmbientSound(CallbackInfoReturnable<SoundEvent> info) {
		if (((IDataManager) this).getValue(EnvironmentalDataProcessors.LOOKING_FOR_TRUFFLE)) {
			info.setReturnValue(null);
		}
	}
}
