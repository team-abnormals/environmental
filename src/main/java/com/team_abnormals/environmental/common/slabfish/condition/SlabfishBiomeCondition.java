package com.team_abnormals.environmental.common.slabfish.condition;

import com.google.common.collect.ImmutableList;
import com.google.gson.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in any of the specified biomes if they are registered.</p>
 *
 * @author Ocelot
 */
public class SlabfishBiomeCondition implements SlabfishCondition
{
    private final ResourceLocation[] biomes;

    public SlabfishBiomeCondition()
    {
        this.biomes = new ResourceLocation[0];
    }

    private SlabfishBiomeCondition(ResourceLocation biomeRegistryName)
    {
        if (ForgeRegistries.BIOMES.containsKey(biomeRegistryName))
        {
            this.biomes = new ResourceLocation[]{biomeRegistryName};
        }
        else
        {
            this.biomes = new ResourceLocation[0];
        }
    }

    private SlabfishBiomeCondition(BiomeManager.BiomeType... biomeTypes)
    {
        List<ResourceLocation> validBiomes = new ArrayList<>();
        for (BiomeManager.BiomeType biomeType : biomeTypes)
        {
            ImmutableList<BiomeManager.BiomeEntry> biomes = BiomeManager.getBiomes(biomeType);
            if (biomes != null)
                for (BiomeManager.BiomeEntry biomeEntry : biomes)
                    validBiomes.add(biomeEntry.biome.getRegistryName());
        }
        this.biomes = validBiomes.toArray(new ResourceLocation[0]);
    }

    @Override
    public SlabfishConditionType getType()
    {
        return SlabfishConditionType.BIOME;
    }

    @Override
    public boolean test(SlabfishConditionContext context)
    {
        for (ResourceLocation biomeRegistryName : this.biomes)
            if (biomeRegistryName.equals(context.getBiome().getRegistryName()))
                return true;
        return false;
    }

    @Nullable
    private static BiomeManager.BiomeType deserializeBiomeType(JsonElement element) throws JsonParseException
    {
        if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString())
            throw new JsonSyntaxException("Biome type expected to be a string");
        String name = element.getAsString();
        for (BiomeManager.BiomeType biomeType : BiomeManager.BiomeType.values())
            if (biomeType.name().equalsIgnoreCase(name))
                return biomeType;
        return null;
    }

    /**
     * Creates a new {@link SlabfishBiomeCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context)
    {
        if (json.has("biome") && json.has("category"))
            throw new JsonSyntaxException("Either 'biome' or 'category' can be present.");

        if (json.has("biome"))
        {
            return new SlabfishBiomeCondition((ResourceLocation) context.deserialize(json.get("biome"), ResourceLocation.class));
        }

        if (json.has("category"))
        {
            BiomeManager.BiomeType biomeType = deserializeBiomeType(json.get("category"));
            if (biomeType == null)
            {
                return new SlabfishBiomeCondition();
            }
            else
            {
                return new SlabfishBiomeCondition(biomeType);
            }
        }

        throw new JsonSyntaxException("Either 'biome' or 'category' must be present.");
    }
}
