package com.teamabnormals.environmental.common.block;

import com.teamabnormals.blueprint.core.util.BlockUtil;
import com.teamabnormals.blueprint.core.util.MathUtil;
import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import com.teamabnormals.environmental.core.other.tags.EnvironmentalBlockTags;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalItems;
import com.teamabnormals.environmental.core.registry.EnvironmentalParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ToolActions;

public class CattailBlock extends BushBlock implements SimpleWaterloggedBlock, BonemealableBlock {
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
	private static final TargetedItemCategoryFiller FILLER = new TargetedItemCategoryFiller(() -> Items.SEA_PICKLE);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty TOP = BooleanProperty.create("top");
	public static final BooleanProperty FLUFFY = BooleanProperty.create("fluffy");
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	public static final IntegerProperty CATTAILS = IntegerProperty.create("cattails", 1, 3);

	public CattailBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(CATTAILS, 1).setValue(TOP, false).setValue(FLUFFY, false).setValue(WATERLOGGED, false).setValue(AGE, 0));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		Vec3 vec3d = state.getOffset(level, pos);
		return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		return this.mayPlaceOn(world.getBlockState(pos.below()), world, pos);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(EnvironmentalBlockTags.CATTAIL_PLANTABLE_ON) || state.is(this) || state.is(EnvironmentalBlocks.CATTAIL_STALK.get());
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockState state = level.getBlockState(pos);
		BlockState belowState = level.getBlockState(pos.below());
		FluidState fluidState = level.getFluidState(pos);

		if (state.is(this)) {
			return state.cycle(CATTAILS);
		} else if (belowState.is(this) && belowState.getValue(CATTAILS) > 1) {
			return null;
		} else {
			return super.getStateForPlacement(context).setValue(TOP, belowState.is(this)).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(AGE, level.getRandom().nextInt(this.getMaxAge()));
		}
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		return !context.isSecondaryUseActive() && context.getItemInHand().getItem() == this.asItem() && state.getValue(CATTAILS) < 3 && !state.getValue(TOP) || super.canBeReplaced(state, context);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(CATTAILS, TOP, FLUFFY, WATERLOGGED, AGE);
	}

	protected boolean canGrowInto(BlockState state) {
		return state.is(Blocks.WATER) || state.isAir();
	}

	public int getMaxAge() {
		return 2;
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return state.getValue(AGE) < this.getMaxAge() || state.getValue(WATERLOGGED) || (!state.getValue(FLUFFY) && !state.getValue(WATERLOGGED));
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!state.getValue(FLUFFY) && !state.getValue(WATERLOGGED) && ForgeHooks.onCropsGrowPre(level, pos, state, random.nextDouble() < 0.1D)) {
			for (int i = 1; this.isCattail(level, pos.below(i)) || this.canGrowFluff(level, pos.below(i)); i++) {
				if (this.canGrowFluff(level, pos.below(i))) {
					BlockState fluffyState = state.setValue(FLUFFY, true);
					level.setBlock(pos, fluffyState, 2);
					level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(fluffyState));
					ForgeHooks.onCropsGrowPost(level, pos, state);
					break;
				}
			}
		}

		BlockPos abovePos = pos.above();
		boolean notMaxAge = state.getValue(AGE) < this.getMaxAge();
		if ((notMaxAge || state.getValue(WATERLOGGED)) && ForgeHooks.onCropsGrowPre(level, abovePos, level.getBlockState(abovePos), random.nextDouble() < 0.1D)) {
			if (this.canGrowInto(level.getBlockState(abovePos))) {
				BlockState newState = state.setValue(TOP, true).setValue(WATERLOGGED, level.getFluidState(abovePos).getType() == Fluids.WATER);
				if (notMaxAge) {
					newState = newState.cycle(AGE);
				}
				level.setBlockAndUpdate(abovePos, newState);
				ForgeHooks.onCropsGrowPost(level, abovePos, level.getBlockState(abovePos));
			}
		}
	}

	public boolean isCattail(ServerLevel level, BlockPos pos) {
		BlockState state = level.getBlockState(pos);
		return state.is(this) || state.is(EnvironmentalBlocks.CATTAIL_STALK.get());
	}

	public boolean canGrowFluff(ServerLevel level, BlockPos pos) {
		return level.getBlockState(pos).is(Blocks.MUD);
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		boolean fluffy = state.getValue(FLUFFY);
		ItemStack stack = player.getItemInHand(hand);
		if (!fluffy && stack.is(Items.BONE_MEAL)) {
			return InteractionResult.PASS;
		} else if (fluffy && stack.canPerformAction(ToolActions.SHEARS_CARVE)) {
			if (!level.isClientSide()) {
				level.playSound(null, pos, SoundEvents.SNOW_GOLEM_SHEAR, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
				if (level.random.nextInt(900) < 5) {
					level.playSound(null, pos, SoundEvents.CAT_AMBIENT, SoundSource.BLOCKS, 2.0F, (level.random.nextFloat() - level.random.nextFloat()) * 0.2F + 1.0F);
				}
				popResource(level, pos, new ItemStack(EnvironmentalItems.CATTAIL_FLUFF.get(), 1 + level.random.nextInt(state.getValue(CATTAILS))));
				level.setBlockAndUpdate(pos, state.setValue(FLUFFY, false));
				stack.hurtAndBreak(1, player, (p_55287_) -> p_55287_.broadcastBreakEvent(hand));
				level.gameEvent(player, GameEvent.SHEAR, pos);
				player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return super.use(state, level, pos, player, hand, result);
		}
	}


	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entityIn) {
		if (entityIn instanceof LivingEntity) {
			RandomSource random = level.getRandom();
			if (state.getValue(FLUFFY)) {
				Vec3 offset = state.getOffset(level, pos);
				double offsetX = MathUtil.makeNegativeRandomly(random.nextFloat() * 0.25F, random);
				double offsetZ = MathUtil.makeNegativeRandomly(random.nextFloat() * 0.25F, random);

				double x = pos.getX() + offset.x() + 0.5D + offsetX;
				double y = pos.getY() + offset.y() + 0.75D + MathUtil.makeNegativeRandomly(random.nextFloat() * 0.05F, random);
				double z = pos.getZ() + offset.z() + 0.5D + offsetZ;

				if (random.nextInt(6 - state.getValue(CATTAILS)) == 0) {
					level.addParticle(EnvironmentalParticleTypes.CATTAIL_FLUFF.get(), x, y, z, random.nextDouble() * 0.01D * Math.abs(offsetX) / offsetX, 0.0D, random.nextDouble() * 0.01D * Math.abs(offsetZ) / offsetZ);
				}
			}
		}
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
		return this.canGrowInto(level.getBlockState(pos.above())) || (!state.getValue(FLUFFY) && !state.getValue(WATERLOGGED));
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		BlockPos abovePos = pos.above();
		if (!state.getValue(FLUFFY) && !state.getValue(WATERLOGGED)) {
			level.setBlock(pos, state.setValue(FLUFFY, true), 2);
		} else if (this.canGrowInto(level.getBlockState(abovePos))) {
			int age = Math.min(state.getValue(AGE) + 1, this.getMaxAge());
			level.setBlockAndUpdate(abovePos, state.setValue(AGE, age).setValue(TOP, true).setValue(WATERLOGGED, level.getFluidState(abovePos).getType() == Fluids.WATER));
		}
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!state.canSurvive(level, pos)) {
			level.destroyBlock(pos, true);
		}
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
		if (direction == Direction.DOWN && !state.canSurvive(level, pos)) {
			level.scheduleTick(pos, this, 1);
		}

		if (direction != Direction.UP || !facingState.is(this) && !facingState.is(EnvironmentalBlocks.CATTAIL_STALK.get())) {
			if (state.getValue(WATERLOGGED)) {
				level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
			}

			return super.updateShape(state, direction, facingState, level, pos, facingPos);
		} else {
			return BlockUtil.transferAllBlockStates(state, EnvironmentalBlocks.CATTAIL_STALK.get().defaultBlockState()).setValue(CattailStalkBlock.BOTTOM, !level.getBlockState(pos.below()).is(EnvironmentalBlocks.CATTAIL_STALK.get()));
		}
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		FILLER.fillItem(this.asItem(), group, items);
	}

	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (state.getValue(FLUFFY)) {
			Vec3 offset = state.getOffset(level, pos);
			double offsetX = MathUtil.makeNegativeRandomly(random.nextFloat() * 0.25F, random);
			double offsetZ = MathUtil.makeNegativeRandomly(random.nextFloat() * 0.25F, random);

			double x = pos.getX() + offset.x() + 0.5D + offsetX;
			double y = pos.getY() + offset.y() + 0.75D + MathUtil.makeNegativeRandomly(random.nextFloat() * 0.05F, random);
			double z = pos.getZ() + offset.z() + 0.5D + offsetZ;

			if (random.nextInt(15) == 0) {
				level.addParticle(EnvironmentalParticleTypes.CATTAIL_FLUFF.get(), x, y, z, random.nextDouble() * 0.01D * Math.abs(offsetX) / offsetX, 0.0D, random.nextDouble() * 0.01D * Math.abs(offsetZ) / offsetZ);
			}
		}
	}
}