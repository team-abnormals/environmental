package com.minecraftabnormals.environmental.client.render;

import com.minecraftabnormals.environmental.client.model.DeerModel;
import com.minecraftabnormals.environmental.client.render.layer.DeerMarkingsRenderLayer;
import com.minecraftabnormals.environmental.client.render.layer.RedNoseReindeerLayer;
import com.minecraftabnormals.environmental.common.entity.DeerEntity;
import com.minecraftabnormals.environmental.common.entity.util.DeerCoatColors;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class DeerRenderer extends MobRenderer<DeerEntity, DeerModel<DeerEntity>> {

	public DeerRenderer(EntityRendererManager renderManager) {
		super(renderManager, new DeerModel<>(), 0.75F);
		this.addLayer(new DeerMarkingsRenderLayer<>(this));
		this.addLayer(new RedNoseReindeerLayer<>(this));
	}

	@Override
	public ResourceLocation getTextureLocation(DeerEntity entity) {
		DeerCoatColors coatType = DeerCoatColors.byId(entity.getCoatColor());
		return new ResourceLocation(Environmental.MOD_ID, "textures/entity/deer/" + coatType.name().toLowerCase(Locale.ROOT) + ".png");
	}
}
