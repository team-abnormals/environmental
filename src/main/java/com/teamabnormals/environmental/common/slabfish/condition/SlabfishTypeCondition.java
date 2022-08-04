package com.teamabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.resources.ResourceLocation;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish type is the same as any of the specified.</p>
 *
 * @author Ocelot
 */
public class SlabfishTypeCondition implements SlabfishCondition {
	private final ResourceLocation[] slabfishTypes;

	private SlabfishTypeCondition(ResourceLocation[] slabfishTypes) {
		this.slabfishTypes = slabfishTypes;
	}

	/**
	 * Creates a new {@link SlabfishTypeCondition} from the specified json.
	 *
	 * @param json    The json to deserialize
	 * @param context The context of the json deserialization
	 * @return A new slabfish condition from that json
	 */
	public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
		if (!json.has("slabfishTypes"))
			throw new JsonSyntaxException("'slabfishTypes' must be present.");
		return new SlabfishTypeCondition(context.deserialize(json.get("slabfishTypes"), ResourceLocation[].class));
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		for (ResourceLocation slabfishType : this.slabfishTypes)
			if (slabfishType.equals(context.getSlabfishType()))
				return true;
		return false;
	}
}
