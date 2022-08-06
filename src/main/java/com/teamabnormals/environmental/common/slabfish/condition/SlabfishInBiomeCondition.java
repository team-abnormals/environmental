package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is in any of the specified biomes if they are registered.</p>
 *
 * @author Ocelot
 */
public class SlabfishInBiomeCondition implements SlabfishCondition {

	public static final Codec<SlabfishInBiomeCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.either(ForgeRegistries.BIOMES.getCodec(), TagKey.hashedCodec(ForgeRegistries.BIOMES.getRegistryKey())).fieldOf("biome").forGetter(c -> {
			if (c.getBiome() != null) return Either.left(c.getBiome());
			else return Either.right(c.getTag());
		})
	).apply(instance, (either) -> new SlabfishInBiomeCondition(either.left().orElse(null), either.right().orElse(null))));


	private final Biome biome;
	private final TagKey<Biome> tag;

	private SlabfishInBiomeCondition(@Nullable Biome biome, @Nullable TagKey<Biome> tag) {
		this.biome = biome;
		this.tag = tag;
	}

	public SlabfishInBiomeCondition(TagKey<Biome> tag) {
		this(null, tag);
	}

	public SlabfishInBiomeCondition(Biome biome) {
		this(biome, null);
	}

	public SlabfishInBiomeCondition(ResourceKey<Biome> biome) {
		this(ForgeRegistries.BIOMES.getValue(biome.location()), null);
	}

	@Nullable
	public Biome getBiome() {
		return biome;
	}

	@Nullable
	public TagKey<Biome> getTag() {
		return tag;
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return this.biome != null ? context.getBiome().is(Objects.requireNonNull(this.biome.getRegistryName())) : this.tag != null && context.getBiome().is(this.tag);
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.IN_BIOME.get();
	}
}
