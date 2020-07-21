package com.team_abnormals.environmental.common.slabfish;

import com.team_abnormals.environmental.common.network.message.SSyncSlabfishTypeMessage;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>A client wrapper for {@link SlabfishManager} that reflects what the server has.</p>
 *
 * @author Ocelot
 */
public final class ClientSlabfishManager implements SlabfishManager
{
    static final ClientSlabfishManager INSTANCE = new ClientSlabfishManager();

    private final Map<ResourceLocation, SlabfishType> slabfishTypes;

    private ClientSlabfishManager()
    {
        this.slabfishTypes = new HashMap<>();
    }

    @Override
    public SlabfishType get(ResourceLocation registryName)
    {
        return this.slabfishTypes.getOrDefault(registryName, DEFAULT_SLABFISH);
    }

    @Override
    public SlabfishType get(Predicate<SlabfishType> predicate, SlabfishConditionContext context)
    {
        throw new UnsupportedOperationException("Client does not have access to select random slabfish based on conditions");
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

    /**
     * Receives the slabfish types from the server.
     *
     * @param msg The message containing the new types
     */
    public static void receive(SSyncSlabfishTypeMessage msg)
    {
        INSTANCE.slabfishTypes.clear();
        INSTANCE.slabfishTypes.putAll(Arrays.stream(msg.getSlabfishTypes()).collect(Collectors.toMap(SlabfishType::getRegistryName, slabfishType -> slabfishType)));
    }
}