package com.teamabnormals.environmental.core.mixin.client;

import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import com.teamabnormals.environmental.client.renderer.entity.layers.MuddyPigDecorationLayer;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.EnvironmentalDataProcessors;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PigRenderer.class)
public abstract class PigRendererMixin extends MobRenderer<Pig, PigModel<Pig>> {
	private static final ResourceLocation MUDDY_PIG_LOCATION = new ResourceLocation(Environmental.MOD_ID, "textures/entity/pig/muddy_pig.png");
	private static final ResourceLocation DRIED_MUDDY_PIG_LOCATION = new ResourceLocation(Environmental.MOD_ID, "textures/entity/pig/dried_muddy_pig.png");

	public PigRendererMixin(Context p_174304_, PigModel<Pig> p_174305_, float p_174306_) {
		super(p_174304_, p_174305_, p_174306_);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void getTextureLocation(EntityRendererProvider.Context context, CallbackInfo ci) {
		this.addLayer(new MuddyPigDecorationLayer<>(this, context.getBlockRenderDispatcher()));
	}

	@Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/animal/Pig;)Lnet/minecraft/resources/ResourceLocation;", at = @At("RETURN"), cancellable = true)
	private void getTextureLocation(Pig pig, CallbackInfoReturnable<ResourceLocation> cir) {
		IDataManager dataManager = (IDataManager) pig;
		if (dataManager.getValue(EnvironmentalDataProcessors.IS_MUDDY)) {
			cir.setReturnValue(dataManager.getValue(EnvironmentalDataProcessors.MUD_DRYING_TIME) <= 0 ? DRIED_MUDDY_PIG_LOCATION : MUDDY_PIG_LOCATION);
		}
	}
}
