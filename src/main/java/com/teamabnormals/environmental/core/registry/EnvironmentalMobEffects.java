package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.blueprint.common.effect.BlueprintMobEffect;
import com.teamabnormals.environmental.common.effect.PanicMobEffect;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Environmental.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnvironmentalMobEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Environmental.MOD_ID);
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Environmental.MOD_ID);

	public static final RegistryObject<MobEffect> PANIC = MOB_EFFECTS.register("panic", () -> new PanicMobEffect().addAttributeModifier(Attributes.MOVEMENT_SPEED, "d33dcd08-5d4a-4bb5-ae0f-4fbcd654e48a", 0.0F, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<MobEffect> SERENITY = MOB_EFFECTS.register("serenity", () -> new BlueprintMobEffect(MobEffectCategory.BENEFICIAL, 15494786));

	public static final RegistryObject<Potion> VITALITY = POTIONS.register("vitality", () -> new Potion(new MobEffectInstance(MobEffects.HEALTH_BOOST, 9600)));
	public static final RegistryObject<Potion> VITALITY_STRONG = POTIONS.register("vitality_strong", () -> new Potion(new MobEffectInstance(MobEffects.HEALTH_BOOST, 4800, 1)));

	public static void registerBrewingRecipes() {
		PotionBrewing.addMix(Potions.AWKWARD, EnvironmentalItems.TRUFFLE.get(), VITALITY.get());
		PotionBrewing.addMix(VITALITY.get(), Items.GLOWSTONE_DUST, VITALITY_STRONG.get());
	}
}
