package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalBannerPatterns {
	public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registries.BANNER_PATTERN, Environmental.MOD_ID);

	public static final RegistryObject<BannerPattern> LUMBERER = BANNER_PATTERNS.register("lumberer", () -> new BannerPattern("lbr"));
	public static final RegistryObject<BannerPattern> HELPER = BANNER_PATTERNS.register("helper", () -> new BannerPattern("hlp"));
}