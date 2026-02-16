package com.teamabnormals.environmental.core.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.AlterGroundDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AlterGroundDecorator.class)
public class AlterGroundDecoratorMixin {
	// Can't use Forge event because vanilla's state provider is bugged
	@WrapOperation(method = "Lnet/minecraft/world/level/levelgen/feature/treedecorators/AlterGroundDecorator;placeBlockAt(Lnet/minecraft/world/level/levelgen/feature/treedecorators/TreeDecorator$Context;Lnet/minecraft/core/BlockPos;Lnet/neoforged/neoforge/event/level/AlterGroundEvent$StateProvider;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/feature/treedecorators/TreeDecorator$Context;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)V"))
	private void fixStateProviderPosition(TreeDecorator.Context context, BlockPos pos, BlockState state, Operation<Void> original) {
		if (state.is(Blocks.PODZOL)) {
			LevelSimulatedReader level = context.level();
			if (level.isStateAtPosition(pos, stateAtPos -> stateAtPos.is(Blocks.MUD))) {
				context.setBlock(pos, EnvironmentalBlocks.MUDDY_PODZOL.get().defaultBlockState());
				return;
			} else if (level.isStateAtPosition(pos, stateAtPos -> stateAtPos.is(EnvironmentalBlocks.MUDDY_PODZOL.get()))) {
				return;
			}
		}
		original.call(context, pos, state);
	}
}
