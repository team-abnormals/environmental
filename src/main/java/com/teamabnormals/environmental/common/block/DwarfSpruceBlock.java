package com.teamabnormals.environmental.common.block;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.ItemAbilities;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class DwarfSpruceBlock extends BushBlock implements BonemealableBlock {
	protected final Supplier<Item> torch;

	public DwarfSpruceBlock(Properties properties, Supplier<Item> torch) {
		super(properties);
		this.torch = torch;
		if (torch != null)
			this.getTorchSpruces().put(torch, this);
	}

	public abstract Item getTorch();

	public abstract BlockState getWithoutTorchesState(BlockState state);

	abstract Map<Supplier<? extends Item>, Block> getTorchSpruces();

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!state.canSurvive(level, pos))
			level.destroyBlock(pos, true);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		BlockPos belowpos = pos.below();
		BlockState belowstate = level.getBlockState(belowpos);
		return belowstate.getBlock() instanceof DwarfSpruceBlock || canSupportCenter(level, belowpos, Direction.UP) || belowstate.getBlock() instanceof LeavesBlock;
	}

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		Item item = stack.getItem();
		Item torch = this.getTorch();
		if (stack.canPerformAction(ItemAbilities.SHEARS_HARVEST) && torch != null) {
			popResource(level, pos, new ItemStack(torch));
			level.setBlockAndUpdate(pos, this.getWithoutTorchesState(state));

			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
			level.playSound(null, pos, SoundEvents.SNOW_GOLEM_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
			level.gameEvent(player, GameEvent.SHEAR, pos);
			player.awardStat(Stats.ITEM_USED.get(item));
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		} else if (item != Items.AIR && torch == null) {
			Block torchspruce = this.getTorchSpruces().getOrDefault(this.getTorchSpruces().keySet().stream().filter(key -> item == key.get()).findFirst().orElse(null), null);
			if (torchspruce != null) {
				if (!player.isCreative())
					stack.shrink(1);

				BlockState blockstate = torchspruce.withPropertiesOf(state);
				if (item == Items.REDSTONE_TORCH)
					blockstate = RedstoneDwarfSpruceBlock.setLitPoweredState(blockstate, level, pos);
				level.setBlockAndUpdate(pos, blockstate);

				level.playSound(null, pos, SoundEvents.AZALEA_LEAVES_PLACE, SoundSource.BLOCKS, 0.4F, 1.0F);

				level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
				player.awardStat(Stats.ITEM_USED.get(item));
				return ItemInteractionResult.sidedSuccess(level.isClientSide);
			}
		}

		return super.useItemOn(stack, state, level, pos, player, hand, result);
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		List<ItemStack> drops = super.getDrops(state, builder);
		Item torch = this.getTorch();
		if (torch != null)
			drops.add(new ItemStack(torch));
		return drops;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	protected MapCodec<? extends BushBlock> codec() {
		return null;
	}

	@Override
	public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state) {
		return new ItemStack(EnvironmentalBlocks.DWARF_SPRUCE.get());
	}

	protected static BlockPos getHeadPos(BlockGetter level, BlockPos pos) {
		MutableBlockPos mutable = pos.mutable();
		while (true) {
			mutable.move(Direction.UP);
			if (level.getBlockState(mutable).getBlock() instanceof DwarfSpruceHeadBlock)
				return mutable.immutable();
			else if (!(level.getBlockState(mutable).getBlock() instanceof DwarfSprucePlantBlock))
				return null;
		}
	}

	protected static boolean isValidAboveBlock(BlockState state) {
		return state.getBlock() instanceof DwarfSpruceHeadBlock && state.getValue(DwarfSpruceHeadBlock.TOP) || state.getBlock() instanceof DwarfSprucePlantBlock && !state.getValue(DwarfSprucePlantBlock.BOTTOM);
	}
}