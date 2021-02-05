package com.minecraftabnormals.environmental.api;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public interface IExplorerArmorItem {
	String getUsesTag();
	String getDescriptionString();
	int getIncreaseForUses(int uses);
	int[] getLevelCaps();

	default int levelUp(ItemStack stack, LivingEntity entity) {
		return levelUp(stack, entity, false);
	}

	default int levelUp(ItemStack stack, LivingEntity entity, boolean useFloat) {
		CompoundNBT tag = stack.getOrCreateTag();
		int uses = tag.getInt(this.getUsesTag());
		int level = this.getIncreaseForUses(uses);

		if (useFloat) {
			uses = (int) tag.getFloat(this.getUsesTag());
			tag.putFloat(this.getUsesTag(), uses + 1);
		} else {
			tag.putInt(this.getUsesTag(), uses + 1);
		}

		int newLevel = this.getIncreaseForUses(uses + 1);
		if (newLevel > level) this.playEffects(newLevel, entity);

		return newLevel;
	}

	default void playEffects(int level, LivingEntity entity) {
		World world = entity.getEntityWorld();
		world.playSound(null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), SoundEvents.ENTITY_PLAYER_LEVELUP, entity.getSoundCategory(), level * 0.20F, 1.0F);
//		IParticleData particle = ParticleTypes.HEART;
//		if (level == 5) particle = ParticleTypes.FLAME;
//		Random rand = entity.getRNG();
//		if (world.isRemote()) {
//			int times = 2 * level;
//			for (int i = 0; i < times; ++i) {
//				double d0 = rand.nextGaussian() * 0.02D;
//				double d1 = rand.nextGaussian() * 0.02D;
//				double d2 = rand.nextGaussian() * 0.02D;
//				world.addParticle(particle, entity.getPosXRandom(1.0D), entity.getPosYRandom() + 0.5D, entity.getPosZRandom(1.0D), d0, d1, d2);
//			}
//		}
	}
}
