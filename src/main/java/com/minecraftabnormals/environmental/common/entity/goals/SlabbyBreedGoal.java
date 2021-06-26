package com.minecraftabnormals.environmental.common.entity.goals;

import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishType;
import com.minecraftabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class SlabbyBreedGoal extends Goal {
	private static final EntityPredicate PARTNER_TARGETING = (new EntityPredicate()).range(8.0D).allowInvulnerable().allowSameTeam().allowUnseeable();
	protected final SlabfishEntity animal;
	private final Class<? extends SlabfishEntity> mateClass;
	protected final World world;
	protected SlabfishEntity targetMate;
	private int spawnBabyDelay;
	private final double moveSpeed;

	public SlabbyBreedGoal(SlabfishEntity animal, double speedIn) {
		this(animal, speedIn, animal.getClass());
	}

	public SlabbyBreedGoal(SlabfishEntity p_i47306_1_, double p_i47306_2_, Class<? extends SlabfishEntity> p_i47306_4_) {
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
	private SlabfishEntity getNearbyMate() {
		List<SlabfishEntity> list = this.world.getNearbyEntities(this.mateClass, PARTNER_TARGETING, this.animal, this.animal.getBoundingBox().inflate(8.0D));
		double d0 = Double.MAX_VALUE;
		SlabfishEntity SlabfishEntity = null;

		for (SlabfishEntity SlabfishEntity1 : list) {
			if (this.animal.canMate(SlabfishEntity1) && this.animal.distanceToSqr(SlabfishEntity1) < d0) {
				SlabfishEntity = SlabfishEntity1;
				d0 = this.animal.distanceToSqr(SlabfishEntity1);
			}
		}

		return SlabfishEntity;
	}

	@Deprecated
	protected void spawnBaby() {
		SlabfishEntity slabby = this.animal.getBreedOffspring((ServerWorld) this.world, this.targetMate);

		final net.minecraftforge.event.entity.living.BabyEntitySpawnEvent event = new net.minecraftforge.event.entity.living.BabyEntitySpawnEvent(animal, targetMate, slabby);
		final boolean cancelled = net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
		slabby = (SlabfishEntity) event.getChild();
		if (cancelled) {
			//Reset the "inLove" state for the animals
			this.animal.setAge(6000);
			this.targetMate.setAge(6000);
			this.animal.resetLove();
			this.targetMate.resetLove();
			return;
		}
		if (slabby != null) {
			ServerPlayerEntity serverplayerentity = this.animal.getLoveCause();
			if (serverplayerentity == null && this.targetMate.getLoveCause() != null) {
				serverplayerentity = this.targetMate.getLoveCause();
			}

			if (serverplayerentity != null) {
				serverplayerentity.awardStat(Stats.ANIMALS_BRED);
				CriteriaTriggers.BRED_ANIMALS.trigger(serverplayerentity, this.animal, this.targetMate, slabby);
			}

			this.animal.setAge(6000);
			this.targetMate.setAge(6000);
			this.animal.resetLove();
			this.targetMate.resetLove();
			slabby.setAge(-24000);
			slabby.moveTo(this.animal.getX(), this.animal.getY(), this.animal.getZ(), 0.0F, 0.0F);

			SlabfishType slabfishType = SlabfishManager.get(this.world).getSlabfishType(SlabfishConditionContext.breeding(slabby, this.animal.getLoveCause(), this.animal, this.targetMate)).orElse(SlabfishManager.DEFAULT_SLABFISH);
			slabby.setSlabfishType(slabfishType.getRegistryName());

			this.world.addFreshEntity(slabby);
			this.world.broadcastEntityEvent(this.animal, (byte) 18);
			if (this.world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
				this.world.addFreshEntity(new ExperienceOrbEntity(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), this.animal.getRandom().nextInt(7) + 1));
			}
		}
	}
}
