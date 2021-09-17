package com.minecraftabnormals.environmental.client.model;

import com.minecraftabnormals.environmental.common.slabfish.SlabfishManager;
import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.LogicalSide;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * <p>Super hack that selects models for slabfish buckets based </p>
 *
 * @author Ocelot
 */
public class SlabfishBucketModel implements IBakedModel {
	private final IBakedModel model;
	private final ItemOverrideList overrideList;

	public SlabfishBucketModel(ModelManager modelManager) {
		this.model = modelManager.getModel(new ResourceLocation(Environmental.MOD_ID, "models/item/slabfish_bucket/swamp"));
		this.overrideList = new Overrides(modelManager);
	}

	@Override
	public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
		return this.model.getQuads(state, side, rand);
	}

	@Override
	public boolean useAmbientOcclusion() {
		return this.model.useAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return this.model.isGui3d();
	}

	@Override
	public boolean usesBlockLight() {
		return this.model.usesBlockLight();
	}

	@Override
	public boolean isCustomRenderer() {
		return this.model.isCustomRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleIcon() {
		return this.model.getParticleIcon();
	}

	@Override
	public ItemOverrideList getOverrides() {
		return this.overrideList;
	}

	private static class Overrides extends ItemOverrideList {
		private final ModelManager modelManager;
		private final IBakedModel model;
		private final Map<String, ResourceLocation> locationCache;
		private final Map<ResourceLocation, ResourceLocation> modelLocations;

		private Overrides(ModelManager modelManager) {
			this.modelManager = modelManager;
			this.model = modelManager.getModel(new ResourceLocation(Environmental.MOD_ID, "item/slabfish_bucket/swamp"));
			this.locationCache = new HashMap<>();
			this.modelLocations = new HashMap<>();
			for (ResourceLocation location : Minecraft.getInstance().getResourceManager().listResources("models/item/slabfish_bucket", s -> s.endsWith(".json")))
				this.modelLocations.put(new ResourceLocation(location.getNamespace(), location.getPath().substring("models/item/slabfish_bucket/".length(), location.getPath().length() - ".json".length())), new ResourceLocation(location.getNamespace(), location.getPath().substring("models/".length(), location.getPath().length() - ".json".length())));
		}

		@Nullable
		@Override
		public IBakedModel resolve(IBakedModel model, ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity entity) {
			if (stack.getTag() != null && stack.getTag().contains("SlabfishType", Constants.NBT.TAG_STRING)) {
				ResourceLocation slabfishType = SlabfishManager.get(LogicalSide.CLIENT).getSlabfishType(this.locationCache.computeIfAbsent(stack.getTag().getString("SlabfishType"), ResourceLocation::new)).orElse(SlabfishManager.DEFAULT_SLABFISH).getRegistryName();
				if (this.modelLocations.containsKey(slabfishType))
					return this.modelManager.getModel(this.modelLocations.get(slabfishType));
			}
			return this.model;
		}
	}
}
