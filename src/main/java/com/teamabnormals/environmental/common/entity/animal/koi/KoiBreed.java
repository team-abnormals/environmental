package com.teamabnormals.environmental.common.entity.animal.koi;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;

import java.util.Locale;

public enum KoiBreed {
	KOHAKU(0), OGON(1), KIGOI(2), TANCHO(3),
	HIGOI(4), CHAGOI(5), NARUMI_ASAGI(6), TANCHO_SANKE(7),
	PLATINUM_OGON(8), ORENJI_OGON(9), HI_UTSURI(10), MAGOI(11),
	KONJO_ASAGI(12), OCHIBA(13), KUMONRYU(14), AKA_MATSUBA(15),
	DOITSU_CHAGOI(16), KIN_SHOWA(17), SHOWA(18), SHIRO_UTSURI(19);

	private static final KoiBreed[] VALUES = values();

	private final int id;
	private final LazyLoadedValue<ResourceLocation> textureLocation = new LazyLoadedValue<>(() -> new ResourceLocation(Environmental.MOD_ID, "textures/entity/koi/" + this.name().toLowerCase(Locale.ROOT) + ".png"));

	KoiBreed(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public ResourceLocation getTextureLocation() {
		return this.textureLocation.get();
	}

	public static KoiBreed byId(int id) {
		if (id < 0 || id >= VALUES.length) {
			id = 0;
		}
		return VALUES[id];
	}
}