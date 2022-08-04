package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.item.crafting.BakingRecipe;
import com.teamabnormals.environmental.common.item.crafting.SawmillRecipe;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EnvironmentalRecipes {

	public static class EnvironmentalRecipeSerializers {
		public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Environmental.MOD_ID);

		public static final RegistryObject<RecipeSerializer<BakingRecipe>> BAKING = RECIPE_SERIALIZERS.register("baking", BakingRecipe.Serializer::new);
		public static final RegistryObject<RecipeSerializer<SawmillRecipe>> SAWING = RECIPE_SERIALIZERS.register("sawing", () -> new SingleItemRecipe.Serializer<>(SawmillRecipe::new) {
		});
	}

	public static class EnvironmentalRecipeTypes {
		public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, Environmental.MOD_ID);

		public static final RegistryObject<RecipeType<BakingRecipe>> BAKING = RECIPE_TYPES.register("baking", () -> new RecipeType<>() {
			@Override
			public String toString() {
				return Environmental.MOD_ID + ":baking";
			}
		});

		public static final RegistryObject<RecipeType<SawmillRecipe>> SAWING = RECIPE_TYPES.register("sawing", () -> new RecipeType<>() {
			@Override
			public String toString() {
				return Environmental.MOD_ID + ":sawing";
			}
		});
	}
}
