package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.common.entity.animal.zebroid.Zebra;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zorse;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.Util;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Markings;
import net.minecraft.world.entity.animal.horse.Variant;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Horse.class)
public abstract class HorseMixin extends AbstractHorse {

	@Shadow
	public abstract Variant getVariant();

	@Shadow
	public abstract Markings getMarkings();

	protected HorseMixin(EntityType<? extends AbstractHorse> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "canMate", at = @At("HEAD"), cancellable = true)
	public void canMate(Animal otherParent, CallbackInfoReturnable<Boolean> cir) {
		if (otherParent instanceof Zebra && this.canParent() && ((AbstractHorse) otherParent).canParent())
			cir.setReturnValue(true);
	}

	@Inject(method = "getBreedOffspring", at = @At("HEAD"), cancellable = true)
	public void getBreedOffspring(ServerLevel level, AgeableMob otherParent, CallbackInfoReturnable<AgeableMob> cir) {
		if (otherParent instanceof Zebra zebra) {
			Zorse zorse = EnvironmentalEntityTypes.ZORSE.get().create(level);
			if (zorse != null) {
				int i = this.random.nextInt(9);
				Variant variant;
				if (i < 8) {
					variant = this.getVariant();
				} else {
					variant = Util.getRandom(Variant.values(), this.random);
				}

				int j = this.random.nextInt(5);
				Markings markings;
				if (j < 2) {
					markings = Markings.NONE;
				} else if (j < 4) {
					markings = this.getMarkings();
				} else {
					markings = Util.getRandom(Markings.values(), this.random);
				}

				zorse.setVariantAndMarkings(variant, markings);
				zorse.randomizeStripeOpacity(level.getRandom());
				zebra.setOffspringAttributes(this, zorse);
			}
			cir.setReturnValue(zorse);
		}
	}
}