package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.environmental.common.item.crafting.BakingRecipe;
import com.minecraftabnormals.environmental.common.item.crafting.SawingRecipe;
import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.SingleItemRecipe;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnvironmentalRecipes {

    public static class Serailizers {
        public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Environmental.MODID);
        public static final RegistryObject<IRecipeSerializer<BakingRecipe>> BAKING = RECIPE_SERIALIZERS.register("baking", BakingRecipe.Serializer::new);
        public static final RegistryObject<IRecipeSerializer<SawingRecipe>> SAWING = RECIPE_SERIALIZERS.register("sawing", () -> new SingleItemRecipe.Serializer<SawingRecipe>(SawingRecipe::new){});
    }

    public static class RecipeTypes {
        public static final IRecipeType<BakingRecipe> BAKING = IRecipeType.register(Environmental.MODID + ":baking");
        public static final IRecipeType<SawingRecipe> SAWING = IRecipeType.register(Environmental.MODID + ":sawing");
    }
}
