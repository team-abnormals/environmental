package com.teamabnormals.environmental.common.block;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class HibiscusBlock extends AbstractHibiscusBlock {
	private final Block wallHibiscus;

	public HibiscusBlock(Supplier<MobEffect> stewEffect, int stewEffectDuration, Block wallHibiscus, Properties properties) {
		super(stewEffect, stewEffectDuration, properties);
		this.wallHibiscus = wallHibiscus;
	}

	@Override
	protected Block getWallHibiscus() {
		return this.wallHibiscus;
	}
}