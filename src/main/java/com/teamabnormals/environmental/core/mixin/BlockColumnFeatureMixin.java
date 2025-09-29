package com.teamabnormals.environmental.core.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.teamabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.BlockColumnFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockColumnFeature.class)
public abstract class BlockColumnFeatureMixin {

	@WrapOperation(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	private boolean place(WorldGenLevel level, BlockPos pos, BlockState state, int i, Operation<Boolean> original) {
		if (EnvironmentalConfig.COMMON.cactusBobble.get() && state.is(EnvironmentalBlockTags.CACTUS_BOBBLE_PLANTABLE_ON)) {
			level.setBlock(pos.above(), EnvironmentalBlocks.CACTUS_BOBBLE.get().defaultBlockState(), i);
		}
		return original.call(level, pos, state, i);
	}
}
