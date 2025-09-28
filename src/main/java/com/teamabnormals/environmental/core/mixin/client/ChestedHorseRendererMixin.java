package com.teamabnormals.environmental.core.mixin.client;

import com.teamabnormals.environmental.client.renderer.entity.layers.MuleArmorLayer;
import net.minecraft.client.model.ChestedHorseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.AbstractHorseRenderer;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestedHorseRenderer.class)
public abstract class ChestedHorseRendererMixin<T extends AbstractChestedHorse> extends AbstractHorseRenderer<T, ChestedHorseModel<T>> {

	public ChestedHorseRendererMixin(EntityRendererProvider.Context context, float scale, ModelLayerLocation p_173950_) {
		super(context, new ChestedHorseModel<>(context.bakeLayer(p_173950_)), scale);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void init(EntityRendererProvider.Context context, float p_173949_, ModelLayerLocation p_173950_, CallbackInfo ci) {
		this.addLayer(new MuleArmorLayer<>(this, context.getModelSet()));
	}
}