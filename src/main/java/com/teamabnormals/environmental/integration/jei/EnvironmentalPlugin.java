package com.teamabnormals.environmental.integration.jei;

import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class EnvironmentalPlugin implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(Environmental.MOD_ID, Environmental.MOD_ID);
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, EnvironmentalItems.SLABFISH_BUCKET.get(), (stack, context) -> stack.getTag() != null && stack.getTag().contains("SlabfishType", Tag.TAG_STRING) ? stack.getTag().getString("SlabfishType") : IIngredientSubtypeInterpreter.NONE);
	}
}