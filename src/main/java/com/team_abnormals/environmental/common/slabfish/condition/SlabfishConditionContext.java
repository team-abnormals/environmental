package com.team_abnormals.environmental.common.slabfish.condition;

import com.team_abnormals.environmental.common.entity.SlabfishEntity;

/**
 * <p>A context used for determining what kinds of slabfish can be spawned.</p>
 *
 * @author Ocelot
 */
public class SlabfishConditionContext
{
    private final String name;

    public SlabfishConditionContext(SlabfishEntity slabfish)
    {
        this.name = slabfish.getDisplayName().getString().trim();
    }

    /**
     * @return The name of the slabfish
     */
    public String getName()
    {
        return name;
    }
}
