package com.teamabnormals.environmental.core.data.client;

import com.teamabnormals.blueprint.core.api.BlueprintTrims;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalTrimPatterns;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class EnvironmentalSpriteSourceProvider extends SpriteSourceProvider {

	public EnvironmentalSpriteSourceProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Environmental.MOD_ID, helper);
	}

	@Override
	protected void gather() {
		this.atlas(BLOCKS_ATLAS)
				.addSource(new SingleFile(Environmental.location("item/slabfish_sweater_slot"), Optional.empty()))
				.addSource(new SingleFile(Environmental.location("item/slabfish_backpack_slot"), Optional.empty()))
				.addSource(new SingleFile(Environmental.location("item/slabfish_backpack_type_slot"), Optional.empty()));

		this.atlas(BlueprintTrims.ARMOR_TRIMS_ATLAS).addSource(BlueprintTrims.patternPermutationsOfVanillaMaterials(EnvironmentalTrimPatterns.KEEPER));
	}
}