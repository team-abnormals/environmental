package com.teamabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.Nullable;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is inside of the specified block or block tag.</p>
 *
 * @author Ocelot
 */
public class SlabfishInFluidCondition implements SlabfishCondition {
	private final TagKey<Fluid> tag;

	private SlabfishInFluidCondition(@Nullable TagKey<Fluid> tag) {
		this.tag = tag;
	}

	/**
	 * Creates a new {@link SlabfishInFluidCondition} from the specified json.
	 *
	 * @param json    The json to deserialize
	 * @param context The context of the json deserialization
	 * @return A new slabfish condition from that json
	 */
	public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
		if (!json.has("tag"))
			throw new JsonSyntaxException("'tag' must be present.");
		return new SlabfishInFluidCondition(TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(json.get("tag").getAsString())));
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.isInFluid(this.tag);
	}
}
