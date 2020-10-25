package com.minecraftabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.minecraftabnormals.environmental.common.slabfish.SlabfishType;

import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish parents are the same as the ones specified.</p>
 *
 * @author Ocelot
 */
public class SlabfishParentCondition implements SlabfishCondition {
    private final ResourceLocation parentA;
    private final ResourceLocation parentB;

    private SlabfishParentCondition(ResourceLocation parentA, @Nullable ResourceLocation parentB) {
        this.parentA = parentA;
        this.parentB = parentB;
    }

    /**
     * Creates a new {@link SlabfishParentCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
        if ((json.has("parentA") || json.has("parentB")) && json.has("parent"))
            throw new JsonSyntaxException("Either 'parentA' and 'parentB' or 'parent' can be present.");
        if (!json.has("parentA") && !json.has("parentB") && !json.has("parent"))
            throw new JsonSyntaxException("Either 'parentA' and 'parentB' or 'parent' must be present.");
        return json.has("parent") ? new SlabfishParentCondition(context.deserialize(json.get("parent"), ResourceLocation.class), null) : new SlabfishParentCondition(context.deserialize(json.get("parentA"), ResourceLocation.class), context.deserialize(json.get("parentB"), ResourceLocation.class));
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        Pair<SlabfishType, SlabfishType> parentTypes = context.getParentTypes();
        if (parentTypes == null)
            return false;

        if (!this.parentA.equals(parentTypes.getLeft().getRegistryName()) && !this.parentA.equals(parentTypes.getRight().getRegistryName()))
            return false;

        if (this.parentB == null)
            return true;

        return (this.parentA.equals(parentTypes.getLeft().getRegistryName()) && this.parentB.equals(parentTypes.getRight().getRegistryName())) || (this.parentA.equals(parentTypes.getRight().getRegistryName()) && this.parentB.equals(parentTypes.getLeft().getRegistryName()));
    }
}
