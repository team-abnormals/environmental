package com.teamabnormals.environmental.common.block;

import com.teamabnormals.environmental.common.levelgen.util.WisteriaColor;
import net.minecraft.world.level.block.state.BlockState;

public interface WisteriaLeafColorBlock {
	WisteriaColor getColor();

	default boolean causesBlendTexture(BlockState state) {
		return true;
	}
}