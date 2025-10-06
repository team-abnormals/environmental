package com.teamabnormals.environmental.common.block;

import com.teamabnormals.blueprint.common.block.BlueprintChiseledBookShelfBlock;
import net.minecraft.world.phys.Vec2;

import java.util.OptionalInt;

public class ChiseledWillowBookShelfBlock extends BlueprintChiseledBookShelfBlock {

	public ChiseledWillowBookShelfBlock(Properties properties) {
		super(properties);
	}

	@Override
	public OptionalInt getHitSlot(Vec2 vec2) {
		int i = vec2.y >= 0.5F ? 0 : 1;
		int j = getSection(vec2.x);
		return OptionalInt.of(j + i * 3);
	}

	private static int getSection(float x) {
		if (x < 0.4375F) {
			return 0;
		} else {
			return x < 0.5625F ? 1 : 2;
		}
	}
}
