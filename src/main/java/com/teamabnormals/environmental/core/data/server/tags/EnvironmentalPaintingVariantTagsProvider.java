package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.PaintingVariantTagsProvider;
import net.minecraft.tags.PaintingVariantTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.teamabnormals.environmental.core.registry.EnvironmentalPaintingTypes.SLABFISH;
import static com.teamabnormals.environmental.core.registry.EnvironmentalPaintingTypes.SNAKE_BLOCK;

public class EnvironmentalPaintingVariantTagsProvider extends PaintingVariantTagsProvider {

	public EnvironmentalPaintingVariantTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Environmental.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.tag(PaintingVariantTags.PLACEABLE).add(SNAKE_BLOCK.get(), SLABFISH.get());
	}
}