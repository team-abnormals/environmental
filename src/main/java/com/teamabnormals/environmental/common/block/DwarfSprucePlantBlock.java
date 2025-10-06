package com.teamabnormals.environmental.common.block;

import com.google.common.collect.Maps;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;
import java.util.function.Supplier;

public class DwarfSprucePlantBlock extends DwarfSpruceBlock {
	private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
	private static final Map<Supplier<? extends Item>, Block> TORCH_SPRUCES = Maps.newHashMap();

	private final DwarfSpruceHeadBlock headBlock;

	public DwarfSprucePlantBlock(Properties properties, DwarfSpruceHeadBlock headBlock) {
		this(properties, (Supplier<Item>) null, headBlock);
	}

	public DwarfSprucePlantBlock(Properties properties, ResourceLocation torch, DwarfSpruceHeadBlock headBlock) {
		this(properties, () -> BuiltInRegistries.ITEM.get(torch), headBlock);
	}

	public DwarfSprucePlantBlock(Properties properties, Supplier<Item> torch, DwarfSpruceHeadBlock headBlock) {
		super(properties, torch);
		this.headBlock = headBlock;
		this.headBlock.setBodyBlock(this);
		this.registerDefaultState(this.stateDefinition.any().setValue(BOTTOM, false));
	}

	@Override
	public Item getTorch() {
		return this.torch == null ? null : this.torch.get();
	}

	@Override
	public Map<Supplier<? extends Item>, Block> getTorchSpruces() {
		return TORCH_SPRUCES;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState offsetState, LevelAccessor level, BlockPos pos, BlockPos offsetPos) {
		if (!state.canSurvive(level, pos))
			level.scheduleTick(pos, this, 1);
		return direction == Direction.UP && !isValidAboveBlock(offsetState) ? this.getHeadState(state) : state;
	}

	protected BlockState getHeadState(BlockState originalState) {
		return this.headBlock.defaultBlockState().setValue(DwarfSpruceHeadBlock.TOP, !originalState.getValue(BOTTOM));
	}

	@Override
	public BlockState getWithoutTorchesState(BlockState state) {
		return EnvironmentalBlocks.DWARF_SPRUCE_PLANT.get().defaultBlockState().setValue(BOTTOM, state.getValue(BOTTOM));
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		BlockPos headpos = getHeadPos(level, pos);
		return headpos != null && level.getBlockState(headpos.above()).isAir();
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		BlockPos headpos = getHeadPos(level, pos);
		if (headpos != null) {
			BlockState headstate = level.getBlockState(headpos);
			((DwarfSpruceHeadBlock) headstate.getBlock()).performBonemeal(level, random, headpos, headstate);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BOTTOM);
	}
}