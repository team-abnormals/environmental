package com.teamabnormals.environmental.core.data.client;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SpriteSourceProvider;

import java.util.Optional;

public final class EnvironmentalSpriteSourceProvider extends SpriteSourceProvider {

	public EnvironmentalSpriteSourceProvider(PackOutput output, ExistingFileHelper helper) {
		super(output, helper, Environmental.MOD_ID);
	}

	@Override
	protected void addSources() {
		this.atlas(BLOCKS_ATLAS)
				.addSource(new SingleFile(Environmental.location("item/slabfish_sweater_slot"), Optional.empty()))
				.addSource(new SingleFile(Environmental.location("item/slabfish_backpack_slot"), Optional.empty()))
				.addSource(new SingleFile(Environmental.location("item/slabfish_backpack_type_slot"), Optional.empty()));

	}
}