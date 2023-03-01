package com.teamabnormals.environmental.common.entity.animal.deer;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;

import java.util.Locale;

public enum DeerCoatTypes {
	NONE(0), SPOTTED(1);

	private static final DeerCoatTypes[] VALUES = values();
	private final int id;
	private final LazyLoadedValue<ResourceLocation> texture = new LazyLoadedValue<>(() -> new ResourceLocation(Environmental.MOD_ID, "textures/entity/deer/" + this.name().toLowerCase(Locale.ROOT) + "_overlay.png"));

	DeerCoatTypes(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public ResourceLocation getTexture() {
		return this.texture.get();
	}

	public static DeerCoatTypes byId(int id) {
		if (id < 0 || id >= VALUES.length) {
			id = 0;
		}
		return VALUES[id];
	}
}