package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is inside of the specified block or block tag.</p>
 *
 * @author Ocelot
 */
public record SlabfishInBlockCondition(HolderSet<Block> blocks) implements SlabfishCondition {
	public static final MapCodec<SlabfishInBlockCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("blocks").forGetter(entry -> entry.blocks)
	).apply(instance, SlabfishInBlockCondition::new));

	@Override
	public boolean test(SlabfishConditionContext context) {
		return this.blocks.contains(context.getBlock());
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.IN_BLOCK.get();
	}
}
