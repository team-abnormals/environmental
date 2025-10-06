package com.teamabnormals.environmental.common.slabfish.condition;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamabnormals.environmental.common.slabfish.SlabfishConditionType;
import com.teamabnormals.environmental.common.slabfish.XorMapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalSlabfishConditions;
import net.minecraft.world.level.LightLayer;

import javax.annotation.Nullable;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

/**
 * <p>A {@link SlabfishCondition} that returns <code>true</code> if the slabfish is within the specified light values.</p>
 *
 * @author Ocelot
 */
public class SlabfishLightCondition implements SlabfishCondition {

	private static final Codec<LightLayer> LIGHT_LAYER_CODEC = Codec.STRING.comapFlatMap(name -> {
		for (LightLayer lightType : LightLayer.values())
			if (lightType.name().equalsIgnoreCase(name))
				return DataResult.success(lightType);
		return DataResult.error(() -> "Invalid light type: " + name);
	}, lightLayer -> lightLayer.name().toLowerCase(Locale.ROOT));

	private static final MapCodec<SlabfishLightCondition> VALUE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.INT.fieldOf("value").forGetter(SlabfishLightCondition::getMax),
			SlabfishLightCondition.LIGHT_LAYER_CODEC.optionalFieldOf("light_type").forGetter(c -> Optional.ofNullable(c.getLightLayer()))
	).apply(instance, (value, light) -> new SlabfishLightCondition(value, value, light.orElse(null))));

	private static final MapCodec<SlabfishLightCondition> MIN_MAX_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			Codec.INT.fieldOf("min").forGetter(SlabfishLightCondition::getMin),
			Codec.INT.fieldOf("max").forGetter(SlabfishLightCondition::getMax),
			SlabfishLightCondition.LIGHT_LAYER_CODEC.optionalFieldOf("light_type").forGetter(c -> Optional.ofNullable(c.getLightLayer()))
	).apply(instance, (min, max, light) -> new SlabfishLightCondition(min, max, light.orElse(null))));

	public static final MapCodec<SlabfishLightCondition> CODEC = XorMapCodec.xor(SlabfishLightCondition.VALUE_CODEC, SlabfishLightCondition.MIN_MAX_CODEC).xmap(
			c -> c.left().isPresent() ? c.left().get() : c.right().orElse(null),
			c -> c.getMin() == c.getMax() ? Either.left(c) : Either.right(c)
	);

	private final int min;
	private final int max;
	private final LightLayer lightLayer;
	private final Function<SlabfishConditionContext, Integer> lightGetter;

	public SlabfishLightCondition(int min, int max, @Nullable LightLayer lightLayer) {
		this.min = min;
		this.max = max;
		this.lightLayer = lightLayer;
		this.lightGetter = context -> lightLayer == null ? context.getLight() : context.getLightFor(lightLayer);
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	@Nullable
	public LightLayer getLightLayer() {
		return lightLayer;
	}

	@Override
	public boolean test(SlabfishConditionContext context) {
		return this.lightGetter.apply(context) >= this.min && this.lightGetter.apply(context) <= this.max;
	}

	@Override
	public SlabfishConditionType getType() {
		return EnvironmentalSlabfishConditions.LIGHT_LEVEL.get();
	}
}