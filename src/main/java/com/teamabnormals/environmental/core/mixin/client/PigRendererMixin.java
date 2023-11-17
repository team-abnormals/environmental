package com.teamabnormals.environmental.core.mixin.client;

import com.teamabnormals.environmental.client.renderer.entity.layers.MuddyPigDecorationLayer;
import com.teamabnormals.environmental.client.renderer.entity.layers.MuddyPigMudLayer;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.world.entity.animal.Pig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PigRenderer.class)
public abstract class PigRendererMixin extends MobRenderer<Pig, PigModel<Pig>> {

	public PigRendererMixin(Context context, PigModel<Pig> model, float shadowSize) {
		super(context, model, shadowSize);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void getTextureLocation(EntityRendererProvider.Context context, CallbackInfo ci) {
		this.addLayer(new MuddyPigMudLayer<>(this));
		this.addLayer(new MuddyPigDecorationLayer<>(this, context.getBlockRenderDispatcher()));
	}
}
