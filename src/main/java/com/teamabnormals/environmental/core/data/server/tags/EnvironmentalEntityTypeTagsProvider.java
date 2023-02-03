package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.blueprint.core.other.tags.BlueprintEntityTypeTags;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags.*;
import static com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes.*;

public class EnvironmentalEntityTypeTagsProvider extends EntityTypeTagsProvider {

	public EnvironmentalEntityTypeTagsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Environmental.MOD_ID, existingFileHelper);
	}

	@Override
	public void addTags() {
		this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(MUD_BALL.get(), DUCK_EGG.get());

		this.tag(SCARES_DEER).add(EntityType.PLAYER, EntityType.VILLAGER, EntityType.WANDERING_TRADER).addTag(SCARES_TRUSTING_DEER);
		this.tag(SCARES_TRUSTING_DEER).add(EntityType.WOLF, ZOMBIE_DEER.get()).addTag(EntityTypeTags.RAIDERS);
		this.tag(UNAFFECTED_BY_SERENITY);

		this.tag(BlueprintEntityTypeTags.MILKABLE).add(YAK.get());
		this.tag(BlueprintEntityTypeTags.FISHES).add(KOI.get());
	}
}