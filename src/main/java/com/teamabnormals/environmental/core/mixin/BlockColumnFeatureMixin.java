package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.BlockColumnFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockColumnFeature.class)
public abstract class BlockColumnFeatureMixin {

	@Redirect(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	private boolean place(WorldGenLevel level, BlockPos pos, BlockState state, int i) {
		if (EnvironmentalConfig.COMMON.cactusBobble.get() && state.is(EnvironmentalBlockTags.CACTUS_BOBBLE_PLANTABLE_ON)) {
			level.setBlock(pos.above(), EnvironmentalBlocks.CACTUS_BOBBLE.get().defaultBlockState(), i);
		}
		return level.setBlock(pos, state, i);
	}
}
