package com.team_abnormals.environmental.core.registry;

import com.team_abnormals.environmental.common.item.crafting.BakingRecipe;
import com.team_abnormals.environmental.core.Environmental;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnvironmentalRecipes {

	public static class Serailizers {
		public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Environmental.MODID);
		public static final RegistryObject<IRecipeSerializer<?>> BAKING = RECIPE_SERIALIZERS.register("baking", BakingRecipe.Serializer::new);
	}

	public static class RecipeTypes {
		public static final IRecipeType<BakingRecipe> BAKING = IRecipeType.register(Environmental.MODID + ":baking");
	}
}
