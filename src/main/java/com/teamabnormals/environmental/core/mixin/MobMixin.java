package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.core.other.tags.EnvironmentalStructureTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {

	@Shadow
	@Final
	protected float[] armorDropChances;

	protected MobMixin(EntityType<? extends LivingEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	@Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
	private void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty, CallbackInfo info) {
		int difficultyChance = difficulty.getDifficulty().getId() + 1;
		if (this.getCommandSenderWorld() instanceof ServerLevel serverLevel) {
			Optional<Named<Structure>> structures = serverLevel.registryAccess().registryOrThrow(Registry.STRUCTURE_REGISTRY).getTag(EnvironmentalStructureTags.HAS_HEALER_POUCH);
			if (structures.isPresent()) {
				boolean valid = false;
				for (Holder<Structure> structure : structures.get()) {
					if (structure != null && serverLevel.structureManager().getStructureAt(this.blockPosition(), structure.value()).isValid()) {
						valid = true;
					}
				}

				if (valid && random.nextDouble() < difficultyChance * 0.01F) {
					this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(EnvironmentalItems.HEALER_POUCH.get()));
					this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = 1.0F;
				}
			}

		}
	}
}
