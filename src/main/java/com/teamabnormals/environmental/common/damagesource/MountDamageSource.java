package com.teamabnormals.environmental.common.damagesource;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public class MountDamageSource extends EntityDamageSource {
	private final Entity rider;

	public MountDamageSource(String msgId, Entity mount, Entity rider) {
		super(msgId, mount);
		this.rider = rider;
	}

	@Nullable
	@Override
	public Entity getDirectEntity() {
		return this.entity;
	}

	@Nullable
	@Override
	public Entity getEntity() {
		return this.rider;
	}

	@Override
	public Component getLocalizedDeathMessage(LivingEntity p_19410_) {
		return Component.translatable("death.attack." + this.msgId, p_19410_.getDisplayName(), this.getEntity().getDisplayName(), this.getDirectEntity().getDisplayName());
	}
}