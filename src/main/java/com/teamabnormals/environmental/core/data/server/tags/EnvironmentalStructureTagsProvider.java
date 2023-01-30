package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalStructureTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ConfiguredStructureTagsProvider;
import net.minecraft.tags.ConfiguredStructureTags;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EnvironmentalStructureTagsProvider extends ConfiguredStructureTagsProvider {

	public EnvironmentalStructureTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Environmental.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.tag(EnvironmentalStructureTags.HAS_HEALER_POUCH).addTag(ConfiguredStructureTags.MINESHAFT).add(BuiltinStructures.STRONGHOLD);
	}
}