package com.teamabnormals.environmental.core.data.client;

import com.teamabnormals.blueprint.core.data.client.BlueprintItemModelProvider;
import com.teamabnormals.environmental.common.entity.animal.koi.KoiBreed;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.slabfish.EnvironmentalSlabfishTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Locale;

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
				WISTERIA_BOAT.getFirst(), WISTERIA_BOAT.getSecond(), WISTERIA_FURNACE_BOAT, LARGE_WISTERIA_BOAT,
				PLUM_BOAT.getFirst(), PLUM_BOAT.getSecond(), PLUM_FURNACE_BOAT, LARGE_PLUM_BOAT,
				LUMBERER_BANNER_PATTERN, HELPER_BANNER_PATTERN,
				CATTAIL_FLUFF, EnvironmentalBlocks.CATTAIL,
				CHERRIES, PLUM,
				VENISON, COOKED_VENISON,
				DUCK_EGG, DUCK, COOKED_DUCK,
				TRUFFLE, YAK_HAIR, KOI, MUD_BALL,
				MUSIC_DISC_LEAVING_HOME, MUSIC_DISC_SLABRAVE
		);

		this.spawnEggItem(SLABFISH_SPAWN_EGG, DUCK_SPAWN_EGG, DEER_SPAWN_EGG, REINDEER_SPAWN_EGG, YAK_SPAWN_EGG, KOI_SPAWN_EGG, TAPIR_SPAWN_EGG, ZEBRA_SPAWN_EGG, ZORSE_SPAWN_EGG, ZONKEY_SPAWN_EGG, PINECONE_GOLEM_SPAWN_EGG);

		this.koiBuckets();

		this.getBuilder(name(SLABFISH_BUCKET.get()));
		Arrays.stream(EnvironmentalSlabfishTypes.class.getDeclaredFields()).forEach(field -> {
			if (Modifier.isStatic(field.getModifiers()) && ResourceKey.class.isAssignableFrom(field.getType())) {
				try {
					ResourceLocation location = ((ResourceKey<?>) field.get(null)).location().withPath(s -> "item/slabfish_bucket/" + s);
					this.withExistingParent(location.getPath(), "item/generated").texture("layer0", location);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		});
	}

	private void koiBuckets() {
		String path = ForgeRegistries.ITEMS.getKey(EnvironmentalItems.KOI_BUCKET.get()).getPath();
		ItemModelBuilder model = this.withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(this.modid, "item/" + path + "/" + KoiBreed.KOHAKU.name().toLowerCase(Locale.ROOT)));
		for (KoiBreed breed : KoiBreed.values()) {
			ResourceLocation name = new ResourceLocation(this.modid, "item/" + path + "/" + breed.name().toLowerCase(Locale.ROOT));
			model.override().model(new UncheckedModelFile(name)).predicate(new ResourceLocation(this.modid, "variant"), breed.getId());
			this.withExistingParent(name.getPath(), "item/generated").texture("layer0", name);
		}
	}
}