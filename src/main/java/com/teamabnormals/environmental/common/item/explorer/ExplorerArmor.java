package com.teamabnormals.environmental.common.item.explorer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface ExplorerArmor {
	String getUsesTag();

	int getIncreaseForUses(int uses);

	int[] getLevelCaps();

	default int levelUp(ItemStack stack, LivingEntity entity) {
		CompoundTag tag = stack.getOrCreateTag();
		int uses = tag.getInt(this.getUsesTag());
		int level = this.getIncreaseForUses(uses);

		tag.putInt(this.getUsesTag(), uses + 1);

		int newLevel = this.getIncreaseForUses(uses + 1);
		if (newLevel > level) this.playEffects(newLevel, entity);

		return newLevel;
	}

	default void playEffects(float level, LivingEntity entity) {
		Level world = entity.getCommandSenderWorld();
		world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_LEVELUP, entity.getSoundSource(), level * 0.20F, 1.0F);
	}
}
