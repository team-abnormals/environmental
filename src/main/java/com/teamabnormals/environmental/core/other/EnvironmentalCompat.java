package com.teamabnormals.environmental.core.other;

import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.environmental.core.Environmental;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;

public class EnvironmentalCompat {

	public static void register() {
		registerFlammables();
		registerDispenserBehaviors();
		changeLocalization();
	}

	private static void changeLocalization() {
		DataUtil.changeBlockLocalization(Blocks.DIRT_PATH, Environmental.MOD_ID, "grass_path");
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
			BlockPos pos = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
			Level level = source.level();
			if (item.emptyContents(null, level, pos, null, stack)) {
				item.checkExtraContent(null, level, stack, pos);
				return this.consumeWithRemainder(source, stack, new ItemStack(Items.BUCKET));
			} else {
				return this.defaultDispenseItemBehavior.dispense(source, stack);
			}
		}
	};

	public static void registerDispenserBehaviors() {
		DispenserBlock.registerProjectileBehavior(EnvironmentalItems.DUCK_EGG.get());
		DispenserBlock.registerProjectileBehavior(EnvironmentalItems.MUD_BALL.get());
		DispenserBlock.registerBehavior(EnvironmentalItems.KOI_BUCKET.get(), EMPTY_FISH_BUCKET_BEHAVIOR);
		DispenserBlock.registerBehavior(EnvironmentalItems.SLABFISH_BUCKET.get(), EMPTY_FISH_BUCKET_BEHAVIOR);
	}
}
