package com.teamabnormals.environmental.common.entity.animal;

import com.teamabnormals.environmental.common.entity.ai.goal.pineconegolem.PineconeGolemGrabSaplingGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.pineconegolem.PineconeGolemLookForSpotGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.pineconegolem.PineconeGolemPlantSaplingGoal;
import com.teamabnormals.environmental.common.entity.ai.goal.pineconegolem.PineconeGolemTemptGoal;
import com.teamabnormals.environmental.core.registry.EnvironmentalSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class PineconeGolem extends AbstractGolem {

	public PineconeGolem(EntityType<? extends AbstractGolem> type, Level level) {
		super(type, level);
		this.setCanPickUpLoot(true);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new PineconeGolemTemptGoal(this, 1.0D));
		this.goalSelector.addGoal(1, new PineconeGolemPlantSaplingGoal(this, 1.0D));
		this.goalSelector.addGoal(2, new PineconeGolemLookForSpotGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new PineconeGolemGrabSaplingGoal(this, 1.0D));
		this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.125D);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return EnvironmentalSoundEvents.PINECONE_GOLEM_HURT.get();
	}

	@Override
	protected SoundEvent getDeathSound() {
		return EnvironmentalSoundEvents.PINECONE_GOLEM_DEATH.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(EnvironmentalSoundEvents.PINECONE_GOLEM_STEP.get(), 0.1F, 1.0F);
	}

	@Override
	public int getMaxHeadXRot() {
		return 0;
	}

	@Override
	public int getMaxHeadYRot() {
		return 5;
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		ItemStack stack1 = this.getMainHandItem();
		if (stack1.isEmpty()) {
			if (stack.is(ItemTags.SAPLINGS)) {
				ItemStack stack2 = stack.copy();
				stack2.setCount(1);
				this.setItemInHand(InteractionHand.MAIN_HAND, stack2);
				if (!player.getAbilities().instabuild)
					stack.shrink(1);
				return InteractionResult.SUCCESS;
			}
		} else if (hand == InteractionHand.MAIN_HAND && stack.isEmpty()) {
			this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
			player.addItem(stack1);
			return InteractionResult.SUCCESS;
		}
		return super.mobInteract(player, hand);
	}

	@Override
	public void knockback(double force, double x, double z) {
		super.knockback(force, x, z);
		ItemStack itemstack = this.getMainHandItem();
		if (!itemstack.isEmpty()) {
			ItemEntity itementity = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), itemstack);
			itementity.setPickUpDelay(40);
			itementity.setThrower(this);
			this.level().addFreshEntity(itementity);
			this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
		}
	}

	@Override
	protected void updateWalkAnimation(float p_268283_) {
		float f = Math.min(p_268283_ * 16.0F, 1.0F);
		this.walkAnimation.update(f, 0.4F);
	}

	@Override
	public boolean canHoldItem(ItemStack stack) {
		return this.getMainHandItem().isEmpty() && stack.is(ItemTags.SAPLINGS);
	}

	@Override
	protected void pickUpItem(ItemEntity itementity) {
		ItemStack itemstack = itementity.getItem();
		if (this.canHoldItem(itemstack)) {
			int i = itemstack.getCount();
			if (i > 1) {
				ItemEntity itementity1 = new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), itemstack.split(i - 1));
				this.level().addFreshEntity(itementity1);
			}

			this.onItemPickup(itementity);
			this.setItemSlot(EquipmentSlot.MAINHAND, itemstack.split(1));
			this.setGuaranteedDrop(EquipmentSlot.MAINHAND);
			this.take(itementity, itemstack.getCount());
			itementity.discard();
		}
	}
}