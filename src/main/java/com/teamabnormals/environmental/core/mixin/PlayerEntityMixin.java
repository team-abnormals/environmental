package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.common.item.explorer.WandererBootsItem;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity {

	protected PlayerEntityMixin(EntityType<? extends LivingEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	@Inject(at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Player;onGround:Z", shift = At.Shift.AFTER), method = "checkMovementStatistics")
	private void addMovementStat(double xDis, double yDis, double zDis, CallbackInfo info) {
		float l = Math.round(Mth.sqrt((float) (xDis * xDis + zDis * zDis)) * 100.0F) / 100.0F;
		if (l > 0 && this.isOnGround()) {
			ItemStack stack = this.getItemBySlot(EquipmentSlot.FEET);
			if (stack.getItem() instanceof WandererBootsItem) {
				((WandererBootsItem) stack.getItem()).levelUp(stack, this, l);
			}
		}
	}
}
