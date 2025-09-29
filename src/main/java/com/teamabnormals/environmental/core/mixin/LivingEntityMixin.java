package com.teamabnormals.environmental.core.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamabnormals.environmental.common.entity.animal.MuddyPig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@WrapOperation(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"))
	private float getFriction(BlockState state, LevelReader level, BlockPos pos, Entity entity, Operation<Float> original) {
		if (entity instanceof Pig pig && MuddyPig.isMuddy(pig) && MuddyPig.getDryingTime(pig) > 0) {
			return 0.999F;
		}

		return original.call(state, level, pos, entity);
	}

	@Shadow
	@Final
	private Map<MobEffect, MobEffectInstance> activeEffects;

	@Inject(method = "getActiveEffects", at = @At("HEAD"), cancellable = true)
	private void getActiveEffects(CallbackInfoReturnable<Collection<MobEffectInstance>> cir) {
		if (this.activeEffects == null) {
			cir.setReturnValue(null);
		}
	}
}