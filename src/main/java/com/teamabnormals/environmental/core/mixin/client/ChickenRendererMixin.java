package com.teamabnormals.environmental.core.mixin.client;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Chicken;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChickenRenderer.class)
public abstract class ChickenRendererMixin extends MobRenderer<Chicken, ChickenModel<Chicken>> {
	private static final ResourceLocation CHICK = new ResourceLocation(Environmental.MOD_ID, "textures/entity/chicken/chick.png");

	public ChickenRendererMixin(Context context, ChickenModel<Chicken> model, float shadowSize) {
		super(context, model, shadowSize);
	}

	@Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Chicken;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
	private void getTextureLocation(Chicken chicken, CallbackInfoReturnable<ResourceLocation> cir) {
		if (chicken.isBaby()) {
			cir.setReturnValue(CHICK);
		}
	}
}
