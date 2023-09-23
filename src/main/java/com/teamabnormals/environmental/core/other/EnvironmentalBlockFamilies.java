package com.teamabnormals.environmental.core.other;

import net.minecraft.data.BlockFamily;

import static com.teamabnormals.environmental.core.registry.EnvironmentalBlocks.*;

public class EnvironmentalBlockFamilies {
	public static final BlockFamily WILLOW_PLANKS_FAMILY = new BlockFamily.Builder(WILLOW_PLANKS.get()).button(WILLOW_BUTTON.get()).fence(WILLOW_FENCE.get()).fenceGate(WILLOW_FENCE_GATE.get()).pressurePlate(WILLOW_PRESSURE_PLATE.get()).sign(WILLOW_SIGNS.getFirst().get(), WILLOW_SIGNS.getSecond().get()).slab(WILLOW_SLAB.get()).stairs(WILLOW_STAIRS.get()).door(WILLOW_DOOR.get()).trapdoor(WILLOW_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
	public static final BlockFamily WISTERIA_PLANKS_FAMILY = new BlockFamily.Builder(WISTERIA_PLANKS.get()).button(WISTERIA_BUTTON.get()).fence(WISTERIA_FENCE.get()).fenceGate(WISTERIA_FENCE_GATE.get()).pressurePlate(WISTERIA_PRESSURE_PLATE.get()).sign(WISTERIA_SIGNS.getFirst().get(), WISTERIA_SIGNS.getSecond().get()).slab(WISTERIA_SLAB.get()).stairs(WISTERIA_STAIRS.get()).door(WISTERIA_DOOR.get()).trapdoor(WISTERIA_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
	public static final BlockFamily CHERRY_PLANKS_FAMILY = new BlockFamily.Builder(CHERRY_PLANKS.get()).button(CHERRY_BUTTON.get()).fence(CHERRY_FENCE.get()).fenceGate(CHERRY_FENCE_GATE.get()).pressurePlate(CHERRY_PRESSURE_PLATE.get()).sign(CHERRY_SIGNS.getFirst().get(), CHERRY_SIGNS.getSecond().get()).slab(CHERRY_SLAB.get()).stairs(CHERRY_STAIRS.get()).door(CHERRY_DOOR.get()).trapdoor(CHERRY_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
	public static final BlockFamily PINE_PLANKS_FAMILY = new BlockFamily.Builder(PINE_PLANKS.get()).button(PINE_BUTTON.get()).fence(PINE_FENCE.get()).fenceGate(PINE_FENCE_GATE.get()).pressurePlate(PINE_PRESSURE_PLATE.get()).sign(PINE_SIGNS.getFirst().get(), PINE_SIGNS.getSecond().get()).slab(PINE_SLAB.get()).stairs(PINE_STAIRS.get()).door(PINE_DOOR.get()).trapdoor(PINE_TRAPDOOR.get()).recipeGroupPrefix("wooden").recipeUnlockedBy("has_planks").getFamily();
	public static final BlockFamily SMOOTH_MUD_FAMILY = new BlockFamily.Builder(SMOOTH_MUD.get()).slab(SMOOTH_MUD_SLAB.get()).getFamily();
	public static final BlockFamily GRASS_THATCH_FAMILY = new BlockFamily.Builder(GRASS_THATCH.get()).stairs(GRASS_THATCH_STAIRS.get()).slab(GRASS_THATCH_SLAB.get()).getFamily();
	public static final BlockFamily CATTAIL_THATCH_FAMILY = new BlockFamily.Builder(CATTAIL_THATCH.get()).stairs(CATTAIL_THATCH_STAIRS.get()).slab(CATTAIL_THATCH_SLAB.get()).getFamily();
	public static final BlockFamily DUCKWEED_THATCH_FAMILY = new BlockFamily.Builder(DUCKWEED_THATCH.get()).stairs(DUCKWEED_THATCH_STAIRS.get()).slab(DUCKWEED_THATCH_SLAB.get()).getFamily();
	public static final BlockFamily DIRT_BRICK_FAMILY = new BlockFamily.Builder(DIRT_BRICKS.get()).stairs(DIRT_BRICK_STAIRS.get()).slab(DIRT_BRICK_SLAB.get()).wall(DIRT_BRICK_WALL.get()).getFamily();
	public static final BlockFamily DIRT_TILE_FAMILY = new BlockFamily.Builder(DIRT_TILES.get()).stairs(DIRT_TILE_STAIRS.get()).slab(DIRT_TILE_SLAB.get()).wall(DIRT_TILE_WALL.get()).getFamily();
}
