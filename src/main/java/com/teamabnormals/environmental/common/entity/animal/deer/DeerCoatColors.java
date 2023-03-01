package com.teamabnormals.environmental.common.entity.animal.deer;

public enum DeerCoatColors {
	CREAMY(0), CHESTNUT(1), GRAY(2), HOLIDAY(3);

	private static final DeerCoatColors[] VALUES = values();
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