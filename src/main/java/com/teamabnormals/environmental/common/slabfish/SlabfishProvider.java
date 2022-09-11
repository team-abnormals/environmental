package com.teamabnormals.environmental.common.slabfish;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.logging.LogUtils;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag.Builder;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class SlabfishProvider implements DataProvider {
	private static final Logger LOGGER = LogUtils.getLogger();
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
	protected final DataGenerator generator;
	protected final Map<ResourceLocation, Builder> builders = Maps.newLinkedHashMap();
	protected final String modId;
	protected final ExistingFileHelper existingFileHelper;

	public SlabfishProvider(DataGenerator generator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
		this.generator = generator;
		this.modId = modId;
		this.existingFileHelper = existingFileHelper;
	}

	@Override
	public void run(HashCache directoryCache) throws IOException {
		Path path = this.generator.getOutputFolder();
		Set<ResourceLocation> set = Sets.newHashSet();
		Consumer<SlabfishType> consumer = (slabfishType) -> {
			if (!set.add(slabfishType.getRegistryName())) {
				throw new IllegalStateException("Duplicate slabfish type " + slabfishType.getRegistryName());
			} else {
				Path path1 = createPath(path, slabfishType);

				try {
					DataProvider.save(GSON, directoryCache, slabfishType.serializeToJson(), path1);
				} catch (IOException ioexception) {
					LOGGER.error("Couldn't save slabfish type {}", path1, ioexception);
				}
			}
		};

		registerSlabfishTypes(consumer, existingFileHelper);
	}

	protected void registerSlabfishTypes(Consumer<SlabfishType> consumer, ExistingFileHelper existingFileHelper) {
		SlabfishManager.DEFAULT_SLABFISH.save(consumer, new ResourceLocation(Environmental.MOD_ID, "test"));
	}

	private static Path createPath(Path path, SlabfishType slabfish) {
		return path.resolve("data/" + slabfish.getRegistryName().getNamespace() + "/slabfish/type/" + slabfish.getRegistryName().getPath() + ".json");
	}

	@Override
	public String getName() {
		return "Slabfish";
	}
}
