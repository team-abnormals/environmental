package com.minecraftabnormals.environmental.core.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.item.HoeItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HoeItem.class)
public abstract class HoeItemMixin {

	@Redirect(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isAirBlock(Lnet/minecraft/util/math/BlockPos;)Z"))
	private boolean onItemUse(World world, BlockPos pos) {
		return world.isAirBlock(pos) || world.getBlockState(pos).isIn(Blocks.WATER);
	}
}
