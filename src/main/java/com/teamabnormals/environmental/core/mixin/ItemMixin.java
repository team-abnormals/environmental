package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.common.block.HangingLeavesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {

	@Inject(method = "mineBlock", at = @At("RETURN"), cancellable = true)
	private void mineBlock(ItemStack stack, Level level, BlockState state, BlockPos pos, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
		if (stack.is(Tags.Items.TOOLS_SHEAR) && state.getBlock() instanceof HangingLeavesBlock) {
			cir.setReturnValue(true);
		}
	}

	@Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
	private void getDestroySpeed(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
		if (stack.is(Tags.Items.TOOLS_SHEAR) && state.getBlock() instanceof HangingLeavesBlock) {
			cir.setReturnValue(15.0F);
		}
	}
}