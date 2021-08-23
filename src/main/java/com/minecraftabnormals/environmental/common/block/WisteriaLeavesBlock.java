package com.minecraftabnormals.environmental.common.block;

import com.minecraftabnormals.abnormals_core.core.util.item.filling.TargetedItemGroupFiller;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;

import java.util.Random;

public class WisteriaLeavesBlock extends Block implements IForgeShearable {
	public static final IntegerProperty DISTANCE = IntegerProperty.create("distance", 1, 8);
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;
	private static final TargetedItemGroupFiller FILLER = new TargetedItemGroupFiller(() -> Items.DARK_OAK_LEAVES);

	public WisteriaLeavesBlock(Block.Properties properties) {
		super(properties);
		registerDefaultState(stateDefinition.any().setValue(DISTANCE, 8).setValue(PERSISTENT, false));
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(DISTANCE) == 8 && !state.getValue(PERSISTENT);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		if (!state.getValue(PERSISTENT) && state.getValue(DISTANCE) == 8) {
			dropResources(state, worldIn, pos);
			worldIn.removeBlock(pos, false);
		}
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		worldIn.setBlock(pos, updateDistance(state, worldIn, pos), 3);
	}

	@Override
	public int getLightBlock(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 1;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		int i = getDistance(facingState) + 1;
		if (i != 1 || stateIn.getValue(DISTANCE) != i) {
			worldIn.getBlockTicks().scheduleTick(currentPos, this, 1);
		}
		return stateIn;
	}

	private static BlockState updateDistance(BlockState state, IWorld worldIn, BlockPos pos) {
		int i = 8;
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for (Direction direction : Direction.values()) {
			blockpos$mutable.setWithOffset(pos, direction);
			i = Math.min(i, getDistance(worldIn.getBlockState(blockpos$mutable)) + 1);
			if (i == 1) {
				break;
			}
		}

		return state.setValue(DISTANCE, i);
	}

	private static int getDistance(BlockState neighbor) {
		if (BlockTags.LOGS.contains(neighbor.getBlock())) {
			return 0;
		} else {
			if (neighbor.getBlock() instanceof WisteriaLeavesBlock) return neighbor.getValue(DISTANCE);
			if (neighbor.getBlock() instanceof LeavesBlock) return neighbor.getValue(LeavesBlock.DISTANCE);
			else return 8;
		}
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.isRainingAt(pos.above())) {
			if (rand.nextInt(15) == 1) {
				BlockPos blockpos = pos.below();
				BlockState blockstate = worldIn.getBlockState(blockpos);
				if (!blockstate.canOcclude() || !blockstate.isFaceSturdy(worldIn, blockpos, Direction.UP)) {
					double d0 = (float) pos.getX() + rand.nextFloat();
					double d1 = (double) pos.getY() - 0.05D;
					double d2 = (float) pos.getZ() + rand.nextFloat();
					worldIn.addParticle(ParticleTypes.DRIPPING_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(DISTANCE, PERSISTENT);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return updateDistance(defaultBlockState().setValue(PERSISTENT, true), context.getLevel(), context.getClickedPos());
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}
}