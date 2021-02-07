package com.minecraftabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if any of the child conditions are true.</p>
 *
 * @author Ocelot
 */
public class SlabfishOrCondition implements SlabfishCondition {
	private final SlabfishCondition[] conditions;

	public SlabfishOrCondition(SlabfishCondition[] conditions) {
		this.conditions = conditions;
	}

	/**
	 * Creates a new {@link SlabfishOrCondition} from the specified json.
	 *
	 * @param json    The json to deserialize
	 * @param context The context of the json deserialization
	 * @return A new slabfish condition from that json
	 */
	public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
		if (!json.has("conditions"))
			throw new JsonSyntaxException("'conditions' must be present.");
		return new SlabfishOrCondition(context.deserialize(json.get("conditions"), SlabfishCondition[].class));
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		for (SlabfishCondition condition : this.conditions)
			if (condition.test(context))
				return true;
		return false;
	}
}
