package com.minecraftabnormals.environmental.core.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

@Mixin(TrapDoorBlock.class)
public class TrapDoorBlockMixin {

	// Temporary fix for a Forge issue
	@Inject(at = @At("RETURN"), method = "isLadder", cancellable = true, remap = false)
	private boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
		if (state.get(TrapDoorBlock.OPEN)) {
			BlockState down = world.getBlockState(pos.down());
			if (down.getBlock() instanceof LadderBlock)
				return down.get(LadderBlock.FACING) == state.get(TrapDoorBlock.HORIZONTAL_FACING);
		}
		return false;
	}
}