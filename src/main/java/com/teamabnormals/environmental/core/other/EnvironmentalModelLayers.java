package com.teamabnormals.environmental.core.other;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class EnvironmentalModelLayers {
	public static final ModelLayerLocation DEER = register("deer");
	public static final ModelLayerLocation REINDEER = register("reindeer");
	public static final ModelLayerLocation DUCK = register("duck");
	public static final ModelLayerLocation FENNEC_FOX = register("fennec_fox");
	public static final ModelLayerLocation KOI = register("koi");
	public static final ModelLayerLocation SLABFISH = register("slabfish");
	public static final ModelLayerLocation TAPIR = register("tapir");
	public static final ModelLayerLocation YAK = register("yak");

	public static final ModelLayerLocation THIEF_HOOD = register("thief_hood", "armor");
	public static final ModelLayerLocation HEALER_POUCH = register("healer_pouch", "armor");
	public static final ModelLayerLocation ARCHITECT_BELT = register("architect_belt", "armor");
	public static final ModelLayerLocation WANDERER_BOOTS = register("wanderer_boots", "armor");

	public static ModelLayerLocation register(String name) {
		return register(name, "main");
	}

	public static ModelLayerLocation register(String name, String layer) {
		return new ModelLayerLocation(new ResourceLocation(Environmental.MOD_ID, name), layer);
	}
}