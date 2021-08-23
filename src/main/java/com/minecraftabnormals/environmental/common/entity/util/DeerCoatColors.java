package com.minecraftabnormals.environmental.common.entity.util;

import java.util.Arrays;
import java.util.Comparator;

public enum DeerCoatColors {
	CREAMY(0), CHESTNUT(1), GRAY(2), HOLIDAY(3);

	private static final DeerCoatColors[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(DeerCoatColors::getId)).toArray(DeerCoatColors[]::new);

	private final int id;

	DeerCoatColors(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static DeerCoatColors byId(int id) {
		if (id < 0 || id >= VALUES.length) {
			id = 0;
		}
		return VALUES[id];
	}
}