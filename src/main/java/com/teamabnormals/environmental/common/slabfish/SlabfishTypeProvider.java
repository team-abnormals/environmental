package com.teamabnormals.environmental.common.slabfish;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import com.teamabnormals.blueprint.core.other.tags.BlueprintBiomeTags;
import com.teamabnormals.environmental.common.slabfish.condition.*;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext.Event;
import com.teamabnormals.environmental.common.slabfish.condition.SlabfishConditionContext.Time;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBiomes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SlabfishTypeProvider implements DataProvider {
	private static final Logger LOGGER = LogManager.getLogger();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	private final DataGenerator generator;
	private final String modId;
	private final ExistingFileHelper existingFileHelper;
	private final Map<ResourceLocation, SlabfishType> slabfishTypes = new LinkedHashMap<>();

	public SlabfishTypeProvider(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
		this.generator = generator;
		this.modId = modId;
		this.existingFileHelper = existingFileHelper;
	}

	protected void registerSlabfish() {
		this.addBasicSlabfish("badlands", new SlabfishInBiomeCondition(BiomeTags.IS_BADLANDS));
		this.addBasicSlabfish("bamboo", 1, new SlabfishInBiomeCondition(Biomes.BAMBOO_JUNGLE));
		this.addBasicSlabfish("basalt_deltas", 1, new SlabfishInBiomeCondition(Biomes.BASALT_DELTAS));
		this.addBasicSlabfish("beach", new SlabfishInBiomeCondition(BiomeTags.IS_BEACH));
		this.addBasicSlabfish("blossom", 1, new SlabfishOrCondition(new SlabfishInBiomeCondition(EnvironmentalBiomes.BLOSSOM_WOODS.get()), new SlabfishInBiomeCondition(EnvironmentalBiomes.BLOSSOM_HILLS.get()), new SlabfishInBiomeCondition(EnvironmentalBiomes.BLOSSOM_HIGHLANDS.get()), new SlabfishInBiomeCondition(EnvironmentalBiomes.BLOSSOM_VALLEYS.get())));
		this.addBasicSlabfish("chorus", 1, new SlabfishInBiomeCondition(Biomes.END_HIGHLANDS));
		this.addBasicSlabfish("crimson", 1, new SlabfishInBiomeCondition(Biomes.CRIMSON_FOREST));
		this.addBasicSlabfish("dark_forest", 1, new SlabfishInBiomeCondition(Biomes.DARK_FOREST));
		this.addBasicSlabfish("desert", new SlabfishInBiomeCondition(Biomes.DESERT));
		this.addBasicSlabfish("drowned", 2, new SlabfishInBiomeCondition(BiomeTags.IS_OCEAN), new SlabfishHeightCondition(-64, 50), new SlabfishInFluidCondition(FluidTags.WATER));
		this.addBasicSlabfish("end", new SlabfishDimensionCondition(DimensionType.END_LOCATION.location()));
		this.addBasicSlabfish("flower_forest", 1, new SlabfishInBiomeCondition(Biomes.FLOWER_FOREST));
		this.addBasicSlabfish("forest", new SlabfishInBiomeCondition(BiomeTags.IS_FOREST));
		this.addBasicSlabfish("frozen_ocean", 1, new SlabfishOrCondition(new SlabfishInBiomeCondition(Biomes.FROZEN_OCEAN), new SlabfishInBiomeCondition(Biomes.DEEP_FROZEN_OCEAN)));
		this.addBasicSlabfish("hill", new SlabfishInBiomeCondition(BiomeTags.IS_HILL));
		this.addBasicSlabfish("ice_spikes", 1, new SlabfishInBiomeCondition(Biomes.ICE_SPIKES));
		this.addBasicSlabfish("jungle", new SlabfishInBiomeCondition(BiomeTags.IS_JUNGLE));
		this.addBasicSlabfish("marsh", new SlabfishOrCondition(new SlabfishInBiomeCondition(EnvironmentalBiomes.MARSH.get()), new SlabfishInBiomeCondition(EnvironmentalBiomes.MUSHROOM_MARSH.get())));
		this.addBasicSlabfish("nether", 1, new SlabfishDimensionCondition(DimensionType.NETHER_LOCATION.location()));
		this.addBasicSlabfish("ocean", new SlabfishInBiomeCondition(BiomeTags.IS_OCEAN));
		this.addBasicSlabfish("plains", 1, new SlabfishOrCondition(new SlabfishInBiomeCondition(Biomes.PLAINS), new SlabfishInBiomeCondition(Biomes.SUNFLOWER_PLAINS), new SlabfishInBiomeCondition(Biomes.MEADOW)));
		this.addBasicSlabfish("river", new SlabfishInBiomeCondition(Biomes.RIVER));
		this.addBasicSlabfish("savanna", new SlabfishInBiomeCondition(BlueprintBiomeTags.IS_SAVANNA));
		this.addBasicSlabfish("snowy", new SlabfishOrCondition(new SlabfishInBiomeCondition(Biomes.SNOWY_PLAINS), new SlabfishInBiomeCondition(Biomes.SNOWY_BEACH), new SlabfishInBiomeCondition(Biomes.SNOWY_TAIGA), new SlabfishInBiomeCondition(Biomes.FROZEN_RIVER), new SlabfishInBiomeCondition(BiomeTags.IS_MOUNTAIN)));
		this.addBasicSlabfish("soul_sand_valley", 1, new SlabfishInBiomeCondition(Biomes.SOUL_SAND_VALLEY));
		this.addBasicSlabfish("warped", 1, new SlabfishInBiomeCondition(Biomes.WARPED_FOREST));
		this.addBasicSlabfish("warm_ocean", 1, new SlabfishOrCondition(new SlabfishInBiomeCondition(Biomes.WARM_OCEAN)));
		this.addBasicSlabfish("taiga", new SlabfishInBiomeCondition(BiomeTags.IS_TAIGA));

		this.addRenameSlabfish("bagel", new String[]{"bagel", "shyguy", "shy guy", "bagel.jpg"});
		this.addRenameSlabfish("bmo", new String[]{"bmo", "beemo", "be more"});
		this.addRenameSlabfish("cameron", new String[]{"cameron", "cam"});
		this.addRenameSlabfish("eve", new String[]{"eve"});
		this.add("gore", SlabfishType.builder().setWeight(10).setBackpack(new ResourceLocation(this.getModID(), "gore")).addCondition(new SlabfishRenameCondition(new String[]{"gore", "gore.", "musicano"}, false)).build());
		this.addRegexRenameSlabfish("jackson", "(?i)\\bjackson|\\bjason|\\bjson|.*\\.json$");
		this.addRenameSlabfish("minion", new String[]{"five", "paradiscal", "minion", "despicable"});
		this.add("ocelot", SlabfishType.builder().setWeight(10).setBackpack(new ResourceLocation(this.getModID(), "ocelot")).addCondition(new SlabfishRenameCondition(Pattern.compile("(?i).*-e$"))).build());
		this.addRenameSlabfish("renren", new String[]{"ren", "renren", "slabrave"});
		this.addRenameSlabfish("smelly", new String[]{"smelly", "stinky", "smellysox", "thefaceofgaming"});
		this.add("snake_block", SlabfishType.builder().setWeight(10).setBackpack(new ResourceLocation(this.getModID(), "snake_block")).addCondition(new SlabfishRenameCondition(new String[]{"snake", "snake block", "snakeblock"}, false)).build());

		this.add("brown_mushroom", SlabfishType.builder().setWeight(2).addCondition(new SlabfishEventCondition(Event.LIGHTNING)).addCondition(new SlabfishTypeCondition(new ResourceLocation(Environmental.MOD_ID, "mushroom"))).build());
		this.addBasicSlabfish("cave", 2, new SlabfishHeightCondition(-64, 20), new SlabfishLightCondition(0, 0, LightLayer.SKY));
		this.addBasicSlabfish("ghost", new SlabfishImpossibleCondition());
		this.add("mushroom", SlabfishType.builder().setWeight(2).addCondition(new SlabfishOrCondition(new SlabfishAndCondition(new SlabfishEventCondition(Event.LIGHTNING), new SlabfishTypeCondition(new ResourceLocation(Environmental.MOD_ID, "mushroom"))), new SlabfishAndCondition(new SlabfishEventCondition(Event.SPAWN, Event.BREED), new SlabfishInBiomeCondition(Biomes.MUSHROOM_FIELDS)))).build());
		this.addBasicSlabfish("nightmare", new SlabfishInsomniaCondition(), new SlabfishTimeCondition(Time.NIGHT));
		this.add("skeleton", SlabfishType.builder().setWeight(2).addCondition(new SlabfishEventCondition(Event.LIGHTNING)).build());
		this.addBasicSlabfish("sky", 2, new SlabfishHeightCondition(256, 512));
		this.add("stray", SlabfishType.builder().setWeight(2).addCondition(new SlabfishEventCondition(Event.BREED)).addCondition(new SlabfishBreedCondition(new ResourceLocation(this.getModID(), "skeleton"), new ResourceLocation(this.getModID(), "skeleton"))).addCondition(new SlabfishOrCondition(new SlabfishInBiomeCondition(Biomes.SNOWY_PLAINS), new SlabfishInBiomeCondition(Biomes.SNOWY_BEACH), new SlabfishInBiomeCondition(Biomes.SNOWY_TAIGA), new SlabfishInBiomeCondition(Biomes.FROZEN_RIVER), new SlabfishInBiomeCondition(BiomeTags.IS_MOUNTAIN))).build());
		this.addBasicSlabfish("totem", 2, new SlabfishRaidCondition());
		this.add("wither", SlabfishType.builder().setWeight(2).addCondition(new SlabfishEventCondition(Event.BREED)).addCondition(new SlabfishBreedCondition(new ResourceLocation(this.getModID(), "skeleton"), new ResourceLocation(this.getModID(), "skeleton"))).addCondition(new SlabfishDimensionCondition(DimensionType.NETHER_LOCATION.location())).build());
	}

	protected void add(String path, SlabfishType type) {
		this.addSlabfish(new ResourceLocation(this.getModID(), path), type);
	}

	protected void add(ResourceLocation id, SlabfishType type) {
		this.addSlabfish(id, type);
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

	public void addBasicSlabfish(String name, SlabfishCondition... conditions) {
		this.addBasicSlabfish(name, 0, conditions);
	}

	public void addBasicSlabfish(String name, int priority, SlabfishCondition... conditions) {
		SlabfishType.Builder builder = SlabfishType.builder().setWeight(priority).addCondition(new SlabfishEventCondition(Event.SPAWN, Event.BREED));
		Arrays.stream(conditions).forEach(builder::addCondition);
		this.add(name, builder.build());
	}

	public void addRenameSlabfish(String name, String[] names) {
		this.add(name, SlabfishType.builder().setWeight(10).addCondition(new SlabfishRenameCondition(names, false)).build());
	}

	public void addRegexRenameSlabfish(String name, String regex) {
		this.add(name, SlabfishType.builder().setWeight(10).addCondition(new SlabfishRenameCondition(Pattern.compile(regex))).build());
	}
}