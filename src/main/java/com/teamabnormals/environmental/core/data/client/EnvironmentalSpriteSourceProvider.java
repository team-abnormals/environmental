package com.teamabnormals.environmental.core.data.client;

import com.mojang.datafixers.util.Either;
import com.teamabnormals.blueprint.client.renderer.texture.atlas.BlueprintPalettedPermutations;
import com.teamabnormals.blueprint.core.api.BlueprintTrims;
import com.teamabnormals.clayworks.core.Clayworks;
import com.teamabnormals.clayworks.core.api.ClayworksTrims;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalTrimMaterials;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalTrimPatterns;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;

import java.util.HashMap;
import java.util.List;
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

		this.atlas(BlueprintTrims.ARMOR_TRIMS_ATLAS)
				.addSource(BlueprintTrims.patternPermutationsOfVanillaMaterials(EnvironmentalTrimPatterns.KEEPER))
				.addSource(BlueprintTrims.materialPatternPermutations(EnvironmentalTrimMaterials.BOG_IRON));
		this.atlas(SpriteSourceProvider.BLOCKS_ATLAS).addSource(BlueprintTrims.materialPermutationsForItemLayers(EnvironmentalTrimMaterials.BOG_IRON));
		this.atlas(ClayworksTrims.DECORATED_POT_ATLAS)
				.addSource(materialPatternPermutations(EnvironmentalTrimMaterials.BOG_IRON));
	}

	@SafeVarargs
	public static BlueprintPalettedPermutations materialPatternPermutations(ResourceKey<TrimMaterial>... keys) {
		return new BlueprintPalettedPermutations(Either.left(List.of(new DirectoryLister("entity/decorated_pot_trim_patterns", "entity/decorated_pot_trim_patterns/"))), ClayworksTrims.TRIM_PALETTE_KEY, getPermutations(keys));
	}

	@SafeVarargs
	private static HashMap<String, ResourceLocation> getPermutations(ResourceKey<TrimMaterial>... keys) {
		HashMap<String, ResourceLocation> permutations = new HashMap<>();
		for (var key : keys) {
			ResourceLocation location = key.location();
			String name = location.getNamespace() + "_" + location.getPath();
			if (location.getNamespace().equals("minecraft")) {
				name = location.getPath();
				location = ResourceLocation.fromNamespaceAndPath(Clayworks.MOD_ID, name);
			}
			permutations.put(name, location.withPath(string -> "entity/decorated_pot_trim_palettes/" + string));
		}
		return permutations;
	}
}