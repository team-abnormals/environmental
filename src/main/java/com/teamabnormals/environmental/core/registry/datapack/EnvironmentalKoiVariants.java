package com.teamabnormals.environmental.core.registry.datapack;

import com.teamabnormals.environmental.common.entity.animal.koi.KoiVariant;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.Util;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;

public class EnvironmentalKoiVariants {
	public static final ResourceKey<KoiVariant> KOHAKU = create("kohaku");
	public static final ResourceKey<KoiVariant> OGON = create("ogon");
	public static final ResourceKey<KoiVariant> KIGOI = create("kigoi");
	public static final ResourceKey<KoiVariant> TANCHO = create("tancho");
	public static final ResourceKey<KoiVariant> HIGOI = create("higoi");
	public static final ResourceKey<KoiVariant> CHAGOI = create("chagoi");
	public static final ResourceKey<KoiVariant> NARUMI_ASAGI = create("narumi_asagi");
	public static final ResourceKey<KoiVariant> TANCHO_SANKE = create("tancho_sanke");
	public static final ResourceKey<KoiVariant> PLATINUM_OGON = create("platinum_ogon");
	public static final ResourceKey<KoiVariant> ORENJI_OGON = create("orenji_ogon");
	public static final ResourceKey<KoiVariant> HI_UTSURI = create("hi_utsuri");
	public static final ResourceKey<KoiVariant> MAGOI = create("magoi");
	public static final ResourceKey<KoiVariant> KONJO_ASAGI = create("konjo_asagi");
	public static final ResourceKey<KoiVariant> OCHIBA = create("ochiba");
	public static final ResourceKey<KoiVariant> KUMONRYU = create("kumonryu");
	public static final ResourceKey<KoiVariant> AKA_MATSUBA = create("aka_matsuba");
	public static final ResourceKey<KoiVariant> DOITSU_CHAGOI = create("doitsu_chagoi");
	public static final ResourceKey<KoiVariant> KIN_SHOWA = create("kin_showa");
	public static final ResourceKey<KoiVariant> SHOWA = create("showa");
	public static final ResourceKey<KoiVariant> SHIRO_UTSURI = create("shiro_utsuri");
	public static final ResourceKey<KoiVariant> DEFAULT = KOHAKU;

	public static void bootstrap(BootstrapContext<KoiVariant> context) {
		register(context, KOHAKU);
		register(context, OGON);
		register(context, KIGOI);
		register(context, TANCHO);
		register(context, HIGOI);
		register(context, CHAGOI);
		register(context, NARUMI_ASAGI);
		register(context, TANCHO_SANKE);
		register(context, PLATINUM_OGON);
		register(context, ORENJI_OGON);
		register(context, HI_UTSURI);
		register(context, MAGOI);
		register(context, KONJO_ASAGI);
		register(context, OCHIBA);
		register(context, KUMONRYU);
		register(context, AKA_MATSUBA);
		register(context, DOITSU_CHAGOI);
		register(context, KIN_SHOWA);
		register(context, SHOWA);
		register(context, SHIRO_UTSURI);
	}

	public static void register(BootstrapContext<KoiVariant> context, ResourceKey<KoiVariant> key) {
		context.register(key, new KoiVariant(key.location().withPrefix("entity/koi/"), Component.translatable(Util.makeDescriptionId("koi_variant", key.location()))));
	}

	public static ResourceKey<KoiVariant> create(String name) {
		return ResourceKey.create(EnvironmentalRegistries.KOI_VARIANT, Environmental.location(name));
	}
}
