package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.environmental.common.entity.projectile.ThrownDuckEgg;
import com.teamabnormals.environmental.common.entity.projectile.ThrownMudBall;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;

public class EnvironmentalCompat {

	public static void register() {
		registerCompostables();
		registerFlammables();
		registerDispenserBehaviors();
		changeLocalization();
	}

	private static void changeLocalization() {
		DataUtil.changeBlockLocalization(Blocks.DIRT_PATH, Environmental.MOD_ID, "grass_path");
	}

	public static void registerCompostables() {
		DataUtil.registerCompostable(EnvironmentalBlocks.LARGE_LILY_PAD.get(), 0.85F);
		DataUtil.registerCompostable(EnvironmentalBlocks.GIANT_LILY_PAD.get(), 1.0F);

		DataUtil.registerCompostable(EnvironmentalBlocks.WILLOW_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.WILLOW_SAPLING.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.HANGING_WILLOW_LEAVES.get(), 0.30F);

		DataUtil.registerCompostable(EnvironmentalBlocks.PINE_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PINE_SAPLING.get(), 0.30F);

		DataUtil.registerCompostable(EnvironmentalBlocks.PINECONE.get(), 0.85F);

		DataUtil.registerCompostable(EnvironmentalBlocks.PLUM_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PLUM_SAPLING.get(), 0.30F);

		DataUtil.registerCompostable(EnvironmentalBlocks.CHEERFUL_PLUM_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CHEERFUL_PLUM_SAPLING.get(), 0.30F);

		DataUtil.registerCompostable(EnvironmentalBlocks.MOODY_PLUM_LEAVES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.MOODY_PLUM_SAPLING.get(), 0.30F);

		DataUtil.registerCompostable(EnvironmentalItems.CHERRIES.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CHERRY_CRATE.get(), 1.0F);

		DataUtil.registerCompostable(EnvironmentalItems.PLUM.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PLUM_CRATE.get(), 1.0F);

		DataUtil.registerCompostable(EnvironmentalItems.CATTAIL_FLUFF.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CATTAIL_FLUFF_BLOCK.get(), 1.0F);

		DataUtil.registerCompostable(EnvironmentalBlocks.DUCKWEED.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CATTAIL.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.GIANT_TALL_GRASS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.MYCELIUM_SPROUTS.get(), 0.50F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CUP_LICHEN.get(), 0.30F);
		DataUtil.registerCompostable(EnvironmentalBlocks.DWARF_SPRUCE.get(), 0.50F);

		DataUtil.registerCompostable(EnvironmentalItems.TRUFFLE.get(), 0.65F);

		DataUtil.registerCompostable(EnvironmentalBlocks.CATTAIL_THATCH.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CATTAIL_THATCH_SLAB.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.CATTAIL_THATCH_STAIRS.get(), 0.65F);

		DataUtil.registerCompostable(EnvironmentalBlocks.DUCKWEED_THATCH.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.DUCKWEED_THATCH_SLAB.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.DUCKWEED_THATCH_STAIRS.get(), 0.65F);

		DataUtil.registerCompostable(EnvironmentalBlocks.GRASS_THATCH.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.GRASS_THATCH_SLAB.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.GRASS_THATCH_STAIRS.get(), 0.65F);

		DataUtil.registerCompostable(EnvironmentalBlocks.WISTERIA_LEAVES.get(), 0.30F);

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

		DataUtil.registerCompostable(EnvironmentalBlocks.CARTWHEEL.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.VIOLET.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.DIANTHUS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.RED_LOTUS_FLOWER.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.WHITE_LOTUS_FLOWER.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.BLUEBELL.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.TASSELFLOWER.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.YELLOW_HIBISCUS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.ORANGE_HIBISCUS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.RED_HIBISCUS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PINK_HIBISCUS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.MAGENTA_HIBISCUS.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PURPLE_HIBISCUS.get(), 0.65F);

		DataUtil.registerCompostable(EnvironmentalBlocks.HIBISCUS_LEAVES.get(), 0.50F);

		DataUtil.registerCompostable(EnvironmentalBlocks.BLUE_DELPHINIUM.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.WHITE_DELPHINIUM.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PINK_DELPHINIUM.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.PURPLE_DELPHINIUM.get(), 0.65F);
		DataUtil.registerCompostable(EnvironmentalBlocks.BIRD_OF_PARADISE.get(), 0.65F);
	}

