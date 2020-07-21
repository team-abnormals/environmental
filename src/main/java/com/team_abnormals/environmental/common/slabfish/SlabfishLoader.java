package com.team_abnormals.environmental.common.slabfish;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.team_abnormals.environmental.common.network.message.SSyncSlabfishTypeMessage;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.team_abnormals.environmental.core.Environmental;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.thread.EffectiveSide;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>Loads all slabfish from data packs as a server implementation of {@link SlabfishManager}.</p>
 *
 * @author Ocelot
 */
public class SlabfishLoader extends JsonReloadListener implements SlabfishManager
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer()).registerTypeAdapter(SlabfishType.class, new SlabfishType.Deserializer()).registerTypeAdapter(SlabfishCondition.class, new SlabfishCondition.Deserializer()).create();
    static SlabfishLoader instance;

    private final Map<ResourceLocation, SlabfishType> slabfishTypes;

    public SlabfishLoader()
    {
        super(GSON, "slabfish");
        this.slabfishTypes = new HashMap<>();
        instance = this;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, IResourceManager resourceManager, IProfiler profiler)
    {
        Map<ResourceLocation, SlabfishType> parsed = new HashMap<>();
        object.forEach(((location, json) ->
        {
            try
            {
                SlabfishType slabfishType = GSON.fromJson(json, SlabfishType.class).setRegistryName(location);
                LOGGER.debug("Registered Slabfish Type: " + location);
                LOGGER.debug("Rarity: " + slabfishType.getRarity());
                parsed.put(location, slabfishType);
            }
            catch (Exception e)
            {
                LOGGER.error("Parsing error loading custom slabfish " + location, e);
            }
        }));
        parsed.put(DEFAULT_SLABFISH.getRegistryName(), DEFAULT_SLABFISH);

        LOGGER.info("Loaded " + parsed.size() + " Slabfish Types");

        this.slabfishTypes.clear();
        this.slabfishTypes.putAll(parsed);

        if (EffectiveSide.get().isServer())
            Environmental.PLAY.send(PacketDistributor.ALL.noArg(), new SSyncSlabfishTypeMessage());
    }

    @Nullable
    @Override
    public SlabfishType getSlabfish(ResourceLocation registryName)
    {
        return this.slabfishTypes.get(registryName);
    }

    @Override
    public SlabfishType getRandom(Predicate<SlabfishType> predicate, Random random)
    {
        if (this.slabfishTypes.isEmpty())
            return DEFAULT_SLABFISH;
        return this.slabfishTypes.values().stream().filter(predicate).skip(random.nextInt(this.slabfishTypes.size())).findFirst().orElse(DEFAULT_SLABFISH);
    }

    @Override
    public SlabfishType[] getAllSlabfish()
    {
        return this.slabfishTypes.values().toArray(new SlabfishType[0]);
    }
}
