package com.minecraftabnormals.environmental.api;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public interface IExplorerArmorItem {
	String getUsesTag();

	int getIncreaseForUses(int uses);

	int[] getLevelCaps();

	default int levelUp(ItemStack stack, LivingEntity entity) {
		CompoundNBT tag = stack.getOrCreateTag();
		int uses = tag.getInt(this.getUsesTag());
		int level = this.getIncreaseForUses(uses);

		tag.putInt(this.getUsesTag(), uses + 1);

		int newLevel = this.getIncreaseForUses(uses + 1);
		if (newLevel > level) this.playEffects(newLevel, entity);

		return newLevel;
	}

	default void playEffects(float level, LivingEntity entity) {
		World world = entity.getCommandSenderWorld();
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_LEVELUP, entity.getSoundSource(), level * 0.20F, 1.0F);
	}
}
