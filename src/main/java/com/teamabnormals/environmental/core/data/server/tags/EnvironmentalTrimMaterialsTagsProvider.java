package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.blueprint.core.other.tags.BlueprintTrimMaterialTags;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalTrimMaterials;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class EnvironmentalTrimMaterialsTagsProvider extends TagsProvider<TrimMaterial> {

	public EnvironmentalTrimMaterialsTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
		super(output, Registries.TRIM_MATERIAL, provider, Environmental.MOD_ID, helper);
	}

	@Override
	public void addTags(HolderLookup.Provider provider) {
		this.tag(BlueprintTrimMaterialTags.GENERATES_OVERRIDES).add(EnvironmentalTrimMaterials.BOG_IRON);
	}

}
