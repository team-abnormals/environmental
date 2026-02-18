package com.teamabnormals.environmental.core.data.client;

import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.datapack.EnvironmentalKoiVariants;
import com.teamabnormals.environmental.core.registry.datapack.slabfish.EnvironmentalSlabfishVariants;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import static com.teamabnormals.environmental.core.registry.EnvironmentalItems.*;

public class EnvironmentalItemModelProvider extends BlueprintItemModelProvider {

	public EnvironmentalItemModelProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, Environmental.MOD_ID, helper);
	}

	@Override
	protected void registerModels() {
		this.generatedItem(
				WILLOW_BOAT.getFirst(), WILLOW_BOAT.getSecond(), WILLOW_FURNACE_BOAT, LARGE_WILLOW_BOAT,
				PINE_BOAT.getFirst(), PINE_BOAT.getSecond(), PINE_FURNACE_BOAT, LARGE_PINE_BOAT,
				CEDAR_BOAT.getFirst(), CEDAR_BOAT.getSecond(), CEDAR_FURNACE_BOAT, LARGE_CEDAR_BOAT,
				WISTERIA_BOAT.getFirst(), WISTERIA_BOAT.getSecond(), WISTERIA_FURNACE_BOAT, LARGE_WISTERIA_BOAT,
				PLUM_BOAT.getFirst(), PLUM_BOAT.getSecond(), PLUM_FURNACE_BOAT, LARGE_PLUM_BOAT,
				LUMBERER_BANNER_PATTERN, HELPER_BANNER_PATTERN,
				CATTAIL_FLUFF, EnvironmentalBlocks.CATTAIL,
				CHERRIES, PLUM,
				VENISON, COOKED_VENISON,
				DUCK_EGG, DUCK, COOKED_DUCK,
				TRUFFLE, YAK_HAIR, KOI, MUD_BALL,
				MUSIC_DISC_LEAVING_HOME, MUSIC_DISC_SLABRAVE,
				KEEPER_ARMOR_TRIM_SMITHING_TEMPLATE
		);

		this.spawnEggItem(SLABFISH_SPAWN_EGG, DUCK_SPAWN_EGG, DEER_SPAWN_EGG, REINDEER_SPAWN_EGG, YAK_SPAWN_EGG, KOI_SPAWN_EGG, TAPIR_SPAWN_EGG, ZEBRA_SPAWN_EGG, ZORSE_SPAWN_EGG, ZONKEY_SPAWN_EGG, PINECONE_GOLEM_SPAWN_EGG);

		this.getBuilder(name(SLABFISH_BUCKET.get()));
		Arrays.stream(EnvironmentalSlabfishVariants.class.getDeclaredFields()).forEach(field -> {
			if (Modifier.isStatic(field.getModifiers()) && ResourceKey.class.isAssignableFrom(field.getType())) {
				try {
					ResourceLocation location = ((ResourceKey<?>) field.get(null)).location().withPath(s -> "item/slabfish_bucket/" + s);
					this.withExistingParent(location.getPath(), "item/generated").texture("layer0", location);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		});

		this.getBuilder(name(KOI_BUCKET.get()));
		Arrays.stream(EnvironmentalKoiVariants.class.getDeclaredFields()).forEach(field -> {
			if (Modifier.isStatic(field.getModifiers()) && ResourceKey.class.isAssignableFrom(field.getType())) {
				try {
					ResourceLocation location = ((ResourceKey<?>) field.get(null)).location().withPath(s -> "item/koi_bucket/" + s);
					this.withExistingParent(location.getPath(), "item/generated").texture("layer0", location);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}
}