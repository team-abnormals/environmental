package com.teamabnormals.environmental.core.registry.slabfish;

import com.teamabnormals.environmental.common.entity.animal.slabfish.SlabfishOverlay;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.EnvironmentalRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.Optional;

public class EnvironmentalSlabfishOverlays {
	public static final ResourceKey<SlabfishOverlay> MUD = create("mud");
	public static final ResourceKey<SlabfishOverlay> SNOW = create("snow");
	public static final ResourceKey<SlabfishOverlay> EGG = create("egg");

	public static void bootstrap(BootstrapContext<SlabfishOverlay> context) {
		register(context, MUD, true, EnvironmentalItems.MUD_BALL.get());
		register(context, SNOW, true, Items.SNOWBALL);
		register(context, EGG, false, Tags.Items.EGGS);
	}

	public static Reference<SlabfishOverlay> register(BootstrapContext<SlabfishOverlay> context, ResourceKey<SlabfishOverlay> key, boolean backpack, Optional<Holder<Item>> itemHolder, Optional<TagKey<Item>> itemTagKey) {
		return context.register(key, new SlabfishOverlay(
				ResourceLocation.fromNamespaceAndPath(key.location().getNamespace(), "overlay/" + key.location().getPath()),
				!backpack ? Optional.empty() : Optional.of(ResourceLocation.fromNamespaceAndPath(key.location().getNamespace(), "overlay/" + key.location().getPath() + "_backpack")),
				itemHolder, itemTagKey)
		);
	}

	public static Reference<SlabfishOverlay> register(BootstrapContext<SlabfishOverlay> context, ResourceKey<SlabfishOverlay> key, boolean backpack, Item item) {
		return register(context, key, backpack, Optional.of(BuiltInRegistries.ITEM.wrapAsHolder(item)), Optional.empty());
	}

	public static Reference<SlabfishOverlay> register(BootstrapContext<SlabfishOverlay> context, ResourceKey<SlabfishOverlay> key, boolean backpack, TagKey<Item> tag) {
		return register(context, key, backpack, Optional.empty(), Optional.of(tag));
	}

	public static ResourceKey<SlabfishOverlay> create(String name) {
		return ResourceKey.create(EnvironmentalRegistries.SLABFISH_OVERLAY, Environmental.location(name));
	}
}
