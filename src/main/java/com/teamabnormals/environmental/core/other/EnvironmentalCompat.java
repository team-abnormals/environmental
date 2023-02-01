package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.environmental.common.entity.projectile.ThrownDuckEgg;
import com.teamabnormals.environmental.common.entity.projectile.ThrownMudBall;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.List;

public class EnvironmentalCompat {

	public static void registerCompat() {
		registerCompostables();
		registerFlammables();
		registerDispenserBehaviors();
	}

	public static void registerCompostables() {
		DataUtil.registerCompostable(EnvironmentalItems.YAK_HAIR.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.YAK_HAIR_BLOCK.get(), 0.85F);
		DataUtil.registerCompostable(EnvironmentalBlocks.YAK_HAIR_RUG.get(), 0.50F);

		DataUtil.registerCompostable(EnvironmentalBlocks.LARGE_LILY_PAD.get(), 0.85F);
		DataUtil.registerCompostable(EnvironmentalBlocks.GIANT_LILY_PAD.get(), 1.0F);

		DataUtil.registerCompostable(EnvironmentalBlocks.WILLOW_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.WILLOW_SAPLING.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.WILLOW_LEAF_CARPET.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(), 0.30F);

		DataUtil.registerCompostable(EnvironmentalBlocks.CHERRY_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CHERRY_SAPLING.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CHERRY_LEAF_CARPET.get(), 0.30F);

		DataUtil.registerCompostable(EnvironmentalItems.CHERRIES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalItems.CHERRY_PIE.get(), 1.0F);
		DataUtil.registerCompostable(EnvironmentalItems.APPLE_PIE.get(), 1.0F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CHERRY_CRATE.get(), 1.0F);

		DataUtil.registerCompostable(EnvironmentalItems.CATTAIL_SEEDS.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CATTAIL_SEED_SACK.get(), 1.0F);

		DataUtil.registerCompostable(EnvironmentalBlocks.DUCKWEED.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CATTAIL.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.TALL_CATTAIL.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.GIANT_TALL_GRASS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.MYCELIUM_SPROUTS.get(), 0.50F);

		DataUtil.registerCompostable(EnvironmentalItems.TRUFFLE.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.TRUFFLE_CRATE.get(), 1.0F);

		DataUtil.registerCompostable(EnvironmentalBlocks.CATTAIL_THATCH.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CATTAIL_THATCH_SLAB.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CATTAIL_THATCH_STAIRS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CATTAIL_THATCH_VERTICAL_SLAB.get(), 0.65F);

		DataUtil.registerCompostable(EnvironmentalBlocks.DUCKWEED_THATCH.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.DUCKWEED_THATCH_SLAB.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.DUCKWEED_THATCH_STAIRS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.DUCKWEED_THATCH_VERTICAL_SLAB.get(), 0.65F);

		DataUtil.registerCompostable(EnvironmentalBlocks.GRASS_THATCH.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.GRASS_THATCH_SLAB.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.GRASS_THATCH_STAIRS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.GRASS_THATCH_VERTICAL_SLAB.get(), 0.65F);

		DataUtil.registerCompostable(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), 0.30F);

		DataUtil.registerCompostable(EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get(), 0.30F);

		DataUtil.registerCompostable(EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get(), 0.30F);

		DataUtil.registerCompostable(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_CARPET.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_CARPET.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PINK_WISTERIA_LEAF_CARPET.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_CARPET.get(), 0.30F);

		DataUtil.registerCompostable(EnvironmentalBlocks.CARTWHEEL.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.VIOLET.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.DIANTHUS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.RED_LOTUS_FLOWER.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.BLUEBELL.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.YELLOW_HIBISCUS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.ORANGE_HIBISCUS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.RED_HIBISCUS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PINK_HIBISCUS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.MAGENTA_HIBISCUS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PURPLE_HIBISCUS.get(), 0.65F);

