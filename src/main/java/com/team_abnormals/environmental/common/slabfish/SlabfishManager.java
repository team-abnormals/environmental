package com.team_abnormals.environmental.common.slabfish;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Manages all types of slabfish through data packs.</p>
 *
 * @author Ocelot
 */
public class SlabfishManager extends JsonReloadListener
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().create();
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
//                JsonObject jsonobject = JSONUtils.getJsonObject(p_240923_3_, "advancement");
//                Advancement.Builder advancement$builder = Advancement.Builder.func_241043_a_(jsonobject, new ConditionArrayParser(p_240923_2_, this.field_240922_d_));
//                if (advancement$builder == null) {
//                    LOGGER.debug("Skipping loading advancement {} as it's conditions were not met", p_240923_2_);
//                    return;
//                }
//                map.put(p_240923_2_, advancement$builder);
            }
            catch (Exception e)
            {
                LOGGER.error("Parsing error loading custom slabfish " + location, e);
            }
        }));

        LOGGER.info("Loaded " + parsed.size() + " Slabfish Types");

        this.slabfishTypes.clear();
        this.slabfishTypes.putAll(parsed);
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
     * @return All registered slabfish types
     */
    public static SlabfishType[] getAllSlabfish()
    {
        return instance == null ? new SlabfishType[0] : instance.slabfishTypes.values().toArray(new SlabfishType[0]);
    }
}
