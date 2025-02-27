package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBannerPatternTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBannerPatterns;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BannerPatternTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class EnvironmentalBannerPatternTagsProvider extends BannerPatternTagsProvider {

	public EnvironmentalBannerPatternTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Environmental.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.tag(EnvironmentalBannerPatternTags.PATTERN_ITEM_LUMBERER).add(EnvironmentalBannerPatterns.LUMBERER.getKey());
		this.tag(EnvironmentalBannerPatternTags.PATTERN_ITEM_HELPER).add(EnvironmentalBannerPatterns.HELPER.getKey());
	}
}