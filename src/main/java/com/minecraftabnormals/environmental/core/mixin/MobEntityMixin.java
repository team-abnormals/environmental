package com.minecraftabnormals.environmental.core.mixin;

import com.minecraftabnormals.environmental.core.other.EnvironmentalPouchEquipping;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {

	protected MobEntityMixin(EntityType<? extends LivingEntity> type, World worldIn) {
		super(type, worldIn);
	}

	@Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
	private void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty, CallbackInfo info) {
		if (this.getCommandSenderWorld() instanceof ServerWorld) {
			int difficultyChance = difficulty.getDifficulty().getId() + 1;
			EnvironmentalPouchEquipping.equipPouch((ServerWorld) this.getCommandSenderWorld(), (MobEntity) (Object) this, difficultyChance);
		}
	}
}
