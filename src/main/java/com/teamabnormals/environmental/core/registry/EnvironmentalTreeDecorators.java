package com.teamabnormals.environmental.core.registry;

import com.teamabnormals.environmental.common.levelgen.treedecorators.*;
import com.teamabnormals.environmental.core.Environmental;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class EnvironmentalTreeDecorators {
	public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, Environmental.MOD_ID);

	public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<?>> HANGING_WILLOW_LEAVES = TREE_DECORATORS.register("hanging_willow_leaves", () -> new TreeDecoratorType<>(HangingWillowDecorator.CODEC));
	public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<?>> HANGING_WISTERIA_LEAVES = TREE_DECORATORS.register("hanging_wisteria_leaves", () -> new TreeDecoratorType<>(HangingWisteriaDecorator.CODEC));
	public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<?>> PINECONE = TREE_DECORATORS.register("pinecone", () -> new TreeDecoratorType<>(PineconeDecorator.CODEC));
	public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<?>> PINE_PODZOL = TREE_DECORATORS.register("pine_podzol", () -> new TreeDecoratorType<>(PinePodzolDecorator.CODEC));
	public static final DeferredHolder<TreeDecoratorType<?>, TreeDecoratorType<?>> FALLEN_LEAVES_DECORATOR = TREE_DECORATORS.register("fallen_leaves", () -> new TreeDecoratorType<>(FallenLeavesDecorator.CODEC));
}
