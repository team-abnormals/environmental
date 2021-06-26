package com.minecraftabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is inside of the specified block or block tag.</p>
 *
 * @author Ocelot
 */
public class SlabfishInFluidCondition implements SlabfishCondition {
	private final ITag<Fluid> tag;

	private SlabfishInFluidCondition(@Nullable ITag<Fluid> tag) {
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
		return new SlabfishInFluidCondition(TagCollectionManager.getInstance().getFluids().getTag(new ResourceLocation(json.get("tag").getAsString())));
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return context.isInFluid(this.tag);
	}
}
