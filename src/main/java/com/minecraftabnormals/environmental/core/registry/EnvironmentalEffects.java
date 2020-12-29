package com.minecraftabnormals.environmental.core.registry;

import com.minecraftabnormals.abnormals_core.common.potion.AbnormalsEffect;
import com.minecraftabnormals.environmental.common.potion.*;
import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EnvironmentalEffects {
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Environmental.MODID);

    public static final RegistryObject<Effect> PANIC 	= EFFECTS.register("panic", () -> new PanicEffect().addAttributesModifier(Attributes.MOVEMENT_SPEED, "d33dcd08-5d4a-4bb5-ae0f-4fbcd654e48a", 0.0F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<Effect> SERENITY = EFFECTS.register("serenity", () -> new AbnormalsEffect(EffectType.BENEFICIAL, 15494786));
}
