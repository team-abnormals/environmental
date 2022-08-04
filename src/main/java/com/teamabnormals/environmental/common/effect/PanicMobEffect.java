package com.teamabnormals.environmental.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.Map.Entry;

public class PanicMobEffect extends MobEffect {
	public PanicMobEffect() {
		super(MobEffectCategory.BENEFICIAL, 15494786);
	}

	@Override
	public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
		return 0.05 * (double) (amplifier + 1);
	}

	@Override
	public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMapIn, int amplifier) {
		float amount = entity.getMaxHealth() - entity.getHealth();
		for (Entry<Attribute, AttributeModifier> entry : this.getAttributeModifiers().entrySet()) {
			AttributeInstance iattributeinstance = attributeMapIn.getInstance(entry.getKey());
			if (iattributeinstance != null) {
				AttributeModifier attributemodifier = entry.getValue();
				iattributeinstance.removeModifier(attributemodifier);
				iattributeinstance.addPermanentModifier(new AttributeModifier(attributemodifier.getId(), this.getDescriptionId() + " " + amplifier, amount * this.getAttributeModifierValue(amplifier, attributemodifier), attributemodifier.getOperation()));
			}
		}
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity instanceof Player) {
			this.addAttributeModifiers(entity, entity.getAttributes(), amplifier);
		}
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		return true;
	}
}