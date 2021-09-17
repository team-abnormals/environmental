package com.minecraftabnormals.environmental.common.block;

import net.minecraft.util.IStringSerializable;

public enum LilyPadPosition implements IStringSerializable {
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
