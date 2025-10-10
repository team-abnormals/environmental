package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.common.entity.animal.zebroid.Zebra;
import com.teamabnormals.environmental.common.entity.animal.zebroid.Zonkey;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Donkey.class)
public abstract class DonkeyMixin extends AbstractChestedHorse {

	protected DonkeyMixin(EntityType<? extends Donkey> entityType, Level level) {
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
			Zonkey zonkey = EnvironmentalEntityTypes.ZONKEY.get().create(level);
			if (zonkey != null) {
				zonkey.randomizeStripeOpacity(level.getRandom());
				zebra.setOffspringAttributes(this, zonkey);
			}
			cir.setReturnValue(zonkey);
		}
	}
}