		DataUtil.registerCompostable(EnvironmentalBlocks.BLUE_DELPHINIUM.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.WHITE_DELPHINIUM.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PINK_DELPHINIUM.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.BIRD_OF_PARADISE.get(), 0.65F);
	}

	public static void registerFlammables() {
		DataUtil.registerFlammable(EnvironmentalBlocks.YAK_HAIR_BLOCK.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.YAK_HAIR_RUG.get(), 30, 60);

		DataUtil.registerFlammable(EnvironmentalBlocks.CATTAIL_SEED_SACK.get(), 30, 60);

		DataUtil.registerFlammable(EnvironmentalBlocks.GRASS_THATCH.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.GRASS_THATCH_STAIRS.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.GRASS_THATCH_SLAB.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.GRASS_THATCH_VERTICAL_SLAB.get(), 60, 20);

		DataUtil.registerFlammable(EnvironmentalBlocks.CATTAIL_THATCH.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CATTAIL_THATCH_STAIRS.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CATTAIL_THATCH_SLAB.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CATTAIL_THATCH_VERTICAL_SLAB.get(), 60, 20);

		DataUtil.registerFlammable(EnvironmentalBlocks.DUCKWEED_THATCH.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.DUCKWEED_THATCH_STAIRS.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.DUCKWEED_THATCH_SLAB.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.DUCKWEED_THATCH_VERTICAL_SLAB.get(), 60, 20);

		DataUtil.registerFlammable(EnvironmentalBlocks.TRUFFLE_CRATE.get(), 5, 20);

		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_LOG.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_WOOD.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_WILLOW_LOG.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_WILLOW_WOOD.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_PLANKS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_SLAB.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_STAIRS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_FENCE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_FENCE_GATE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.VERTICAL_WILLOW_PLANKS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_LEAF_CARPET.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_VERTICAL_SLAB.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_BOOKSHELF.get(), 30, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_BEEHIVE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_POST.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_WILLOW_POST.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_HEDGE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_BOARDS.get(), 5, 20);

		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_LOG.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_WOOD.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_CHERRY_LOG.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_CHERRY_WOOD.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_PLANKS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_SLAB.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_STAIRS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_FENCE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_FENCE_GATE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.VERTICAL_CHERRY_PLANKS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_LEAF_CARPET.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_VERTICAL_SLAB.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_BOOKSHELF.get(), 30, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_CRATE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_BEEHIVE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_POST.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_CHERRY_POST.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_HEDGE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_BOARDS.get(), 5, 20);

		DataUtil.registerFlammable(EnvironmentalBlocks.GIANT_TALL_GRASS.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.MYCELIUM_SPROUTS.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.TALL_DEAD_BUSH.get(), 60, 100);

		DataUtil.registerFlammable(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_LOG.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_WOOD.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_WISTERIA_LOG.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_WISTERIA_WOOD.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_PLANKS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_SLAB.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_STAIRS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_FENCE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_FENCE_GATE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.BLUE_DELPHINIUM.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.WHITE_DELPHINIUM.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINK_DELPHINIUM.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.VERTICAL_WISTERIA_PLANKS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_CARPET.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_CARPET.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINK_WISTERIA_LEAF_CARPET.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_CARPET.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_VERTICAL_SLAB.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_BOOKSHELF.get(), 30, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_BEEHIVE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_POST.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_WISTERIA_POST.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINK_WISTERIA_HEDGE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.BLUE_WISTERIA_HEDGE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PURPLE_WISTERIA_HEDGE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WHITE_WISTERIA_HEDGE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_BOARDS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINK_WISTERIA_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_PILE.get(), 30, 60);
	}

	public static void registerDispenserBehaviors() {
		DispenserBlock.registerBehavior(EnvironmentalItems.DUCK_EGG.get(), new AbstractProjectileDispenseBehavior() {
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				return new ThrownDuckEgg(worldIn, position.x(), position.y(), position.z());
			}
		});

		DispenserBlock.registerBehavior(EnvironmentalItems.MUD_BALL.get(), new AbstractProjectileDispenseBehavior() {
			protected Projectile getProjectile(Level worldIn, Position position, ItemStack stackIn) {
				return new ThrownMudBall(worldIn, position.x(), position.y(), position.z());
			}
		});

		DispenserBlock.registerBehavior(EnvironmentalItems.SLABFISH_BUCKET.get(), new DefaultDispenseItemBehavior() {
			@Override
			protected ItemStack execute(BlockSource source, ItemStack stack) {
				BucketItem bucket = (BucketItem) stack.getItem();
				BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				Level world = source.getLevel();
				if (bucket.emptyContents(null, world, pos, null)) {
					bucket.checkExtraContent(null, world, stack, pos);
					return new ItemStack(Items.BUCKET);
				} else {
					return super.dispense(source, stack);
				}
			}
		});
	}


	public static void setRenderLayers() {
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.SLABFISH_EFFIGY.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.TALL_DEAD_BUSH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.YAK_HAIR_BLOCK.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.YAK_HAIR_RUG.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MYCELIUM_SPROUTS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GIANT_TALL_GRASS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.LARGE_LILY_PAD.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GIANT_LILY_PAD.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_SPROUTS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.TALL_CATTAIL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_CATTAIL.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH_SLAB.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH_STAIRS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.GRASS_THATCH_VERTICAL_SLAB.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_SLAB.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_STAIRS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CATTAIL_THATCH_VERTICAL_SLAB.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_SLAB.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_STAIRS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DUCKWEED_THATCH_VERTICAL_SLAB.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CARTWHEEL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.VIOLET.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.RED_LOTUS_FLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.DIANTHUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUEBELL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.YELLOW_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.ORANGE_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.RED_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.MAGENTA_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BIRD_OF_PARADISE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_CARTWHEEL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_VIOLET.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_RED_LOTUS_FLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_WHITE_LOTUS_FLOWER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_DIANTHUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_BLUEBELL.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_YELLOW_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_ORANGE_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_RED_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PINK_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_MAGENTA_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PURPLE_HIBISCUS.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_BIRD_OF_PARADISE.get(), RenderType.cutout());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_TRAPDOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_LADDER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_POST.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.STRIPPED_WILLOW_POST.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_WILLOW_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WILLOW_LEAF_PILE.get(), RenderType.cutoutMipped());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_TRAPDOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_LADDER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_POST.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.STRIPPED_CHERRY_POST.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_CHERRY_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.CHERRY_LEAF_PILE.get(), RenderType.cutoutMipped());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_DOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_TRAPDOOR.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_LADDER.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WISTERIA_POST.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.STRIPPED_WISTERIA_POST.get(), RenderType.cutoutMipped());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_HANGING_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_WHITE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_WHITE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_PILE.get(), RenderType.cutoutMipped());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_HANGING_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_BLUE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_BLUE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_PILE.get(), RenderType.cutoutMipped());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_HANGING_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PINK_WISTERIA_SAPLING.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PINK_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PINK_WISTERIA_LEAF_PILE.get(), RenderType.cutoutMipped());

		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_HANGING_WISTERIA_LEAVES.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_CARPET.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_HEDGE.get(), RenderType.cutoutMipped());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PURPLE_WISTERIA_SAPLING.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.POTTED_PURPLE_DELPHINIUM.get(), RenderType.cutout());
		ItemBlockRenderTypes.setRenderLayer(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_PILE.get(), RenderType.cutoutMipped());
	}

	public static void registerBlockColors() {
		BlockColors blockColors = Minecraft.getInstance().getBlockColors();
		ItemColors itemColors = Minecraft.getInstance().getItemColors();

		List<RegistryObject<Block>> grassColors = Arrays.asList(EnvironmentalBlocks.GIANT_TALL_GRASS);
		List<RegistryObject<Block>> foliageColors = Arrays.asList(EnvironmentalBlocks.WILLOW_LEAVES, EnvironmentalBlocks.WILLOW_LEAF_CARPET, EnvironmentalBlocks.HANGING_WILLOW_LEAVES, EnvironmentalBlocks.WILLOW_HEDGE, EnvironmentalBlocks.WILLOW_LEAF_PILE);
		List<RegistryObject<Block>> waterLilyColors = Arrays.asList(EnvironmentalBlocks.LARGE_LILY_PAD, EnvironmentalBlocks.GIANT_LILY_PAD);

		DataUtil.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? BiomeColors.getAverageGrassColor(world, pos) : GrassColor.get(0.5D, 1.0D), grassColors);
		DataUtil.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos) : FoliageColor.get(0.5D, 1.0D), foliageColors);
		DataUtil.registerBlockColor(blockColors, (x, world, pos, u) -> world != null && pos != null ? 2129968 : 7455580, waterLilyColors);

		DataUtil.registerBlockItemColor(itemColors, (color, items) -> GrassColor.get(0.5D, 1.0D), grassColors);
		DataUtil.registerBlockItemColor(itemColors, (color, items) -> FoliageColor.get(0.5D, 1.0D), foliageColors);
		DataUtil.registerBlockItemColor(itemColors, (block, tintIndex) -> {
			BlockState blockstate = ((BlockItem) block.getItem()).getBlock().defaultBlockState();
			return blockColors.getColor(blockstate, null, null, tintIndex);
		}, waterLilyColors);

	}
}
