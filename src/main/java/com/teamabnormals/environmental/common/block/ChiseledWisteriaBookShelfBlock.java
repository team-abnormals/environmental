package com.teamabnormals.environmental.common.block;

import com.teamabnormals.blueprint.common.block.BlueprintChiseledBookShelfBlock;
import net.minecraft.world.phys.Vec2;

import java.util.OptionalInt;

public class ChiseledWisteriaBookShelfBlock extends BlueprintChiseledBookShelfBlock {

	public ChiseledWisteriaBookShelfBlock(Properties properties) {
		super(properties);
	}

	public OptionalInt getHitSlot(Vec2 vec2) {
		float x = vec2.x;
		return OptionalInt.of(x < 0.1875F ? 0 : x < 0.375F ? 1 : x < 0.5F ? 2 : x < 0.625F ? 3 : x < 0.8125F ? 4 : 5);
	}
}
