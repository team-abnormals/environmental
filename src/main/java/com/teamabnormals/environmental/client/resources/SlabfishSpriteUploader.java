package com.teamabnormals.environmental.client.resources;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;

/**
 * <p>Manages all slabfish textures in the atlas.</p>
 *
 * @author Ocelot
 */
public final class SlabfishSpriteUploader extends TextureAtlasHolder {
	public static final ResourceLocation ATLAS_LOCATION = Environmental.location("textures/atlas/slabfish.png");

	private static SlabfishSpriteUploader spriteUploader;

	private SlabfishSpriteUploader(TextureManager textureManager) {
		super(textureManager, ATLAS_LOCATION, Environmental.location("slabfish"));
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
		bus.addListener(EventPriority.NORMAL, false, RegisterColorHandlersEvent.Block.class, event ->
		{
			spriteUploader = new SlabfishSpriteUploader(Minecraft.getInstance().getTextureManager());
			ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
			if (resourceManager instanceof ReloadableResourceManager) {
				((ReloadableResourceManager) resourceManager).registerReloadListener(spriteUploader);
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
