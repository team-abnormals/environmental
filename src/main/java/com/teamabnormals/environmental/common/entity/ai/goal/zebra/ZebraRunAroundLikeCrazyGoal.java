package com.teamabnormals.environmental.common.entity.ai.goal.zebra;

import com.teamabnormals.environmental.common.entity.animal.Zebra;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RunAroundLikeCrazyGoal;
import net.minecraft.world.entity.player.Player;

public class ZebraRunAroundLikeCrazyGoal extends RunAroundLikeCrazyGoal {
	private final Zebra zebra;

	public ZebraRunAroundLikeCrazyGoal(Zebra zebra, double speed) {
		super(zebra, speed);
		this.zebra = zebra;
	}

	@Override
	public void tick() {
		Entity entity = this.zebra.getPassengers().get(0);
		if (entity == null)
			return;

		if (!this.zebra.isTamed() && this.zebra.getRandom().nextInt(this.adjustedTickDelay(50)) == 0) {
			if (entity instanceof Player) {
				int i = this.zebra.getTemper();
				int j = this.zebra.getMaxTemper();
				if (j > 0 && this.zebra.getRandom().nextInt(j) < i && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this.zebra, (Player)entity)) {
					this.zebra.tameWithName((Player) entity);
					this.zebra.stopBeingAngry();
					return;
				}

				this.zebra.modifyTemper(5);
			}

			boolean backfling = this.zebra.getRandom().nextBoolean();
			this.zebra.kick(backfling);
			this.zebra.flingPassengers(backfling);
			this.zebra.playAngrySound();
			this.zebra.level.broadcastEntityEvent(this.zebra, (byte)6);
		}

		if (entity instanceof LivingEntity)
			this.zebra.setTarget((LivingEntity) entity);
	}
}