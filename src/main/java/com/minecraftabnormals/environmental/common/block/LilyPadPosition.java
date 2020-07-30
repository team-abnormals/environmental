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

	private LilyPadPosition(String nameIn) {
		this.heightName = nameIn;
	}

	public String toString() {
		return this.getString();
	}

	public String getString() {
		return this.heightName;
	}
}
