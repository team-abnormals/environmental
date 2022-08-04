package com.teamabnormals.environmental.common.entity.animal.slabfish;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;

import java.util.Arrays;
import java.util.Comparator;

public enum SlabfishOverlay implements StringRepresentable {
	NONE(0, "none"),
	MUDDY(1, "mud"),
	SNOWY(2, "snow"),
	EGG(3, "egg");

	private static final SlabfishOverlay[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(SlabfishOverlay::getId)).toArray(SlabfishOverlay[]::new);
	private final int id;
	private final String name;
	private final LazyLoadedValue<ResourceLocation> textureLocation = new LazyLoadedValue<>(() -> new ResourceLocation(Environmental.MOD_ID, "overlay/" + this.getSerializedName()));
	private final LazyLoadedValue<ResourceLocation> backpackTextureLocation = new LazyLoadedValue<>(() -> new ResourceLocation(Environmental.MOD_ID, "overlay/" + this.getSerializedName() + "_backpack"));

	SlabfishOverlay(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	@Override
	public String getSerializedName() {
		return this.name;
	}

	public ResourceLocation getTextureLocation() {
		return this.textureLocation.get();
	}

	public ResourceLocation getBackpackTextureLocation() {
		return this.backpackTextureLocation.get();
	}

	public static SlabfishOverlay byId(int id) {
		if (id < 0 || id >= VALUES.length) {
			id = 0;
		}
		return VALUES[id];
	}

	public static SlabfishOverlay byTranslationKey(String key, SlabfishOverlay type) {
		for (SlabfishOverlay slabfishtype : values()) {
			if (slabfishtype.name.equals(key)) {
				return slabfishtype;
			}
		}
		return type;
	}
}