	public static void registerFlammables() {
		DataUtil.registerFlammable(EnvironmentalBlocks.YAK_HAIR_BLOCK.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.YAK_HAIR_RUG.get(), 30, 60);

		DataUtil.registerFlammable(EnvironmentalBlocks.CATTAIL_FLUFF_BLOCK.get(), 30, 60);

		DataUtil.registerFlammable(EnvironmentalBlocks.GRASS_THATCH.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.GRASS_THATCH_STAIRS.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.GRASS_THATCH_SLAB.get(), 60, 20);

		DataUtil.registerFlammable(EnvironmentalBlocks.CATTAIL_THATCH.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CATTAIL_THATCH_STAIRS.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CATTAIL_THATCH_SLAB.get(), 60, 20);

		DataUtil.registerFlammable(EnvironmentalBlocks.DUCKWEED_THATCH.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.DUCKWEED_THATCH_STAIRS.get(), 60, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.DUCKWEED_THATCH_SLAB.get(), 60, 20);

		DataUtil.registerFlammable(EnvironmentalBlocks.HIBISCUS_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.HIBISCUS_LEAF_PILE.get(), 30, 60);

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
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_BOOKSHELF.get(), 30, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_BEEHIVE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.WILLOW_BOARDS.get(), 5, 20);

		DataUtil.registerFlammable(EnvironmentalBlocks.PINE_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINE_LOG.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINE_WOOD.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_PINE_LOG.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_PINE_WOOD.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINE_PLANKS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINE_SLAB.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINE_STAIRS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINE_FENCE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINE_FENCE_GATE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINE_BOOKSHELF.get(), 30, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINE_BEEHIVE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINE_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINE_BOARDS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINECONE.get(), 5, 20);

		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_LOG.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_WOOD.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_PLUM_LOG.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.STRIPPED_PLUM_WOOD.get(), 5, 5);
		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_PLANKS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_SLAB.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_STAIRS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_FENCE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_FENCE_GATE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_BOOKSHELF.get(), 30, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHERRY_CRATE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_CRATE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_BEEHIVE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PLUM_BOARDS.get(), 5, 20);

		DataUtil.registerFlammable(EnvironmentalBlocks.CHEERFUL_PLUM_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.CHEERFUL_PLUM_LEAF_PILE.get(), 30, 60);

		DataUtil.registerFlammable(EnvironmentalBlocks.MOODY_PLUM_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.MOODY_PLUM_LEAF_PILE.get(), 30, 60);

		DataUtil.registerFlammable(EnvironmentalBlocks.GIANT_TALL_GRASS.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.MYCELIUM_SPROUTS.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.CUP_LICHEN.get(), 60, 100);

		DataUtil.registerFlammable(EnvironmentalBlocks.DWARF_SPRUCE.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.DWARF_SPRUCE_PLANT.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.DWARF_SPRUCE_TORCH.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.DWARF_SPRUCE_PLANT_TORCH.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.DWARF_SPRUCE_SOUL_TORCH.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.DWARF_SPRUCE_PLANT_SOUL_TORCH.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.DWARF_SPRUCE_REDSTONE_TORCH.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.DWARF_SPRUCE_PLANT_REDSTONE_TORCH.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.DWARF_SPRUCE_ENDER_TORCH.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.DWARF_SPRUCE_PLANT_ENDER_TORCH.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.DWARF_SPRUCE_CUPRIC_TORCH.get(), 60, 100);
		DataUtil.registerFlammable(EnvironmentalBlocks.DWARF_SPRUCE_PLANT_CUPRIC_TORCH.get(), 60, 100);

		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_LEAVES.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_LEAF_PILE.get(), 30, 60);

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
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_BOOKSHELF.get(), 30, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_BEEHIVE.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.WISTERIA_BOARDS.get(), 5, 20);
		DataUtil.registerFlammable(EnvironmentalBlocks.BLUE_WISTERIA_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.WHITE_WISTERIA_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PINK_WISTERIA_LEAF_PILE.get(), 30, 60);
		DataUtil.registerFlammable(EnvironmentalBlocks.PURPLE_WISTERIA_LEAF_PILE.get(), 30, 60);
	}

	public static DispenseItemBehavior EMPTY_FISH_BUCKET_BEHAVIOR = new DefaultDispenseItemBehavior() {
		private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

		@Override
		public ItemStack execute(BlockSource source, ItemStack stack) {
			DispensibleContainerItem item = (DispensibleContainerItem) stack.getItem();
			BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
			Level level = source.getLevel();
			if (item.emptyContents(null, level, pos, null, stack)) {
				item.checkExtraContent(null, level, stack, pos);
				return new ItemStack(Items.BUCKET);
			} else {
				return this.defaultDispenseItemBehavior.dispense(source, stack);
			}
		}
	};

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

		DispenserBlock.registerBehavior(EnvironmentalItems.KOI_BUCKET.get(), EMPTY_FISH_BUCKET_BEHAVIOR);
		DispenserBlock.registerBehavior(EnvironmentalItems.SLABFISH_BUCKET.get(), EMPTY_FISH_BUCKET_BEHAVIOR);
	}
}
