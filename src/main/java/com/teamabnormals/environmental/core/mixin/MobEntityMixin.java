package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.core.EnvironmentalConfig;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobEntityMixin extends LivingEntity {

	@Shadow
	@Final
	protected float[] armorDropChances;

	protected MobEntityMixin(EntityType<? extends LivingEntity> type, Level worldIn) {
		super(type, worldIn);
	}

	@Inject(method = "populateDefaultEquipmentSlots", at = @At("TAIL"))
	private void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty, CallbackInfo info) {
		int difficultyChance = difficulty.getDifficulty().getId() + 1;
		if (this.getCommandSenderWorld() instanceof ServerLevel world) {

			boolean isValid = false;
			//TODO: Tag for healer pouch structures
			for (String structureName : EnvironmentalConfig.COMMON.healerPouchStructures.get()) {
				StructureFeature<?> structure = ForgeRegistries.STRUCTURE_FEATURES.getValue(new ResourceLocation(structureName));
				if (structure != null && world.structureFeatureManager().getStructureAt(this.blockPosition(), StructureFeatures.STRONGHOLD.value()).isValid()) {
					isValid = true;
					break;
				}
			}

			if (isValid && Math.random() < difficultyChance * 0.01F) {
				this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(EnvironmentalItems.HEALER_POUCH.get()));
				this.armorDropChances[EquipmentSlot.CHEST.getIndex()] = 1.0F;
			}
		}
	}
}
