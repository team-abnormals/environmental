package com.team_abnormals.environmental.common.slabfish.condition;

import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import com.team_abnormals.environmental.common.slabfish.SlabfishManager;
import com.team_abnormals.environmental.common.slabfish.SlabfishType;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>A context used for determining what kinds of slabfish can be spawned.</p>
 *
 * @author Ocelot
 */
public class SlabfishConditionContext
{
    private final boolean struckByLightning;
    private final LazyValue<String> name;
    private final LazyValue<BlockPos> pos;
    private final LazyValue<Biome> biome;
    private final LazyValue<Boolean> raid;
    private final LazyValue<Integer> light;
    private final Map<LightType, LazyValue<Integer>> lightTypes;
    private final LazyValue<ResourceLocation> dimension;
    private final Pair<SlabfishType, SlabfishType> parents;

    public SlabfishConditionContext(SlabfishEntity slabfish, boolean struckByLightning, @Nullable SlabfishEntity parent1, @Nullable SlabfishEntity parent2)
    {
        ServerWorld world = (ServerWorld) slabfish.getEntityWorld();
        this.struckByLightning = struckByLightning;
        this.name = new LazyValue<>(() -> slabfish.getDisplayName().getString().trim());
        this.pos = new LazyValue<>(() -> new BlockPos(slabfish.getPositionVec()));
        this.biome = new LazyValue<>(() -> world.getBiome(this.pos.getValue()));
        this.raid = new LazyValue<>(() -> world.findRaid(this.pos.getValue()) != null);
        this.light = new LazyValue<>(() -> world.getLight(this.pos.getValue()));
        this.lightTypes = new HashMap<>();
        for (LightType lightType : LightType.values())
            this.lightTypes.put(lightType, new LazyValue<>(() -> world.getLightFor(lightType, this.pos.getValue())));
        this.dimension = new LazyValue<>(() -> world.func_234923_W_().func_240901_a_());
        this.parents = parent1 != null && parent2 != null ? new ImmutablePair<>(SlabfishManager.DEFAULT_SLABFISH, SlabfishManager.DEFAULT_SLABFISH) : null;// TODO get type from parents
    }

    /**
     * @return Whether or not the slabfish was struck by lightning
     */
    public boolean isStruckByLightning()
    {
        return struckByLightning;
    }

    /**
     * @return The name of the slabfish
     */
    public String getName()
    {
        return this.name.getValue();
    }

    /**
     * @return The position of the slabfish
     */
    public BlockPos getPos()
    {
        return this.pos.getValue();
    }

    /**
     * @return The biome the slabfish is in
     */
    public Biome getBiome()
    {
        return this.biome.getValue();
    }

    /**
     * @return Whether or not a raid is currently ongoing
     */
    public boolean isInRaid()
    {
        return this.raid.getValue();
    }

    /**
     * @return The light value at the slabfish position
     */
    public int getLight()
    {
        return this.light.getValue();
    }

    /**
     * Fetches light for the specified type of light
     *
     * @param lightType The type of light to get
     * @return The sky light value at the slabfish position
     */
    public int getLightFor(LightType lightType)
    {
        return this.lightTypes.get(lightType).getValue();
    }

    /**
     * @return The dimension the slabfish is in
     */
    public ResourceLocation getDimension()
    {
        return this.dimension.getValue();
    }

    /**
     * @return The types of slabfish the parents were or null if there are no parents
     */
    @Nullable
    public Pair<SlabfishType, SlabfishType> getParentTypes()
    {
        return this.parents;
    }
}
