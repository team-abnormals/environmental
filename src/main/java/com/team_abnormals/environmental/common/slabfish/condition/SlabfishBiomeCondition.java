package com.team_abnormals.environmental.common.slabfish.condition;

import com.google.gson.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
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

    private SlabfishBiomeCondition(@Nullable Biome.Category biomeCategory, @Nullable Biome.TempCategory tempCategory)
    {
        List<ResourceLocation> validBiomes = new ArrayList<>();
        for (Biome biome : ForgeRegistries.BIOMES)
            if ((tempCategory == null || biome.getTempCategory() == tempCategory) && (biomeCategory == null || biome.getCategory() == biomeCategory))
                validBiomes.add(biome.getRegistryName());
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
    private static Biome.Category deserializeBiomeType(JsonElement element) throws JsonParseException
    {
        if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString())
            throw new JsonSyntaxException("Biome type expected to be a string");
        String name = element.getAsString();
        for (Biome.Category biomeCategory : Biome.Category.values())
            if (biomeCategory.name().equalsIgnoreCase(name))
                return biomeCategory;
        return null;
    }

    @Nullable
    private static Biome.TempCategory deserializeBiomeTempCategory(JsonElement element) throws JsonParseException
    {
        if (!element.isJsonPrimitive() || !element.getAsJsonPrimitive().isString())
            throw new JsonSyntaxException("Biome temperature category expected to be a string");
        String name = element.getAsString();
        for (Biome.TempCategory tempCategory : Biome.TempCategory.values())
            if (tempCategory.name().equalsIgnoreCase(name))
                return tempCategory;
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
            return new SlabfishBiomeCondition(context.deserialize(json.get("biome"), ResourceLocation.class));
        }

        if (json.has("category") || json.has("tempCategory"))
        {
            Biome.Category biomeCategory = deserializeBiomeType(json.get("category"));
            Biome.TempCategory tempCategory = json.has("tempCategory") ? deserializeBiomeTempCategory(json.get("tempCategory")) : null;

            return new SlabfishBiomeCondition(biomeCategory, tempCategory);
        }

        throw new JsonSyntaxException("Either 'biome' or 'category' and 'tempCategory' must be present.");
    }
}
