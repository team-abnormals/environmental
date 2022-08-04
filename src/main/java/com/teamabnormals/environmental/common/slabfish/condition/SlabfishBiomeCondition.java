package com.teamabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in any of the specified biomes if they are registered.</p>
 *
 * @author Ocelot
 */
public class SlabfishBiomeCondition implements SlabfishCondition {
	private final Biome biome;
	private final TagKey<Biome> tag;

	private SlabfishBiomeCondition(@Nullable Biome biome, @Nullable TagKey<Biome> tag) {
		this.biome = biome;
		this.tag = tag;
	}

	/**
	 * Creates a new {@link SlabfishBiomeCondition} from the specified json.
	 *
	 * @param json    The json to deserialize
	 * @param context The context of the json deserialization
	 * @return A new slabfish condition from that json
	 */
	public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
		if (json.has("biome") && json.has("tag"))
			throw new JsonSyntaxException("Either 'biome' or 'tag' can be present.");
		if (!json.has("biome") && !json.has("tag"))
			throw new JsonSyntaxException("Either 'biome' or 'tag' must be present.");
		return new SlabfishBiomeCondition(json.has("tag") ? null : ForgeRegistries.BIOMES.getValue(new ResourceLocation(json.get("biome").getAsString())), json.has("tag") ? TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(json.get("tag").getAsString())) : null);
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return this.biome != null ? context.getBiome().is(this.biome.getRegistryName()) : this.tag != null && context.getBiome().is(this.tag);
	}
}
