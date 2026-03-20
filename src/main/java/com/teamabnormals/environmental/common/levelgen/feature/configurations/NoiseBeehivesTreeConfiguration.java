package com.teamabnormals.environmental.common.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class NoiseBeehivesTreeConfiguration extends TreeConfiguration {
	public static final Codec<TreeConfiguration> CODEC = RecordCodecBuilder.create(builder -> {
		return builder.group(
				TreeConfiguration.CODEC.fieldOf("tree_configuration").forGetter(config -> config),
				Codec.BOOL.fieldOf("use_noise_beehives").forGetter(NoiseBeehivesTreeConfiguration::useNoiseBeehives)
		).apply(builder, NoiseBeehivesTreeConfiguration::new);
	});
	private final boolean useNoiseBeehives;

	public NoiseBeehivesTreeConfiguration(TreeConfiguration configuration, boolean useNoiseBeehives) {
		super(configuration.trunkProvider, configuration.trunkPlacer, configuration.foliageProvider, configuration.foliagePlacer, configuration.rootPlacer, configuration.dirtProvider, configuration.minimumSize, configuration.decorators, configuration.ignoreVines, configuration.forceDirt);
		this.useNoiseBeehives = useNoiseBeehives;
	}

	public static NoiseBeehivesTreeConfiguration noised(TreeConfiguration config) {
		return new NoiseBeehivesTreeConfiguration(config, true);
	}

	public static boolean useNoiseBeehives(TreeConfiguration config) {
		return config instanceof NoiseBeehivesTreeConfiguration c && c.useNoiseBeehives;
	}

	public boolean usesNoiseBeehives() {
		return this.useNoiseBeehives;
	}
}
