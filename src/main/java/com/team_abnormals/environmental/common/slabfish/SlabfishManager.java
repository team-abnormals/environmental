package com.team_abnormals.environmental.common.slabfish;

import com.team_abnormals.environmental.common.entity.util.SlabfishRarity;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishCondition;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import com.team_abnormals.environmental.core.Environmental;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fml.LogicalSide;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Predicate;

/**
 * <p>Manages all slabfish for both sides.</p>
 *
 * @author Ocelot
 */
public interface SlabfishManager
{
    /**
     * The default slabfish that exists if there are no other slabfish types to choose from.
     */
    SlabfishType DEFAULT_SLABFISH = new SlabfishType(SlabfishRarity.COMMON, -1, new SlabfishCondition[0]).setRegistryName(new ResourceLocation(Environmental.MODID, "default"));

    /**
     * Checks the slabfish types for a slabfish of the specified name.
     *
     * @param registryName The name of the slabfish to search for
     * @return The slabfish type by that name or null for no slabfish under that name
     */
    @Nullable
    SlabfishType get(ResourceLocation registryName);

    /**
     * Checks through all slabfish types for a slabfish conditions that succeed in the current context.
     *
     * @param context The context of the slabfish
     * @return The slabfish that that was selected to be the best fit for the context
     */
    SlabfishType get(SlabfishConditionContext context);

    /**
     * Fetches a random slabfish type by the specified {@link Predicate}.
     *
     * @param predicate The predicate to use when searching for a slabfish type
     * @param random    The random to use for the index
     * @return A random slabfish type by that rarity or {@link #DEFAULT_SLABFISH} if there were no results
     */
    SlabfishType getRandom(Predicate<SlabfishType> predicate, Random random);

    /**
     * @return All registered slabfish types
     */
    SlabfishType[] getAllSlabfish();

    /**
     * Fetches the slabfish manager for the specified side.
     *
     * @param side The logical side to get the slabfish manager for
     * @return The slabfish manager for that side
     */
    static SlabfishManager get(LogicalSide side)
    {
        return side.isClient() ? ClientSlabfishManager.INSTANCE : SlabfishLoader.instance;
    }

    /**
     * Same as {@link #get(LogicalSide)} but provides ease of access by using a world instead of {@link LogicalSide}.
     *
     * @param world The world to get the logical side from
     * @return The slabfish manager for that side
     */
    static SlabfishManager get(IWorldReader world)
    {
        return get(world.isRemote() ? LogicalSide.CLIENT : LogicalSide.SERVER);
    }
}
