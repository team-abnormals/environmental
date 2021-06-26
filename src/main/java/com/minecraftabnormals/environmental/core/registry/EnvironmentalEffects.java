package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.abnormals_core.common.potion.AbnormalsEffect;
import com.minecraftabnormals.environmental.common.potion.PanicEffect;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Items;
import net.minecraft.potion.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalEffects {
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Environmental.MOD_ID);
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, Environmental.MOD_ID);

	public static final RegistryObject<Effect> PANIC = EFFECTS.register("panic", () -> new PanicEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED, "d33dcd08-5d4a-4bb5-ae0f-4fbcd654e48a", 0.0F, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<Effect> SERENITY = EFFECTS.register("serenity", () -> new AbnormalsEffect(EffectType.BENEFICIAL, 15494786));
	
	public static final RegistryObject<Potion> VITALITY = POTIONS.register("vitality", () -> new Potion(new EffectInstance(Effects.HEALTH_BOOST, 9600)));
	public static final RegistryObject<Potion> VITALITY_STRONG = POTIONS.register("vitality_strong", () -> new Potion(new EffectInstance(Effects.HEALTH_BOOST, 4800, 1)));

	public static void registerBrewingRecipes() {
		PotionBrewing.addMix(Potions.AWKWARD, EnvironmentalItems.TRUFFLE.get(), VITALITY.get());
		PotionBrewing.addMix(VITALITY.get(), Items.GLOWSTONE_DUST, VITALITY_STRONG.get());
	}
}
