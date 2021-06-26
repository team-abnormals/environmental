package com.minecraftabnormals.environmental.client.render;

import com.minecraftabnormals.environmental.core.Environmental;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.stream.Stream;

/**
 * <p>Manages all slabfish textures in the atlas.</p>
 *
 * @author Ocelot
 */
public final class SlabfishSpriteUploader extends SpriteUploader {

	public static final ResourceLocation ATLAS_LOCATION = new ResourceLocation(Environmental.MOD_ID, "textures/atlas/slabfish.png");

	private static SlabfishSpriteUploader spriteUploader;

	private SlabfishSpriteUploader(TextureManager textureManager) {
		super(textureManager, ATLAS_LOCATION, "entity/slabfish");
	}

	@Override
	protected Stream<ResourceLocation> getResourcesToLoad() {
		IResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
		return resourceManager.listResources("textures/entity/slabfish", file -> file.endsWith(".png")).stream().map(location -> new ResourceLocation(location.getNamespace(), location.getPath().substring("textures/entity/slabfish/".length(), location.getPath().length() - 4)));
	}

	@Override
	public TextureAtlasSprite getSprite(ResourceLocation location) {
		return super.getSprite(location);
	}

	/**
	 * Initializes the uploader for slabfish.
	 *
	 * @param bus The bus to register on
	 */
	public static void init(IEventBus bus) {
		bus.addListener(EventPriority.NORMAL, false, ColorHandlerEvent.Block.class, event ->
		{
			spriteUploader = new SlabfishSpriteUploader(Minecraft.getInstance().getTextureManager());
			IResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
			if (resourceManager instanceof IReloadableResourceManager) {
				((IReloadableResourceManager) resourceManager).registerReloadListener(spriteUploader);
			}
		});
	}

	/**
	 * @return The instanceof the sprite uploader
	 */
	public static SlabfishSpriteUploader instance() {
		return spriteUploader;
	}
}
