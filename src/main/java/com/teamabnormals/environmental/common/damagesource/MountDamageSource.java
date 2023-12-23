package com.teamabnormals.environmental.common.damagesource;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class MountDamageSource extends IndirectEntityDamageSource {

	public MountDamageSource(String msgId, Entity passenger, Entity mount) {
		super(msgId, passenger, mount);
	}

	public Component getLocalizedDeathMessage(LivingEntity p_19410_) {
		return Component.translatable("death.attack." + this.msgId, p_19410_.getDisplayName(), this.getEntity().getDisplayName(), this.getDirectEntity().getDisplayName());
	}
}