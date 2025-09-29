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
	public static final BoatloadBoatType WILLOW = BoatloadBoatType.register(BoatloadBoatType.create(Environmental.location("willow"), () -> EnvironmentalBlocks.WILLOW_PLANKS.get().asItem(), () -> EnvironmentalItems.WILLOW_BOAT.getFirst().get(), () -> EnvironmentalItems.WILLOW_BOAT.getSecond().get(), () -> EnvironmentalItems.WILLOW_FURNACE_BOAT.get(), () -> EnvironmentalItems.LARGE_WILLOW_BOAT.get()));
	public static final BoatloadBoatType PINE = BoatloadBoatType.register(BoatloadBoatType.create(Environmental.location("pine"), () -> EnvironmentalBlocks.PINE_PLANKS.get().asItem(), () -> EnvironmentalItems.PINE_BOAT.getFirst().get(), () -> EnvironmentalItems.PINE_BOAT.getSecond().get(), () -> EnvironmentalItems.PINE_FURNACE_BOAT.get(), () -> EnvironmentalItems.LARGE_PINE_BOAT.get()));
	public static final BoatloadBoatType WISTERIA = BoatloadBoatType.register(BoatloadBoatType.create(Environmental.location("wisteria"), () -> EnvironmentalBlocks.WISTERIA_PLANKS.get().asItem(), () -> EnvironmentalItems.WISTERIA_BOAT.getFirst().get(), () -> EnvironmentalItems.WISTERIA_BOAT.getSecond().get(), () -> EnvironmentalItems.WISTERIA_FURNACE_BOAT.get(), () -> EnvironmentalItems.LARGE_WISTERIA_BOAT.get()));
	public static final BoatloadBoatType PLUM = BoatloadBoatType.register(BoatloadBoatType.create(Environmental.location("plum"), () -> EnvironmentalBlocks.PLUM_PLANKS.get().asItem(), () -> EnvironmentalItems.PLUM_BOAT.getFirst().get(), () -> EnvironmentalItems.PLUM_BOAT.getSecond().get(), () -> EnvironmentalItems.PLUM_FURNACE_BOAT.get(), () -> EnvironmentalItems.LARGE_PLUM_BOAT.get()));

	public static final Supplier<Item> WILLOW_FURNACE_BOAT = () -> new FurnaceBoatItem(WILLOW);
	public static final Supplier<Item> LARGE_WILLOW_BOAT = () -> new LargeBoatItem(WILLOW);

	public static final Supplier<Item> PINE_FURNACE_BOAT = () -> new FurnaceBoatItem(PINE);
	public static final Supplier<Item> LARGE_PINE_BOAT = () -> new LargeBoatItem(PINE);

	public static final Supplier<Item> WISTERIA_FURNACE_BOAT = () -> new FurnaceBoatItem(WISTERIA);
	public static final Supplier<Item> LARGE_WISTERIA_BOAT = () -> new LargeBoatItem(WISTERIA);

	public static final Supplier<Item> PLUM_FURNACE_BOAT = () -> new FurnaceBoatItem(PLUM);
	public static final Supplier<Item> LARGE_PLUM_BOAT = () -> new LargeBoatItem(PLUM);
}