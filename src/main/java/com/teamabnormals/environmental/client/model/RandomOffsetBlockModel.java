package com.teamabnormals.environmental.client.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.math.Transformation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.QuadTransformers;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IGeometryLoader;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.function.Function;

public class RandomOffsetBlockModel implements IUnbakedGeometry<RandomOffsetBlockModel> {
	private final BlockModel baseModel;

	public RandomOffsetBlockModel(BlockModel baseModel) {
		this.baseModel = baseModel;
	}

	@Override
	public BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides) {
		Material particleLocation = context.getMaterial("particle");
		TextureAtlasSprite particle = spriteGetter.apply(particleLocation);
		BakedModel bakedBase = this.baseModel.bake(baker, this.baseModel, spriteGetter, modelState, context.useBlockLight());
		return new Baked(context.isGui3d(), context.useBlockLight(), context.useAmbientOcclusion(), particle, context.getTransforms(), overrides, bakedBase);
	}

	@Override
	public void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter, IGeometryBakingContext context) {
		this.baseModel.resolveParents(modelGetter);
	}

	public static final class Baked implements IDynamicBakedModel {
		private final boolean isAmbientOcclusion;
		private final boolean isGui3d;
		private final boolean isSideLit;
		private final TextureAtlasSprite particle;
		private final ItemOverrides overrides;
		private final ItemTransforms transforms;
		private final BakedModel baseModel;

		public Baked(boolean isAmbientOcclusion, boolean isGui3d, boolean isSideLit, TextureAtlasSprite particle, ItemTransforms transforms, ItemOverrides overrides, BakedModel baseModel) {
			this.isAmbientOcclusion = isAmbientOcclusion;
			this.isGui3d = isGui3d;
			this.isSideLit = isSideLit;
			this.particle = particle;
			this.transforms = transforms;
			this.overrides = overrides;
			this.baseModel = baseModel;
		}

		@Override
		public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource random, ModelData data, @Nullable RenderType renderType) {
			List<BakedQuad> baseQuads = this.baseModel.getQuads(state, side, random, data, renderType);
			if (baseQuads.isEmpty()) return baseQuads;
			long seed = random.nextLong();
			float xOffset = Mth.clamp((((float) (seed & 15L) / 15.0F) - 0.5F) * 0.5F, -0.25F, 0.25F);
			float zOffset = Mth.clamp((((float) (seed >> 8 & 15L) / 15.0F) - 0.5F) * 0.5F, -0.25F, 0.25F);
			return QuadTransformers.applying(new Transformation(new Vector3f(xOffset, 0.0F, zOffset), null, null, null)).process(baseQuads);
		}

		@Override
		public boolean useAmbientOcclusion() {
			return this.isAmbientOcclusion;
		}

		@Override
		public boolean isGui3d() {
			return this.isGui3d;
		}

		@Override
		public boolean usesBlockLight() {
			return this.isSideLit;
		}

		@Override
		public boolean isCustomRenderer() {
			return false;
		}

		@Override
		public TextureAtlasSprite getParticleIcon() {
			return this.particle;
		}

		@Override
		public ItemOverrides getOverrides() {
			return this.overrides;
		}

		@Override
		public ItemTransforms getTransforms() {
			return this.transforms;
		}

		@Override
		public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
			return this.baseModel.getRenderTypes(state, rand, data);
		}
	}

	public static final class Loader implements IGeometryLoader<RandomOffsetBlockModel> {
		public static final RandomOffsetBlockModel.Loader INSTANCE = new RandomOffsetBlockModel.Loader();

		private Loader() {}

		@Override
		public RandomOffsetBlockModel read(JsonObject jsonObject, JsonDeserializationContext deserializationContext) {
			return new RandomOffsetBlockModel(deserializationContext.deserialize(GsonHelper.getAsJsonObject(jsonObject, "base"), BlockModel.class));
		}
	}
}
