package com.minecraftabnormals.environmental.common.entity.util;

import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Comparator;

public enum SlabfishOverlay implements IStringSerializable {
	NONE(0, "none"),
	MUDDY(1, "mud"),
	SNOWY(2, "snow"),
	EGG(3, "egg");

	private static final SlabfishOverlay[] VALUES = Arrays.stream(values()).sorted(Comparator.comparingInt(SlabfishOverlay::getId)).toArray(SlabfishOverlay[]::new);
	private final int id;
	private final String name;
	private final LazyValue<ResourceLocation> textureLocation = new LazyValue<>(() -> new ResourceLocation(Environmental.MODID, "textures/entity/slabfish/overlay/" + this.getString() + ".png"));
	private final LazyValue<ResourceLocation> backpackTextureLocation = new LazyValue<>(() -> new ResourceLocation(Environmental.MODID, "textures/entity/slabfish/overlay/" + this.getString() + "_backpack.png"));

	SlabfishOverlay(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	@Override
	public String getString() {
		return this.name;
	}

	public ResourceLocation getTextureLocation() {
		return this.textureLocation.getValue();
	}

	public ResourceLocation getBackpackTextureLocation() {
		return this.backpackTextureLocation.getValue();
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