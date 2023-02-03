package com.teamabnormals.environmental.common.slabfish;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import com.teamabnormals.blueprint.core.other.tags.BlueprintBiomeTags;
import com.teamabnormals.environmental.common.slabfish.condition.*;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext.Event;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext.Time;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBiomeTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SlabfishTypeProvider implements DataProvider, BuiltInSlabfishTypes {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	private final DataGenerator generator;
	private final String modId;
	private final Map<ResourceLocation, SlabfishType> slabfishTypes = new LinkedHashMap<>();

	public SlabfishTypeProvider(DataGenerator generator, String modId) {
		this.generator = generator;
		this.modId = modId;
	}

	protected void registerSlabfish() {
		this.addBasicSlabfish(BADLANDS, new SlabfishInBiomeCondition(BiomeTags.IS_BADLANDS));
		this.addBasicSlabfish(BAMBOO, 1, new SlabfishInBiomeCondition(Biomes.BAMBOO_JUNGLE));
		this.addBasicSlabfish(BEACH, new SlabfishInBiomeCondition(BiomeTags.IS_BEACH));
		this.addBasicSlabfish(BLOSSOM, 1, new SlabfishOrCondition(new SlabfishInBiomeCondition(EnvironmentalBiomes.BLOSSOM_WOODS.get()), new SlabfishInBiomeCondition(EnvironmentalBiomes.BLOSSOM_VALLEYS.get())));
		this.addBasicSlabfish(DARK_FOREST, 1, new SlabfishInBiomeCondition(Biomes.DARK_FOREST));
		this.addBasicSlabfish(DESERT, new SlabfishInBiomeCondition(Biomes.DESERT));
		this.addBasicSlabfish(FLOWER_FOREST, 1, new SlabfishInBiomeCondition(Biomes.FLOWER_FOREST));
		this.addBasicSlabfish(FOREST, new SlabfishInBiomeCondition(BiomeTags.IS_FOREST));
		this.addBasicSlabfish(HILL, new SlabfishInBiomeCondition(BiomeTags.IS_HILL));
		this.addBasicSlabfish(ICE_SPIKES, 1, new SlabfishInBiomeCondition(Biomes.ICE_SPIKES));
		this.addBasicSlabfish(JUNGLE, new SlabfishInBiomeCondition(BiomeTags.IS_JUNGLE));
		this.addBasicSlabfish(MARSH, new SlabfishInBiomeCondition(EnvironmentalBiomes.MARSH.get()));
		this.addBasicSlabfish(PLAINS, 1, new SlabfishOrCondition(new SlabfishInBiomeCondition(Biomes.PLAINS), new SlabfishInBiomeCondition(Biomes.SUNFLOWER_PLAINS), new SlabfishInBiomeCondition(Biomes.MEADOW)));
		this.addBasicSlabfish(SAVANNA, new SlabfishInBiomeCondition(BlueprintBiomeTags.IS_SAVANNA));
		this.addBasicSlabfish(SNOWY, new SlabfishOrCondition(new SlabfishInBiomeCondition(Biomes.SNOWY_PLAINS), new SlabfishInBiomeCondition(Biomes.SNOWY_BEACH), new SlabfishInBiomeCondition(Biomes.SNOWY_TAIGA), new SlabfishInBiomeCondition(Biomes.FROZEN_RIVER), new SlabfishInBiomeCondition(BiomeTags.IS_MOUNTAIN)));
		this.addBasicSlabfish(TAIGA, new SlabfishInBiomeCondition(BiomeTags.IS_TAIGA));

		this.addBasicSlabfish(RIVER, new SlabfishInBiomeCondition(Biomes.RIVER));
		this.addBasicSlabfish(OCEAN, new SlabfishInBiomeCondition(BiomeTags.IS_OCEAN));
		this.addBasicSlabfish(WARM_OCEAN, 1, new SlabfishOrCondition(new SlabfishInBiomeCondition(Biomes.WARM_OCEAN)));
		this.addBasicSlabfish(FROZEN_OCEAN, 1, new SlabfishOrCondition(new SlabfishInBiomeCondition(Biomes.FROZEN_OCEAN), new SlabfishInBiomeCondition(Biomes.DEEP_FROZEN_OCEAN)));
		this.addBasicSlabfish(DROWNED, 2, new SlabfishInBiomeCondition(BiomeTags.IS_OCEAN), new SlabfishHeightCondition(-64, 50), new SlabfishInFluidCondition(FluidTags.WATER));

		this.addBasicSlabfish(NETHER, 1, new SlabfishDimensionCondition(DimensionType.NETHER_LOCATION.location()));
		this.addBasicSlabfish(CRIMSON, 1, new SlabfishInBiomeCondition(Biomes.CRIMSON_FOREST));
		this.addBasicSlabfish(WARPED, 1, new SlabfishInBiomeCondition(Biomes.WARPED_FOREST));
		this.addBasicSlabfish(SOUL_SAND_VALLEY, 1, new SlabfishInBiomeCondition(Biomes.SOUL_SAND_VALLEY));
		this.addBasicSlabfish(BASALT_DELTAS, 1, new SlabfishInBiomeCondition(Biomes.BASALT_DELTAS));

		this.addBasicSlabfish(END, new SlabfishDimensionCondition(DimensionType.END_LOCATION.location()));
		this.addBasicSlabfish(CHORUS, 1, new SlabfishInBiomeCondition(Biomes.END_HIGHLANDS));

		this.add(MUSHROOM, SlabfishType.builder().setWeight(2).addCondition(new SlabfishOrCondition(new SlabfishAndCondition(new SlabfishEventCondition(Event.LIGHTNING), new SlabfishTypeCondition(new ResourceLocation(Environmental.MOD_ID, "mushroom"))), new SlabfishAndCondition(new SlabfishEventCondition(Event.SPAWN, Event.BREED), new SlabfishInBiomeCondition(Biomes.MUSHROOM_FIELDS)))).build());
		this.add(BROWN_MUSHROOM, SlabfishType.builder().setWeight(2).addCondition(new SlabfishEventCondition(Event.LIGHTNING)).addCondition(new SlabfishTypeCondition(BuiltInSlabfishTypes.MUSHROOM.location())).build());
		this.add(SKELETON, SlabfishType.builder().setWeight(2).addCondition(new SlabfishEventCondition(Event.LIGHTNING)).build());
		this.add(STRAY, SlabfishType.builder().setWeight(2).addCondition(new SlabfishEventCondition(Event.BREED)).addCondition(new SlabfishBreedCondition(BuiltInSlabfishTypes.SKELETON.location(), BuiltInSlabfishTypes.SKELETON.location())).addCondition(new SlabfishOrCondition(new SlabfishInBiomeCondition(Biomes.SNOWY_PLAINS), new SlabfishInBiomeCondition(Biomes.SNOWY_BEACH), new SlabfishInBiomeCondition(Biomes.SNOWY_TAIGA), new SlabfishInBiomeCondition(Biomes.FROZEN_RIVER), new SlabfishInBiomeCondition(BiomeTags.IS_MOUNTAIN))).build());
		this.add(WITHER, SlabfishType.builder().setWeight(2).addCondition(new SlabfishEventCondition(Event.BREED)).addCondition(new SlabfishBreedCondition(BuiltInSlabfishTypes.SKELETON.location(), BuiltInSlabfishTypes.SKELETON.location())).addCondition(new SlabfishDimensionCondition(DimensionType.NETHER_LOCATION.location())).build());
		this.addBasicSlabfish(CAVE, 2, new SlabfishHeightCondition(-64, 20), new SlabfishLightCondition(0, 0, LightLayer.SKY));
		this.addBasicSlabfish(GHOST, new SlabfishImpossibleCondition());
		this.addBasicSlabfish(SKY, 2, new SlabfishHeightCondition(256, 512));
		this.addBasicSlabfish(NIGHTMARE, new SlabfishInsomniaCondition(), new SlabfishTimeCondition(Time.NIGHT));
		this.addBasicSlabfish(TOTEM, 2, new SlabfishRaidCondition());

		this.addRenameSlabfish(BAGEL, new String[]{"bagel", "shyguy", "shy guy", "bagel.jpg"});
		this.addRenameSlabfish(BMO, new String[]{"bmo", "beemo", "be more"});
		this.addRenameSlabfish(CAMERON, new String[]{"cameron", "cam"});
		this.addRenameSlabfish(EVE, new String[]{"eve"});
		this.add(GORE, SlabfishType.builder().setWeight(10).setBackpack(new ResourceLocation(this.getModID(), "gore")).addCondition(new SlabfishRenameCondition(new String[]{"gore", "gore.", "musicano"}, false)).build());
		this.addRegexRenameSlabfish(JACKSON, "(?i)\\bjackson|\\bjason|\\bjson|.*\\.json$");
		this.addRenameSlabfish(MINION, new String[]{"five", "paradiscal", "minion", "despicable"});
		this.add(OCELOT, SlabfishType.builder().setWeight(10).setBackpack(new ResourceLocation(this.getModID(), "ocelot")).addCondition(new SlabfishRenameCondition(Pattern.compile("(?i).*-e$"))).build());
		this.addRenameSlabfish(RENREN, new String[]{"ren", "renren", "slabrave"});
		this.addRenameSlabfish(SMELLY, new String[]{"smelly", "stinky", "smellysox", "thefaceofgaming"});
		this.add(SNAKE_BLOCK, SlabfishType.builder().setWeight(10).setBackpack(new ResourceLocation(this.getModID(), "snake_block")).addCondition(new SlabfishRenameCondition(new String[]{"snake", "snake block", "snakeblock"}, false)).build());

		this.addBasicSlabfish(RAINFOREST, new SlabfishAndCondition(new SlabfishModLoadedCondition("atmospheric"), new SlabfishInBiomeCondition(EnvironmentalBiomeTags.IS_RAINFOREST)));
		this.addBasicSlabfish(DUNES, new SlabfishAndCondition(new SlabfishModLoadedCondition("atmospheric"), new SlabfishInBiomeCondition(EnvironmentalBiomeTags.IS_DUNES)));
		this.addBasicSlabfish(MAPLE, new SlabfishAndCondition(new SlabfishModLoadedCondition("autumnity"), new SlabfishInBiomeCondition(EnvironmentalBiomeTags.IS_MAPLE)));
		this.addBasicSlabfish(POISE, new SlabfishAndCondition(new SlabfishModLoadedCondition("endergetic"), new SlabfishInBiomeCondition(EnvironmentalBiomeTags.IS_POISE)));
	}

	protected void add(ResourceKey<SlabfishType> key, SlabfishType type) {
		this.addSlabfish(key.location(), type);
	}

	protected void add(String path, SlabfishType type) {
		this.addSlabfish(new ResourceLocation(this.getModID(), path), type);
	}

	protected void add(ResourceLocation id, SlabfishType type) {
		this.addSlabfish(id, type);
	}

	public void addBasicSlabfish(ResourceKey<SlabfishType> key, SlabfishCondition... conditions) {
		this.addBasicSlabfish(key, 0, conditions);
	}

	public void addBasicSlabfish(ResourceKey<SlabfishType> key, int priority, SlabfishCondition... conditions) {
		SlabfishType.Builder builder = SlabfishType.builder().setWeight(priority).addCondition(new SlabfishEventCondition(Event.SPAWN, Event.BREED));
		Arrays.stream(conditions).forEach(builder::addCondition);
		this.add(key, builder.build());
	}

	public void addRenameSlabfish(ResourceKey<SlabfishType> key, String[] names) {
		this.add(key, SlabfishType.builder().setWeight(10).addCondition(new SlabfishRenameCondition(names, false)).build());
	}

	public void addRegexRenameSlabfish(ResourceKey<SlabfishType> key, String regex) {
		this.add(key, SlabfishType.builder().setWeight(10).addCondition(new SlabfishRenameCondition(Pattern.compile(regex))).build());
	}

	private void addSlabfish(ResourceLocation path, SlabfishType definition) {
		if (this.slabfishTypes.put(path, definition) != null) {
			throw new IllegalStateException("Slabfish type '" + path + "' already exists");
		}
	}

	@Override
	public void run(HashCache cache) throws IOException {
		this.slabfishTypes.clear();
		this.registerSlabfish();

		for (Map.Entry<ResourceLocation, SlabfishType> entry : this.slabfishTypes.entrySet()) {
			ResourceLocation location = entry.getKey();
			DataProvider.save(GSON, cache, SlabfishType.CODEC.encodeStart(JsonOps.INSTANCE, entry.getValue()).getOrThrow(false, LOGGER::error), this.generator.getOutputFolder().resolve("data/" + location.getNamespace() + "/" + Environmental.MOD_ID + "/slabfish_type/" + location.getPath() + ".json"));
		}
	}

	public String getModID() {
		return this.modId;
	}

	@Override
	public String getName() {
		return "Slabfish Types";
	}
}