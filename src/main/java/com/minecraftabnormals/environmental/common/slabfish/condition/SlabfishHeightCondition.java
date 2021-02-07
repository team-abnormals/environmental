package com.minecraftabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish within the height range specified.</p>
 *
 * @author Ocelot
 */
public class SlabfishHeightCondition implements SlabfishCondition {
	private final int minHeight;
	private final int maxHeight;

	private SlabfishHeightCondition(int minHeight, int maxHeight) {
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
	}

	/**
	 * Creates a new {@link SlabfishHeightCondition} from the specified json.
	 *
	 * @param json    The json to deserialize
	 * @param context The context of the json deserialization
	 * @return A new slabfish condition from that json
	 */
	public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
		if ((json.has("min") || json.has("max")) && json.has("value"))
			throw new JsonSyntaxException("Either 'min' and 'max' or 'value' can be present.");
		if (!json.has("min") && !json.has("max") && !json.has("value"))
			throw new JsonSyntaxException("Either 'min' and 'max' or 'value' must be present.");
		return json.has("value") ? new SlabfishHeightCondition(json.get("value").getAsInt(), json.get("value").getAsInt()) : new SlabfishHeightCondition(json.has("min") ? json.get("min").getAsInt() : Integer.MIN_VALUE, json.has("max") ? json.get("max").getAsInt() : Integer.MAX_VALUE);
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.getPos().getY() >= this.minHeight && context.getPos().getY() <= this.maxHeight;
	}
}
