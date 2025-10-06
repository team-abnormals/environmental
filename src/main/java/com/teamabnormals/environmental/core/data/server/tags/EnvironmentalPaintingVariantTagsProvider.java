package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.environmental.core.registry.datapack.EnvironmentalPaintingVariants.*;

public class EnvironmentalPaintingVariantTagsProvider extends PaintingVariantTagsProvider {

	public EnvironmentalPaintingVariantTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Environmental.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.tag(PaintingVariantTags.PLACEABLE).add(
				SNAKE_BLOCK, SLABFISH, ARCHIVE,
				OPTIMAL_AERODYNAMICS, IN_PLAINS_SIGHT, THE_PLACE_WITHIN_THE_PINES,
				BOUQUET, BOUQUET2, LONE_PLUM, MARSHPATH
		);
	}
}