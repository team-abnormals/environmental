package com.teamabnormals.environmental.core.other;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class EnvironmentalModelLayers {
	public static final ModelLayerLocation DEER = register("deer");
	public static final ModelLayerLocation REINDEER = register("reindeer");
	public static final ModelLayerLocation DUCK = register("duck");
	public static final ModelLayerLocation KOI = register("koi");
	public static final ModelLayerLocation SLABFISH = register("slabfish");
	public static final ModelLayerLocation TAPIR = register("tapir");
	public static final ModelLayerLocation YAK = register("yak");
	public static final ModelLayerLocation ZEBRA = register("zebra");
	public static final ModelLayerLocation ZORSE = register("zorse");
	public static final ModelLayerLocation ZONKEY = register("zonkey");
	public static final ModelLayerLocation ZORSE_ARMOR = register("zorse_armor");
	public static final ModelLayerLocation MULE_ARMOR = register("mule_armor");
	public static final ModelLayerLocation PINECONE_GOLEM = register("pinecone_golem");

	public static ModelLayerLocation register(String name) {
		return register(name, "main");
	}

	public static ModelLayerLocation register(String name, String layer) {
		return new ModelLayerLocation(new ResourceLocation(Environmental.MOD_ID, name), layer);
	}
}