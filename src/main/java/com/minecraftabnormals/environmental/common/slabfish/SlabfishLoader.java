package com.minecraftabnormals.environmental.common.slabfish;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.minecraftabnormals.environmental.common.network.message.SSyncBackpackTypeMessage;
import com.minecraftabnormals.environmental.common.network.message.SSyncSlabfishTypeMessage;
import com.minecraftabnormals.environmental.common.network.message.SSyncSweaterTypeMessage;
import com.minecraftabnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.minecraftabnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.minecraftabnormals.environmental.core.Environmental;

import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

/**
 * <p>Loads all slabfish from data packs as a server implementation of {@link SlabfishManager}.</p>
 *
 * @author Ocelot
 */
public class SlabfishLoader extends JsonReloadListener implements SlabfishManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .registerTypeAdapter(ITextComponent.class, new ITextComponent.Serializer())
            .registerTypeAdapter(SlabfishType.class, new SlabfishType.Deserializer())
            .registerTypeAdapter(SlabfishCondition.class, new SlabfishCondition.Deserializer())
            .registerTypeAdapter(SweaterType.class, new SweaterType.Deserializer())
            .registerTypeAdapter(BackpackType.class, new BackpackType.Deserializer())
            .create();

    static SlabfishLoader instance;

    private final Map<ResourceLocation, SlabfishType> slabfishTypes;
    private final Map<ResourceLocation, SweaterType> sweaterTypes;
    private final Map<ResourceLocation, BackpackType> backpackTypes;

    public SlabfishLoader() {
        super(GSON, "slabfish");
        this.slabfishTypes = new HashMap<>();
        this.sweaterTypes = new HashMap<>();
        this.backpackTypes = new HashMap<>();
        instance = this;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, IResourceManager resourceManager, IProfiler profiler) {
        Map<ResourceLocation, SlabfishType> parsedSlabfishTypes = new HashMap<>();
        Map<ResourceLocation, SweaterType> parsedSweaterTypes = new HashMap<>();
        Map<ResourceLocation, BackpackType> parsedBackpackTypes = new HashMap<>();

        // Force this to exist if there is no overriding JSON
        parsedBackpackTypes.put(BROWN_BACKPACK.getRegistryName(), BROWN_BACKPACK);

        object.forEach(((location, json) ->
        {
            if (location.getPath().startsWith("type")) {
                ResourceLocation registryName = new ResourceLocation(location.getNamespace(), location.getPath().substring("type/".length()));
                try {
                    parsedSlabfishTypes.put(registryName, GSON.fromJson(json, SlabfishType.class).setRegistryName(registryName));
                } catch (Exception e) {
                    LOGGER.error("Parsing error loading custom slabfish " + registryName, e);
                }
            } else if (location.getPath().startsWith("sweater")) {
                ResourceLocation registryName = new ResourceLocation(location.getNamespace(), location.getPath().substring("sweater/".length()));
                try {
                    parsedSweaterTypes.put(registryName, GSON.fromJson(json, SweaterType.class).setRegistryName(registryName));
                } catch (Exception e) {
                    LOGGER.error("Parsing error loading custom sweater " + registryName, e);
                }
            } else if (location.getPath().startsWith("backpack")) {
                ResourceLocation registryName = new ResourceLocation(location.getNamespace(), location.getPath().substring("backpack/".length()));
                try {
                    parsedBackpackTypes.put(registryName, GSON.fromJson(json, BackpackType.class).setRegistryName(registryName));
                } catch (Exception e) {
                    LOGGER.error("Parsing error loading custom backpack " + registryName, e);
                }
            }
        }));

        LOGGER.info("Loaded " + parsedSlabfishTypes.size() + " Slabfish Types");
        LOGGER.info("Loaded " + parsedSweaterTypes.size() + " Sweater Types");
        LOGGER.info("Loaded " + parsedBackpackTypes.size() + " Backpack Types");

        this.slabfishTypes.clear();
        this.slabfishTypes.put(DEFAULT_SLABFISH.getRegistryName(), DEFAULT_SLABFISH);
        this.slabfishTypes.putAll(parsedSlabfishTypes);

        this.sweaterTypes.clear();
        this.sweaterTypes.put(EMPTY_SWEATER.getRegistryName(), EMPTY_SWEATER);
        this.sweaterTypes.putAll(parsedSweaterTypes);

        this.backpackTypes.clear();
        this.backpackTypes.putAll(parsedBackpackTypes);

        if (EffectiveSide.get().isServer()) {
            Environmental.PLAY.send(PacketDistributor.ALL.noArg(), new SSyncSlabfishTypeMessage());
            Environmental.PLAY.send(PacketDistributor.ALL.noArg(), new SSyncSweaterTypeMessage());
            Environmental.PLAY.send(PacketDistributor.ALL.noArg(), new SSyncBackpackTypeMessage());
        }
    }

    @Override
    public SlabfishType getSlabfishType(ResourceLocation registryName) {
        return this.slabfishTypes.getOrDefault(registryName, DEFAULT_SLABFISH);
    }

    @Override
    public SweaterType getSweaterType(ResourceLocation registryName) {
        return this.sweaterTypes.getOrDefault(registryName, EMPTY_SWEATER);
    }

    @Override
    public BackpackType getBackpackType(ResourceLocation registryName) {
        return this.backpackTypes.getOrDefault(registryName, BROWN_BACKPACK);
    }

    @Override
    public SlabfishType getSlabfishType(Predicate<SlabfishType> predicate, SlabfishConditionContext context) {
        return this.slabfishTypes.values().stream().filter(slabfishType -> predicate.test(slabfishType) && slabfishType.test(context)).max(Comparator.comparingInt(SlabfishType::getPriority)).orElse(DEFAULT_SLABFISH);
    }

    @Override
    public SweaterType getSweaterType(ItemStack stack) {
        if (this.sweaterTypes.isEmpty())
            return EMPTY_SWEATER;
        return this.sweaterTypes.values().stream().filter(sweaterType -> sweaterType.test(stack)).findFirst().orElse(EMPTY_SWEATER);
    }

    @Override
    public BackpackType getBackpackType(ItemStack stack) {
        if (this.backpackTypes.isEmpty())
            return BROWN_BACKPACK;
        return this.backpackTypes.values().stream().filter(backpackType -> backpackType.test(stack)).findFirst().orElse(BROWN_BACKPACK);
    }

    @Override
    public SlabfishType getRandomSlabfishType(Predicate<SlabfishType> predicate, Random random) {
        if (this.slabfishTypes.isEmpty())
            return DEFAULT_SLABFISH;
        SlabfishType[] slabfishTypes = this.slabfishTypes.values().stream().filter(predicate).toArray(SlabfishType[]::new);
        return slabfishTypes.length == 0 ? DEFAULT_SLABFISH : slabfishTypes[random.nextInt(slabfishTypes.length)];
    }

    @Override
    public boolean hasSweaterType(ResourceLocation registryName) {
        return this.sweaterTypes.containsKey(registryName);
    }

    @Override
    public boolean hasBackpackType(ResourceLocation registryName) {
        return this.backpackTypes.containsKey(registryName);
    }

    @Override
    public boolean hasSweaterType(ItemStack stack) {
        if (this.sweaterTypes.isEmpty())
            return false;
        return this.sweaterTypes.values().stream().anyMatch(sweaterType -> sweaterType.test(stack));
    }

    @Override
    public boolean hasBackpackType(ItemStack stack) {
        if (this.backpackTypes.isEmpty())
            return false;
        return this.backpackTypes.values().stream().anyMatch(backpackType -> backpackType.test(stack));
    }

    @Override
    public SlabfishType[] getAllSlabfishTypes() {
        return this.slabfishTypes.values().toArray(new SlabfishType[0]);
    }

    @Override
    public SweaterType[] getAllSweaterTypes() {
        return this.sweaterTypes.values().toArray(new SweaterType[0]);
    }

    @Override
    public BackpackType[] getAllBackpackTypes() {
        return this.backpackTypes.values().toArray(new BackpackType[0]);
    }
}
