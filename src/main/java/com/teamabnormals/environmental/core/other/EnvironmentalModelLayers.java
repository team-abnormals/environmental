package com.teamabnormals.environmental.core.other;

import com.teamabnormals.environmental.client.model.*;
import com.teamabnormals.environmental.client.renderer.entity.*;
import com.teamabnormals.environmental.client.renderer.entity.layers.MuddyPigDecorationLayer;
import com.teamabnormals.environmental.client.renderer.entity.layers.MuddyPigMudLayer;
import com.teamabnormals.environmental.client.renderer.entity.layers.MuleArmorLayer;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.client.renderer.entity.PigRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.horse.Mule;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Environmental.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EnvironmentalModelLayers {
	public static final ModelLayerLocation DEER = register("deer");
	public static final ModelLayerLocation REINDEER = register("reindeer");
	public static final ModelLayerLocation DUCK = register("duck");
	public static final ModelLayerLocation KOI = register("koi");
	public static final ModelLayerLocation SLABFISH = register("slabfish");
	public static final ModelLayerLocation TAPIR = register("tapir");
	public static final ModelLayerLocation YAK = register("yak");
	public static final ModelLayerLocation ZEBRA = register("zebra");
	public static final ModelLayerLocation ZORSE = register("zorse");
	public static final ModelLayerLocation ZONKEY = register("zonkey");
	public static final ModelLayerLocation ZORSE_ARMOR = register("zorse_armor");
	public static final ModelLayerLocation MULE_ARMOR = register("mule_armor");
	public static final ModelLayerLocation PINECONE_GOLEM = register("pinecone_golem");

	@SubscribeEvent
	public static void registerLayers(EntityRenderersEvent.AddLayers event) {
		ChestedHorseRenderer<Mule> muleRenderer = event.getRenderer(EntityType.MULE);
		if (muleRenderer != null) {
			muleRenderer.addLayer(new MuleArmorLayer<>(muleRenderer, event.getEntityModels()));
		}

		PigRenderer pigRenderer = event.getRenderer(EntityType.PIG);
		if (pigRenderer != null) {
			pigRenderer.addLayer(new MuddyPigMudLayer<>(pigRenderer));
			pigRenderer.addLayer(new MuddyPigDecorationLayer<>(pigRenderer, event.getContext().getBlockRenderDispatcher()));
		}
	}

	@SubscribeEvent
	public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(EnvironmentalModelLayers.DUCK, DuckModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.DEER, DeerModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.REINDEER, ReindeerModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.KOI, KoiModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.SLABFISH, SlabfishModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.YAK, YakModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.TAPIR, TapirModel::createBodyLayer);
		event.registerLayerDefinition(EnvironmentalModelLayers.ZEBRA, () -> LayerDefinition.create(ZebraModel.createBodyMesh(CubeDeformation.NONE), 64, 64));
		event.registerLayerDefinition(EnvironmentalModelLayers.ZORSE, () -> LayerDefinition.create(ZorseModel.createBodyMesh(CubeDeformation.NONE), 64, 64));
		event.registerLayerDefinition(EnvironmentalModelLayers.ZONKEY, () -> LayerDefinition.create(ZonkeyModel.createBodyMesh(CubeDeformation.NONE), 64, 64));
		event.registerLayerDefinition(EnvironmentalModelLayers.ZORSE_ARMOR, () -> LayerDefinition.create(ZorseModel.createBodyMesh(new CubeDeformation(0.1F)), 64, 64));
		event.registerLayerDefinition(EnvironmentalModelLayers.MULE_ARMOR, () -> MuleArmorModel.createBodyLayer(new CubeDeformation(0.1F)));
		event.registerLayerDefinition(EnvironmentalModelLayers.PINECONE_GOLEM, PineconeGolemModel::createBodyLayer);
	}

	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(EnvironmentalEntityTypes.SLABFISH.get(), SlabfishRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.DUCK.get(), DuckRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.DEER.get(), DeerRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.REINDEER.get(), ReindeerRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.YAK.get(), YakRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.KOI.get(), KoiRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.TAPIR.get(), TapirRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.ZEBRA.get(), ZebraRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.ZORSE.get(), ZorseRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.ZONKEY.get(), ZonkeyRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.PINECONE_GOLEM.get(), PineconeGolemRenderer::new);

		event.registerEntityRenderer(EnvironmentalEntityTypes.DUCK_EGG.get(), ThrownItemRenderer::new);
		event.registerEntityRenderer(EnvironmentalEntityTypes.MUD_BALL.get(), ThrownItemRenderer::new);
	}

	public static ModelLayerLocation register(String name) {
		return register(name, "main");
	}

	public static ModelLayerLocation register(String name, String layer) {
		return new ModelLayerLocation(Environmental.location(name), layer);
	}
}