package com.teamabnormals.environmental.core.registry.datapack;

import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class EnvironmentalPaintingVariants {
	public static final ResourceKey<PaintingVariant> SLABFISH = create("slabfish");
	public static final ResourceKey<PaintingVariant> SNAKE_BLOCK = create("snake_block");
	public static final ResourceKey<PaintingVariant> ARCHIVE = create("archive");
	public static final ResourceKey<PaintingVariant> OPTIMAL_AERODYNAMICS = create("optimal_aerodynamics");
	public static final ResourceKey<PaintingVariant> IN_PLAINS_SIGHT = create("in_plains_sight");
	public static final ResourceKey<PaintingVariant> THE_PLACE_WITHIN_THE_PINES = create("the_place_within_the_pines");
	public static final ResourceKey<PaintingVariant> BOUQUET = create("bouquet");
	public static final ResourceKey<PaintingVariant> BOUQUET2 = create("bouquet2");
	public static final ResourceKey<PaintingVariant> LONE_PLUM = create("lone_plum");
	public static final ResourceKey<PaintingVariant> MARSHPATH = create("marshpath");

	public static void bootstrap(BootstrapContext<PaintingVariant> context) {
		register(context, SLABFISH, 2, 2);
		register(context, SNAKE_BLOCK, 2, 2);
		register(context, ARCHIVE, 4, 3);
		register(context, OPTIMAL_AERODYNAMICS, 2, 3);
		register(context, IN_PLAINS_SIGHT, 2, 1);
		register(context, THE_PLACE_WITHIN_THE_PINES, 3, 1);
		register(context, BOUQUET, 1, 2);
		register(context, BOUQUET2, 1, 1);
		register(context, LONE_PLUM, 3, 2);
		register(context, MARSHPATH, 2, 3);
	}

	private static ResourceKey<PaintingVariant> create(String name) {
		return ResourceKey.create(Registries.PAINTING_VARIANT, Environmental.location(name));
	}

	private static void register(BootstrapContext<PaintingVariant> context, ResourceKey<PaintingVariant> key, int width, int height) {
		context.register(key, new PaintingVariant(width, height, key.location()));
	}
}
