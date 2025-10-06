package com.teamabnormals.environmental.common.entity.ai.goal.zebroid;

import com.teamabnormals.environmental.common.entity.animal.zebroid.Zebroid;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.EventHooks;

public class ZebroidRunAroundLikeCrazyGoal<Z extends AbstractHorse & NeutralMob & Zebroid> extends RunAroundLikeCrazyGoal {
	private final Z zebroid;

	public ZebroidRunAroundLikeCrazyGoal(Z zebroid, double speed) {
		super(zebroid, speed);
		this.zebroid = zebroid;
	}

	@Override
	public void tick() {
		Entity entity = this.zebroid.getPassengers().get(0);
		if (entity == null)
			return;

		if (!this.zebroid.isTamed() && this.zebroid.getRandom().nextInt(this.adjustedTickDelay(30)) == 0) {
			if (entity instanceof Player) {
				int i = this.zebroid.getTemper();
				int j = this.zebroid.getMaxTemper();
				if (j > 0 && this.zebroid.getRandom().nextInt(j) < i && !EventHooks.onAnimalTame(this.zebroid, (Player) entity)) {
					this.zebroid.tameWithName((Player) entity);
					this.zebroid.stopBeingAngry();
					return;
				}

				this.zebroid.modifyTemper(5);
			}

			boolean backfling = this.zebroid.getRandom().nextBoolean();
			this.zebroid.kick(backfling);
			this.zebroid.flingPassengers(backfling);
			this.zebroid.playAngrySound();
			this.zebroid.level().broadcastEntityEvent(this.zebroid, (byte) 6);
		}

		if (entity instanceof LivingEntity)
			this.zebroid.setTarget((LivingEntity) entity);
	}
}