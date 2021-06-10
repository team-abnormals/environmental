package com.minecraftabnormals.environmental.common.entity.util;

import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

public enum DeerCoatTypes {
	NONE(0), SPOTTED(1);

	private static final DeerCoatTypes[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(DeerCoatTypes::getId)).toArray(DeerCoatTypes[]::new);

	private final int id;
	private final LazyValue<ResourceLocation> textureLocation = new LazyValue<>(() -> new ResourceLocation(Environmental.MOD_ID, "textures/entity/deer/" + this.name().toLowerCase(Locale.ROOT) + "_overlay.png"));

	DeerCoatTypes(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public ResourceLocation getTextureLocation() {
		return this.textureLocation.getValue();
	}

	public static DeerCoatTypes byId(int id) {
		if (id < 0 || id >= VALUES.length) {
			id = 0;
		}
		return VALUES[id];
	}
}