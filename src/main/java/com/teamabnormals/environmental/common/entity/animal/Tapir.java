package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Tapir extends Animal {

	public Tapir(EntityType<? extends Tapir> type, Level world) {
		super(type, world);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(ItemTags.ANVIL), false));
		this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.PIG_AMBIENT;
	}

	protected SoundEvent getHurtSound(DamageSource source) {
		return SoundEvents.PIG_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.PIG_DEATH;
	}

	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
	}

	public Tapir getBreedOffspring(ServerLevel level, AgeableMob mob) {
		return EnvironmentalEntityTypes.TAPIR.get().create(level);
	}

	public boolean isFood(ItemStack stack) {
		return stack.is(ItemTags.ANVIL);
	}
}
