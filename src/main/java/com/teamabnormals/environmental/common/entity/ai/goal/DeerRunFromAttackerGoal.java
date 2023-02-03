package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.environmental.common.entity.animal.deer.AbstractDeer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DeerRunFromAttackerGoal extends Goal {
	private final AbstractDeer deer;
	private final PathNavigation pathNav;

	public DeerRunFromAttackerGoal(AbstractDeer deerIn) {
		this.deer = deerIn;
		this.pathNav = deerIn.getNavigation();
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	@Override
	public boolean canUse() {
		LivingEntity attacker = this.deer.getLastHurtByMob();
		return attacker != null && attacker.isAlive() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(attacker) && this.deer.distanceToSqr(attacker) <= 256.0F;
	}

	@Override
	public void start() {
		this.flee();
	}

	@Override
	public void tick() {
		if (this.pathNav.isDone()) {
			this.flee();
		}
	}

	private void flee() {
		LivingEntity attacker = this.deer.getLastHurtByMob();
		Vec3 vec3 = DefaultRandomPos.getPosAway(this.deer, 12, 6, attacker.position());
		if (vec3 != null && attacker.distanceToSqr(vec3.x, vec3.y, vec3.z) >= attacker.distanceToSqr(this.deer)) {
			Path path = this.pathNav.createPath(vec3.x, vec3.y, vec3.z, 0);
			this.pathNav.moveTo(path, 1.8F);
		}
	}
}