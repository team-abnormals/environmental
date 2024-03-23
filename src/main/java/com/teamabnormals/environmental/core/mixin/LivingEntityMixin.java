package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import com.teamabnormals.environmental.core.other.EnvironmentalDataProcessors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"))
	private float getFriction(BlockState state, LevelReader level, BlockPos pos, Entity entity) {
		if (entity instanceof Pig pig) {
			IDataManager data = (IDataManager) pig;
			if (data.getValue(EnvironmentalDataProcessors.IS_MUDDY) && data.getValue(EnvironmentalDataProcessors.MUD_DRYING_TIME) > 0) {
				return 0.999F;
			}
		}

		return state.getFriction(level, pos, entity);
	}
}