package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.blueprint.core.other.tags.BlueprintEntityTypeTags;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
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
		this.tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(REINDEER.get());
		this.tag(EntityTypeTags.AXOLOTL_HUNT_TARGETS).add(KOI.get());

		this.tag(UNAFFECTED_BY_SERENITY);
		this.tag(EnvironmentalEntityTypeTags.DEER).add(EnvironmentalEntityTypes.DEER.get(), REINDEER.get());
		this.tag(SCARES_DEER).add(EntityType.PLAYER, EntityType.VILLAGER, EntityType.WANDERING_TRADER).addTag(SCARES_TRUSTING_DEER);
		this.tag(SCARES_TRUSTING_DEER).add(EntityType.WOLF).addTag(EntityTypeTags.RAIDERS);
		this.tag(ZEBRAS_DONT_KICK).add(EntityType.HORSE, EntityType.DONKEY, EntityType.MULE, ZEBRA.get());

		this.tag(BlueprintEntityTypeTags.MILKABLE).add(YAK.get());
		this.tag(BlueprintEntityTypeTags.FISHES).add(KOI.get());
	}
}