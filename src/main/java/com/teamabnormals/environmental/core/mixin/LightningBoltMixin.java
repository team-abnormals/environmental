package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.common.block.SlabfishEffigyBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightningBolt.class)
public abstract class LightningBoltMixin extends Entity {

	@Shadow
	protected abstract BlockPos getStrikePosition();

	public LightningBoltMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "powerLightningRod", at = @At("TAIL"))
	public void powerLightningRod(CallbackInfo ci) {
		BlockPos pos = this.getStrikePosition();
		BlockState state = this.level().getBlockState(pos);
		if (state.getBlock() instanceof SlabfishEffigyBlock effigy) {
			effigy.onLightningStrike(state, this.level(), pos);
		}

		BlockPos belowPos = this.getStrikePosition().below();
		BlockState belowState = this.level().getBlockState(belowPos);
		if (belowState.getBlock() instanceof SlabfishEffigyBlock effigy) {
			effigy.onLightningStrike(belowState, this.level(), belowPos);
		}
	}
}
