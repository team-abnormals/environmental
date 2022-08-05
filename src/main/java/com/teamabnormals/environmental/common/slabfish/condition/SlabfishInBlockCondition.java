package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is inside of the specified block or block tag.</p>
 *
 * @author Ocelot
 */
public class SlabfishInBlockCondition implements SlabfishCondition {

    public static final Codec<SlabfishInBlockCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.either(ForgeRegistries.BLOCKS.getCodec(), TagKey.hashedCodec(ForgeRegistries.BLOCKS.getRegistryKey())).fieldOf("block").forGetter(c -> {
            if (c.getBlock() != null) return Either.left(c.getBlock());
            else return Either.right(c.getTag());
        })
    ).apply(instance, (either) -> new SlabfishInBlockCondition(either.left().orElse(null), either.right().orElse(null))));

    private final Block block;
    private final TagKey<Block> tag;

    private SlabfishInBlockCondition(@Nullable Block block, @Nullable TagKey<Block> tag) {
        this.block = block;
        this.tag = tag;
    }

    @Nullable
    public Block getBlock() {
        return block;
    }

    @Nullable
    public TagKey<Block> getTag() {
        return tag;
    }

    @Override
    public boolean test(SlabfishConditionContext context) {
        return this.block != null ? context.isInBlock(this.block) : context.isInBlock(this.tag);
    }

    @Override
    public SlabfishConditionType getType() {
        return EnvironmentalSlabfishConditions.IN_BLOCK.get();
    }
}
