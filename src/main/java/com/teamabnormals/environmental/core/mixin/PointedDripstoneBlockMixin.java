package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {
	@Inject(method = "maybeTransferFluid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/PointedDripstoneBlock;findFillableCauldronBelowStalactiteTip(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/material/Fluid;)Lnet/minecraft/core/BlockPos;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILSOFT, allow = 1, cancellable = true)
	private static void transformMuddySandToMud(BlockState state, ServerLevel level, BlockPos pos, float randChance, CallbackInfo info, Optional<PointedDripstoneBlock.FluidInfo> fluidInfoOptional, Fluid fluid, float f, FluidType.DripstoneDripInfo dripInfo, BlockPos tip) {
		var fluidInfo = fluidInfoOptional.get();
		if (fluidInfo.fluid() != Fluids.WATER) return;
		BlockState sourceState = fluidInfo.sourceState();
		BlockState newState;
		if (sourceState.is(EnvironmentalBlocks.MUDDY_SAND)) {
			newState = Blocks.MUD.defaultBlockState();
		} else if (sourceState.is(EnvironmentalBlocks.MUDDY_PODZOL)) {
			newState = Blocks.CLAY.defaultBlockState();
		} else return;
		level.setBlockAndUpdate(fluidInfo.pos(), newState);
		Block.pushEntitiesUp(sourceState, newState, level, fluidInfo.pos());
		level.gameEvent(GameEvent.BLOCK_CHANGE, fluidInfo.pos(), GameEvent.Context.of(newState));
		level.levelEvent(1504, tip, 0);
		info.cancel();
	}

	@Inject(method = "Lnet/minecraft/world/level/block/PointedDripstoneBlock;lambda$getFluidAboveStalactite$11(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/PointedDripstoneBlock$FluidInfo;", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"), locals = LocalCapture.CAPTURE_FAILSOFT, allow = 1, cancellable = true)
	private static void getConvertibleBlockAboveStalactite(Level level, BlockPos pos, CallbackInfoReturnable<PointedDripstoneBlock.FluidInfo> info, BlockPos above, BlockState aboveState) {
		if ((aboveState.is(EnvironmentalBlocks.MUDDY_SAND) || aboveState.is(EnvironmentalBlocks.MUDDY_PODZOL)) && !level.dimensionType().ultraWarm())
			info.setReturnValue(new PointedDripstoneBlock.FluidInfo(above, Fluids.WATER, aboveState));
	}
}
