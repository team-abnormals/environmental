package com.minecraftabnormals.environmental.common.slabfish.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is inside of the specified block or block tag.</p>
 *
 * @author Ocelot
 */
public class SlabfishInBlockCondition implements SlabfishCondition {
    private final Block block;
    private final ITag<Block> tag;

    private SlabfishInBlockCondition(@Nullable Block block, @Nullable ITag<Block> tag) {
        this.block = block;
        this.tag = tag;
    }

    /**
     * Creates a new {@link SlabfishInBlockCondition} from the specified json.
     *
     * @param json    The json to deserialize
     * @param context The context of the json deserialization
     * @return A new slabfish condition from that json
     */
    public static SlabfishCondition deserialize(JsonObject json, JsonDeserializationContext context) {
        if (json.has("block") && json.has("tag"))
            throw new JsonSyntaxException("Either 'block' or 'tag' can be present.");
        if (!json.has("block") && !json.has("tag"))
            throw new JsonSyntaxException("Either 'block' or 'tag' must be present.");
        return new SlabfishInBlockCondition(json.has("tag") ? null : ForgeRegistries.BLOCKS.getValue(new ResourceLocation(json.get("block").getAsString())), json.has("tag") ? TagCollectionManager.getManager().getBlockTags().get(new ResourceLocation(json.get("tag").getAsString())) : null);
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return this.block != null ? context.isInBlock(this.block) : context.isInBlock(this.tag);
    }
}
