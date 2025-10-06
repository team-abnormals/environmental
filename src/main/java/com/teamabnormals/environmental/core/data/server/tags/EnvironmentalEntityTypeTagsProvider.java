package com.teamabnormals.environmental.core.data.server.tags;

import com.teamabnormals.blueprint.core.other.tags.BlueprintEntityTypeTags;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.environmental.core.other.tags.EnvironmentalEntityTypeTags.*;
import static com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes.*;

public class EnvironmentalEntityTypeTagsProvider extends EntityTypeTagsProvider {

	public EnvironmentalEntityTypeTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, Environmental.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.tag(EntityTypeTags.FALL_DAMAGE_IMMUNE).add(SLABFISH.get(), DUCK.get(), PINECONE_GOLEM.get());
		this.tag(EntityTypeTags.IMPACT_PROJECTILES).add(MUD_BALL.get(), DUCK_EGG.get());
		this.tag(EntityTypeTags.POWDER_SNOW_WALKABLE_MOBS).add(REINDEER.get());

		this.tag(UNAFFECTED_BY_SERENITY);
		this.tag(EnvironmentalEntityTypeTags.DEER).add(EnvironmentalEntityTypes.DEER.get(), REINDEER.get());
		this.tag(ZEBROIDS).add(ZEBRA.get(), ZORSE.get(), ZONKEY.get());
		this.tag(SCARES_DEER).add(EntityType.PLAYER, EntityType.VILLAGER, EntityType.WANDERING_TRADER).addTag(SCARES_TRUSTING_DEER);
		this.tag(SCARES_TRUSTING_DEER).add(EntityType.WOLF).addTag(EntityTypeTags.RAIDERS);
		this.tag(ZEBROIDS_DONT_KICK).addTag(ZEBROIDS).add(EntityType.HORSE, EntityType.DONKEY, EntityType.MULE, EntityType.ZOMBIE_HORSE, EntityType.SKELETON_HORSE);

		this.tag(BlueprintEntityTypeTags.MILKABLE).add(YAK.get());
		this.tag(BlueprintEntityTypeTags.FISHES).add(KOI.get());

		this.tag(HORSE_FEED_TEMPTED).addTag(ZEBROIDS);
		this.tag(HORSE_FEED_USERS).addTag(ZEBROIDS);
	}
}