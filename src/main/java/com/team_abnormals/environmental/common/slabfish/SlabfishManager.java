package com.team_abnormals.environmental.common.slabfish;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.team_abnormals.environmental.common.entity.util.SlabfishRarity;
import com.team_abnormals.environmental.common.network.message.SSyncSlabfishTypeMessage;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishConditionType;
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

/**
 * <p>Manages all types of slabfish through data packs.</p>
 *
 * @author Ocelot
 */
public class SlabfishManager extends JsonReloadListener
{
    public static final ResourceLocation DEFAULT = new ResourceLocation(Environmental.MODID, "");

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(SlabfishType.class, new SlabfishType.Deserializer()).registerTypeAdapter(SlabfishCondition.class, new SlabfishCondition.Deserializer()).create();
    private static final SlabfishType DEFAULT_SLABFISH_TYPE = new SlabfishType(SlabfishRarity.COMMON, new SlabfishCondition[0]);
    private static SlabfishManager instance;

    private final Map<ResourceLocation, SlabfishType> slabfishTypes;

    public SlabfishManager()
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
                SlabfishType slabfishType = GSON.fromJson(json, SlabfishType.class);
                LOGGER.debug("Registered Slabfish Type: " + location);
                LOGGER.debug("Rarity: " + slabfishType.getRarity());
                parsed.put(location, slabfishType);
            }
            catch (Exception e)
            {
                LOGGER.error("Parsing error loading custom slabfish " + location, e);
            }
        }));
        parsed.put(DEFAULT, DEFAULT_SLABFISH_TYPE);

        LOGGER.info("Loaded " + parsed.size() + " Slabfish Types");

        this.slabfishTypes.clear();
        this.slabfishTypes.putAll(parsed);

        if (EffectiveSide.get().isServer())
            Environmental.PLAY.send(PacketDistributor.ALL.noArg(), new SSyncSlabfishTypeMessage());
    }

    /**
     * Checks the slabfish types for a slabfish of the specified name.
     *
     * @param registryName The name of the slabfish to search for
     * @return The slabfish type by that name or null for no slabfish under that name
     */
    @Nullable
    public static SlabfishType getSlabfish(ResourceLocation registryName)
    {
        return instance == null ? null : instance.slabfishTypes.get(registryName);
    }

    /**
     * Fetches a random slabfish type by the specified {@link Predicate}.
     *
     * @param predicate The predicate to use when searching for a slabfish type
     * @param random    The random to use for the index
     * @return A random slabfish type by that rarity or {@link #DEFAULT_SLABFISH_TYPE} if there were no results
     */
    public static SlabfishType getRandom(Predicate<SlabfishType> predicate, Random random)
    {
        SlabfishType[] slabfishTypes = getAllSlabfish();
        if (slabfishTypes.length == 0)
            return DEFAULT_SLABFISH_TYPE;
        return Arrays.stream(slabfishTypes).filter(predicate).skip(random.nextInt(slabfishTypes.length)).findFirst().orElse(DEFAULT_SLABFISH_TYPE);
    }

    /**
     * @return All registered slabfish types
     */
    public static SlabfishType[] getAllSlabfish()
    {
        return instance == null ? new SlabfishType[0] : instance.slabfishTypes.values().toArray(new SlabfishType[0]);
    }
}
