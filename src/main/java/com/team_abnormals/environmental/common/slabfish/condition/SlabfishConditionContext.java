package com.team_abnormals.environmental.common.slabfish.condition;

import com.team_abnormals.environmental.common.entity.SlabfishEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;

/**
 * <p>A context used for determining what kinds of slabfish can be spawned.</p>
 *
 * @author Ocelot
 */
public class SlabfishConditionContext
{
    private final String name;
    private final Biome biome;

    public SlabfishConditionContext(SlabfishEntity slabfish)
    {
        IWorldReader world = slabfish.getEntityWorld();
        BlockPos pos = new BlockPos(slabfish.getPositionVec());
        this.name = slabfish.getDisplayName().getString().trim();
        this.biome = world.getBiome(pos);
    }

    /**
     * @return The name of the slabfish
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The biome the slabfish is in
     */
    public Biome getBiome()
    {
        return biome;
    }
}
