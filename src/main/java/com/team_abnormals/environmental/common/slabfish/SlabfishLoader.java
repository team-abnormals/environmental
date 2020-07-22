package com.team_abnormals.environmental.common.slabfish;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.team_abnormals.environmental.common.network.message.SSyncSlabfishTypeMessage;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.team_abnormals.environmental.core.Environmental;
import net.minecraft.client.resources.JsonReloadListener;
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
public class SlabfishLoader extends JsonReloadListener implements SlabfishManager
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer()).registerTypeAdapter(ITextComponent.class, new ITextComponent.Serializer()).registerTypeAdapter(SlabfishType.class, new SlabfishType.Deserializer()).registerTypeAdapter(SlabfishCondition.class, new SlabfishCondition.Deserializer()).create();
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
                parsed.put(location, GSON.fromJson(json, SlabfishType.class).setRegistryName(location));
            }
            catch (Exception e)
            {
                LOGGER.error("Parsing error loading custom slabfish " + location, e);
            }
        }));

        LOGGER.info("Loaded " + parsed.size() + " Slabfish Types");

        this.slabfishTypes.clear();
        this.slabfishTypes.put(DEFAULT_SLABFISH.getRegistryName(), DEFAULT_SLABFISH);
        this.slabfishTypes.putAll(parsed);

        if (EffectiveSide.get().isServer())
            Environmental.PLAY.send(PacketDistributor.ALL.noArg(), new SSyncSlabfishTypeMessage());
    }

    @Override
    public SlabfishType get(ResourceLocation registryName)
    {
        return this.slabfishTypes.getOrDefault(registryName, DEFAULT_SLABFISH);
    }

    @Override
    public SlabfishType get(Predicate<SlabfishType> predicate, SlabfishConditionContext context)
    {
        return this.slabfishTypes.values().stream().filter(slabfishType -> predicate.test(slabfishType) && slabfishType.test(context)).max(Comparator.comparingInt(SlabfishType::getPriority)).orElse(DEFAULT_SLABFISH);
    }

    @Override
    public SlabfishType getRandom(Predicate<SlabfishType> predicate, Random random)
    {
        if (this.slabfishTypes.isEmpty())
            return DEFAULT_SLABFISH;
        SlabfishType[] slabfishTypes = this.slabfishTypes.values().stream().filter(predicate).toArray(SlabfishType[]::new);
        return slabfishTypes.length == 0 ? DEFAULT_SLABFISH : slabfishTypes[random.nextInt(slabfishTypes.length)];
    }

    @Override
    public SlabfishType[] getAllSlabfish()
    {
        return this.slabfishTypes.values().toArray(new SlabfishType[0]);
    }
}
