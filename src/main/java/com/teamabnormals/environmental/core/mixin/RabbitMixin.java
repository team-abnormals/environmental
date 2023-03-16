package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.core.other.EnvironmentalRabbitTypes;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Rabbit.class)
public abstract class RabbitMixin extends Animal {

	protected RabbitMixin(EntityType<? extends Animal> entity, Level level) {
		super(entity, level);
	}

	@Inject(method = "getRandomRabbitType", at = @At("RETURN"), cancellable = true)
	private void getRandomRabbitType(LevelAccessor level, CallbackInfoReturnable<Integer> cir) {
		Holder<Biome> holder = level.getBiome(this.blockPosition());
		if (holder.is(EnvironmentalBiomeTags.ONLY_ALLOWS_MUDDY_RABBITS)) {
			cir.setReturnValue(EnvironmentalRabbitTypes.MUDDY.id());
		}
	}
}