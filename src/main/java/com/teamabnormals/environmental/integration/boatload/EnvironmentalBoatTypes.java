package com.teamabnormals.environmental.integration.boatload;

import com.teamabnormals.boatload.common.item.FurnaceBoatItem;
import com.teamabnormals.boatload.common.item.LargeBoatItem;
import com.teamabnormals.boatload.core.api.BoatloadBoatType;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class EnvironmentalBoatTypes {
	public static final BoatloadBoatType WILLOW = BoatloadBoatType.register(BoatloadBoatType.create(new ResourceLocation(Environmental.MOD_ID, "willow"), () -> EnvironmentalBlocks.WILLOW_PLANKS.get().asItem(), () -> EnvironmentalItems.WILLOW_BOAT.getFirst().get(), () -> EnvironmentalItems.WILLOW_BOAT.getSecond().get(), () -> EnvironmentalItems.WILLOW_FURNACE_BOAT.get(), () -> EnvironmentalItems.LARGE_WILLOW_BOAT.get()));
	public static final BoatloadBoatType WISTERIA = BoatloadBoatType.register(BoatloadBoatType.create(new ResourceLocation(Environmental.MOD_ID, "wisteria"), () -> EnvironmentalBlocks.WISTERIA_PLANKS.get().asItem(), () -> EnvironmentalItems.WISTERIA_BOAT.getFirst().get(), () -> EnvironmentalItems.WILLOW_BOAT.getSecond().get(), () -> EnvironmentalItems.WISTERIA_FURNACE_BOAT.get(), () -> EnvironmentalItems.LARGE_WISTERIA_BOAT.get()));
	public static final BoatloadBoatType CHERRY = BoatloadBoatType.register(BoatloadBoatType.create(new ResourceLocation(Environmental.MOD_ID, "cherry"), () -> EnvironmentalBlocks.CHERRY_PLANKS.get().asItem(), () -> EnvironmentalItems.CHERRY_BOAT.getFirst().get(), () -> EnvironmentalItems.CHERRY_BOAT.getSecond().get(), () -> EnvironmentalItems.CHERRY_FURNACE_BOAT.get(), () -> EnvironmentalItems.LARGE_CHERRY_BOAT.get()));

	public static final Supplier<Item> WILLOW_FURNACE_BOAT = () -> new FurnaceBoatItem(WILLOW);
	public static final Supplier<Item> LARGE_WILLOW_BOAT = () -> new LargeBoatItem(WILLOW);

	public static final Supplier<Item> WISTERIA_FURNACE_BOAT = () -> new FurnaceBoatItem(WISTERIA);
	public static final Supplier<Item> LARGE_WISTERIA_BOAT = () -> new LargeBoatItem(WISTERIA);

	public static final Supplier<Item> CHERRY_FURNACE_BOAT = () -> new FurnaceBoatItem(CHERRY);
	public static final Supplier<Item> LARGE_CHERRY_BOAT = () -> new LargeBoatItem(CHERRY);
}