package com.teamabnormals.environmental.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;

public class SmoothcapMossBlock extends Block {
	public static final MapCodec<SmoothcapMossBlock> CODEC = simpleCodec(SmoothcapMossBlock::new);

	public SmoothcapMossBlock(Properties properties) {
		super(properties);
	}

	@Override
	public MapCodec<SmoothcapMossBlock> codec() {
		return CODEC;
	}
}
