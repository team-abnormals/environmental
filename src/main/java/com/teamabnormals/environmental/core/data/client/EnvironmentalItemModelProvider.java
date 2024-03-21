package com.teamabnormals.environmental.core.data.client;

import com.teamabnormals.environmental.common.entity.animal.koi.KoiBreed;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

import static com.teamabnormals.environmental.core.registry.EnvironmentalItems.*;

public class EnvironmentalItemModelProvider extends ItemModelProvider {

	public EnvironmentalItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Environmental.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		this.generatedItem(
				PINE_BOAT.getFirst().get(), PINE_BOAT.getSecond().get(), PINE_FURNACE_BOAT.get(), LARGE_PINE_BOAT.get(),
				CATTAIL_FLUFF.get(), EnvironmentalBlocks.CATTAIL.get()
		);

		this.koiBuckets();
	}

	private void generatedItem(ItemLike... items) {
		for (ItemLike item : items)
			item(item, "generated");
	}

	private void handheldItem(ItemLike... items) {
		for (ItemLike item : items)
			item(item, "handheld");
	}

	private void item(ItemLike item, String type) {
		ResourceLocation itemName = ForgeRegistries.ITEMS.getKey(item.asItem());
		withExistingParent(itemName.getPath(), "item/" + type).texture("layer0", new ResourceLocation(this.modid, "item/" + itemName.getPath()));
	}

	private void spawnEggItem(ItemLike... items) {
		for (ItemLike item : items) {
			ResourceLocation itemName = ForgeRegistries.ITEMS.getKey(item.asItem());
			withExistingParent(itemName.getPath(), "item/template_spawn_egg");
		}
	}

	private void blockItem(Block block) {
		ResourceLocation name = ForgeRegistries.BLOCKS.getKey(block);
		this.getBuilder(name.getPath()).parent(new UncheckedModelFile(new ResourceLocation(this.modid, "block/" + name.getPath())));
	}

	private void koiBuckets() {
		for (KoiBreed breed : KoiBreed.values()) {
			String path = "item/" + ForgeRegistries.ITEMS.getKey(EnvironmentalItems.KOI_BUCKET.get()).getPath() + "/" + breed.name().toLowerCase(Locale.ROOT);
			withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(this.modid, path));
		}
	}
}