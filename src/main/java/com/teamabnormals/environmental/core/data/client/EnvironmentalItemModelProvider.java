package com.teamabnormals.environmental.core.data.client;

import com.teamabnormals.environmental.common.entity.animal.koi.KoiBreed;
import com.teamabnormals.environmental.core.Environmental;
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

public class EnvironmentalItemModelProvider extends ItemModelProvider {

	public EnvironmentalItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Environmental.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		this.koiModels();
	}

	private void generated(ItemLike item) {
		basicModel(item, "generated");
	}

	private void handheld(ItemLike item) {
		basicModel(item, "handheld");
	}

	private void basicModel(ItemLike item, String type) {
		ResourceLocation itemName = ForgeRegistries.ITEMS.getKey(item.asItem());
		withExistingParent(itemName.getPath(), "item/" + type).texture("layer0", new ResourceLocation(this.modid, "item/" + itemName.getPath()));
	}

	private void blockItem(Block block) {
		ResourceLocation name = ForgeRegistries.BLOCKS.getKey(block);
		this.getBuilder(name.getPath()).parent(new UncheckedModelFile(new ResourceLocation(this.modid, "block/" + name.getPath())));
	}

	private void koiModels() {
		for (KoiBreed breed : KoiBreed.values()) {
			String path = "item/" + ForgeRegistries.ITEMS.getKey(EnvironmentalItems.KOI_BUCKET.get()).getPath() + "/" + breed.name().toLowerCase(Locale.ROOT);
			withExistingParent(path, "item/generated").texture("layer0", new ResourceLocation(this.modid, path));
		}
	}
}