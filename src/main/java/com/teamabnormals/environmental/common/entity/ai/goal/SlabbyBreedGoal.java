package com.teamabnormals.environmental.common.entity.ai.goal;

import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.slabfish.SlabfishManager;
import com.teamabnormals.environmental.common.slabfish.SlabfishType;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishTypes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class SlabbyBreedGoal extends Goal {
	private static final TargetingConditions PARTNER_TARGETING = TargetingConditions.forNonCombat().range(8.0D).ignoreLineOfSight();
	protected final Slabfish animal;
	private final Class<? extends Slabfish> mateClass;
	protected final Level world;
	protected Slabfish targetMate;
	private int spawnBabyDelay;
	private final double moveSpeed;

	public SlabbyBreedGoal(Slabfish animal, double speedIn) {
		this(animal, speedIn, animal.getClass());
	}

	public SlabbyBreedGoal(Slabfish p_i47306_1_, double p_i47306_2_, Class<? extends Slabfish> p_i47306_4_) {
		this.animal = p_i47306_1_;
		this.world = p_i47306_1_.level;
		this.mateClass = p_i47306_4_;
		this.moveSpeed = p_i47306_2_;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	public boolean canUse() {
		if (!this.animal.isInLove()) {
			return false;
		} else {
			this.targetMate = this.getNearbyMate();
			return this.targetMate != null;
		}
	}

	public boolean canContinueToUse() {
		return this.targetMate.isAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60;
	}

	public void stop() {
		this.targetMate = null;
		this.spawnBabyDelay = 0;
	}

	public void tick() {
		this.animal.getLookControl().setLookAt(this.targetMate, 10.0F, (float) this.animal.getMaxHeadXRot());
		this.animal.getNavigation().moveTo(this.targetMate, this.moveSpeed);
		++this.spawnBabyDelay;
		if (this.spawnBabyDelay >= 60 && this.animal.distanceToSqr(this.targetMate) < 9.0D) {
			this.spawnBaby();
		}

	}

	@Nullable
	private Slabfish getNearbyMate() {
		List<? extends Slabfish> list = this.world.getNearbyEntities(this.mateClass, PARTNER_TARGETING, this.animal, this.animal.getBoundingBox().inflate(8.0D));
		double d0 = Double.MAX_VALUE;
		Slabfish slabfish = null;

		for (Slabfish slabfish1 : list) {
			if (this.animal.canMate(slabfish1) && this.animal.distanceToSqr(slabfish1) < d0) {
				slabfish = slabfish1;
				d0 = this.animal.distanceToSqr(slabfish1);
			}
		}

		return slabfish;
	}

	@Deprecated
	protected void spawnBaby() {
		Slabfish slabby = (Slabfish) this.animal.getBreedOffspring((ServerLevel) this.world, this.targetMate);

		final BabyEntitySpawnEvent event = new BabyEntitySpawnEvent(animal, targetMate, slabby);
		final boolean cancelled = MinecraftForge.EVENT_BUS.post(event);
		slabby = (Slabfish) event.getChild();
		if (cancelled) {
			//Reset the "inLove" state for the animals
			this.animal.setAge(6000);
			this.targetMate.setAge(6000);
			this.animal.resetLove();
			this.targetMate.resetLove();
			return;
		}
		if (slabby != null) {
			ServerPlayer player = this.animal.getLoveCause();
			if (player == null && this.targetMate.getLoveCause() != null) {
				player = this.targetMate.getLoveCause();
			}

			this.animal.setAge(6000);
			this.targetMate.setAge(6000);
			this.animal.resetLove();
			this.targetMate.resetLove();
			slabby.setAge(-24000);
			slabby.moveTo(this.animal.getX(), this.animal.getY(), this.animal.getZ(), 0.0F, 0.0F);

			SlabfishType slabfishType = SlabfishManager.get(this.world).getSlabfishType(SlabfishConditionContext.breeding(slabby, this.animal.getLoveCause(), this.animal, this.targetMate)).orElse(EnvironmentalSlabfishTypes.SWAMP.get());
			slabby.setSlabfishType(slabfishType.registryName());

			if (player != null) {
				player.awardStat(Stats.ANIMALS_BRED);
				CriteriaTriggers.BRED_ANIMALS.trigger(player, this.animal, this.targetMate, slabby);
			}

			this.world.addFreshEntity(slabby);
			this.world.broadcastEntityEvent(this.animal, (byte) 18);
			if (this.world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
				this.world.addFreshEntity(new ExperienceOrb(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), this.animal.getRandom().nextInt(7) + 1));
			}
		}
	}
}
