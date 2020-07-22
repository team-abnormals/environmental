package com.team_abnormals.environmental.common.slabfish;

import com.team_abnormals.environmental.common.network.message.SSyncBackpackTypeMessage;
import com.team_abnormals.environmental.common.network.message.SSyncSlabfishTypeMessage;
import com.team_abnormals.environmental.common.network.message.SSyncSweaterTypeMessage;
import com.team_abnormals.environmental.common.slabfish.condition.SlabfishConditionContext;
import net.minecraft.util.ResourceLocation;

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
    private final Map<ResourceLocation, SweaterType> sweaterTypes;
    private final Map<ResourceLocation, BackpackType> backpackTypes;

    private ClientSlabfishManager()
    {
        this.slabfishTypes = new HashMap<>();
        this.sweaterTypes = new HashMap<>();
        this.backpackTypes = new HashMap<>();
    }

    @Override
    public SlabfishType getSlabfishType(ResourceLocation registryName)
    {
        return this.slabfishTypes.getOrDefault(registryName, DEFAULT_SLABFISH);
    }

    @Override
    public SweaterType getSweaterType(ResourceLocation registryName)
    {
        return this.sweaterTypes.getOrDefault(registryName, EMPTY_SWEATER);
    }

    @Override
    public BackpackType getBackpackType(ResourceLocation registryName)
    {
        return this.backpackTypes.getOrDefault(registryName, EMPTY_BACKPACK);
    }

    @Override
    public SlabfishType getSlabfishType(Predicate<SlabfishType> predicate, SlabfishConditionContext context)
    {
        throw new UnsupportedOperationException("Client does not have access to select random slabfish");
    }

    @Override
    public SlabfishType getRandomSlabfishType(Predicate<SlabfishType> predicate, Random random)
    {
        throw new UnsupportedOperationException("Client does not have access to select random slabfish");
    }

    @Override
    public SlabfishType[] getAllSlabfishTypes()
    {
        return this.slabfishTypes.values().toArray(new SlabfishType[0]);
    }

    @Override
    public SweaterType[] getAllSweaterTypes()
    {
        return this.sweaterTypes.values().toArray(new SweaterType[0]);
    }

    @Override
    public BackpackType[] getAllBackpackTypes()
    {
        return this.backpackTypes.values().toArray(new BackpackType[0]);
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

    /**
     * Receives the sweater types from the server.
     *
     * @param msg The message containing the new types
     */
    public static void receive(SSyncSweaterTypeMessage msg)
    {
        INSTANCE.sweaterTypes.clear();
        INSTANCE.sweaterTypes.putAll(Arrays.stream(msg.getSweaterTypes()).collect(Collectors.toMap(SweaterType::getRegistryName, sweaterType -> sweaterType)));
        System.out.println("Received sweater types: " + INSTANCE.sweaterTypes);
    }

    /**
     * Receives the backpack types from the server.
     *
     * @param msg The message containing the new types
     */
    public static void receive(SSyncBackpackTypeMessage msg)
    {
        INSTANCE.backpackTypes.clear();
        INSTANCE.backpackTypes.putAll(Arrays.stream(msg.getBackpackTypes()).collect(Collectors.toMap(BackpackType::getRegistryName, backpackType -> backpackType)));
        System.out.println("Received backpack types: " + INSTANCE.backpackTypes);
    }
}
