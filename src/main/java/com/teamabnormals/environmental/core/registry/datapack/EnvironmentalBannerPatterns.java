package com.teamabnormals.environmental.core.registry.datapack;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class EnvironmentalBannerPatterns {
	public static final ResourceKey<BannerPattern> LUMBERER = create("lumberer");
	public static final ResourceKey<BannerPattern> HELPER = create("helper");

	public static void bootstrap(BootstrapContext<BannerPattern> context) {
		register(context, LUMBERER);
		register(context, HELPER);
	}

	private static ResourceKey<BannerPattern> create(String name) {
		return ResourceKey.create(Registries.BANNER_PATTERN, Environmental.location(name));
	}

	public static void register(BootstrapContext<BannerPattern> context, ResourceKey<BannerPattern> resourceKey) {
		context.register(resourceKey, new BannerPattern(resourceKey.location(), "block.minecraft.banner." + resourceKey.location().toShortLanguageKey()));
	}
}