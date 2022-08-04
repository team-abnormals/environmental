package com.teamabnormals.environmental.client.resources;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.stream.Stream;

/**
 * <p>Manages all slabfish textures in the atlas.</p>
 *
 * @author Ocelot
 */
public final class SlabfishSpriteUploader extends TextureAtlasHolder {
	public static final ResourceLocation ATLAS_LOCATION = new ResourceLocation(Environmental.MOD_ID, "textures/atlas/slabfish.png");

	private static SlabfishSpriteUploader spriteUploader;

	private SlabfishSpriteUploader(TextureManager textureManager) {
		super(textureManager, ATLAS_LOCATION, "entity/slabfish");
	}

	@Override
	protected Stream<ResourceLocation> getResourcesToLoad() {
		ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
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
