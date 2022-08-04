package com.teamabnormals.environmental.common.block;

import net.minecraft.util.StringRepresentable;

public enum LilyPadPosition implements StringRepresentable {
	CENTER("center"),
	NORTH("north"),
	NORTHEAST("northeast"),
	EAST("east"),
	SOUTHEAST("southeast"),
	SOUTH("south"),
	SOUTHWEST("southwest"),
	WEST("west"),
	NORTHWEST("northwest");

	private final String heightName;

	LilyPadPosition(String nameIn) {
		this.heightName = nameIn;
	}

	public String toString() {
		return this.getSerializedName();
	}

	public String getSerializedName() {
		return this.heightName;
	}
}
