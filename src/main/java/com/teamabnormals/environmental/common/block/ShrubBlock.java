package com.teamabnormals.environmental.common.block;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.IShearable;

public class ShrubBlock extends BushBlock implements BonemealableBlock, IShearable {
	public static final MapCodec<ShrubBlock> CODEC = simpleCodec(ShrubBlock::new);
	public static final IntegerProperty SIZE = IntegerProperty.create("size", 1, 4);
	public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
	public static final BooleanProperty CENTERED = BooleanProperty.create("centered");
	private static final VoxelShape SMALL_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);
	private static final VoxelShape SHORT_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 11.0, 16.0);

	public ShrubBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(SIZE, 1).setValue(PART, Part.BOTTOM).setValue(CENTERED, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(SIZE, PART, CENTERED);
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return switch (state.getValue(SIZE)) {
			case 1 -> SMALL_SHAPE;
			case 2 -> state.getValue(PART) == Part.BOTTOM ? Shapes.block() : SHORT_SHAPE;
			default -> Shapes.block();
		};
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return state.is(BlockTags.SAND) || super.mayPlaceOn(state, level, pos);
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		if (state.getValue(SIZE) > 1) {
			switch (state.getValue(PART)) {
				case BOTTOM -> {
					if (!this.isNotMalformedPart(level, pos.above(), state))
						return false;
				}
				case MIDDLE -> {
					return this.isNotMalformedPart(level, pos.below(), state) && this.isNotMalformedPart(level, pos.above(), state);
				}
				case TOP -> {
					return this.isNotMalformedPart(level, pos.below(), state);
				}
			}
		}
		return super.canSurvive(state, level, pos);
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		switch (state.getValue(SIZE)) {
			case 1 -> {
				BlockPos above = pos.above();
				if (level.isEmptyBlock(above)) {
					state = state.setValue(SIZE, 2);
					level.setBlock(pos, state, 2);
					level.setBlock(above, state.setValue(PART, Part.TOP), 2);
				}
			}
			case 2 -> {
				state = state.setValue(SIZE, 3);
				level.setBlock(pos, state, 2);
				if (state.getValue(PART) == Part.BOTTOM) {
					this.setIfReplaceable(level, pos.above(), state.setValue(PART, Part.TOP));
				} else {
					this.setIfReplaceable(level, pos.below(), state.setValue(PART, Part.BOTTOM));
				}
			}
			case 3 -> {
				if (state.getValue(PART) == Part.BOTTOM) {
					BlockPos newTopPos = pos.above(2);
					if (level.isEmptyBlock(newTopPos)) {
						state = state.setValue(SIZE, 4);
						BlockPos above = pos.above();
						if (isWalledIn(level, above) || isWalledIn(level, newTopPos))
							state = state.setValue(CENTERED, true);
						level.setBlock(pos, state, 2);
						this.setIfReplaceable(level, above, state.setValue(PART, Part.MIDDLE));
						level.setBlock(newTopPos, state.setValue(PART, Part.TOP), 2);
					}
				} else {
					BlockPos above = pos.above();
					if (level.isEmptyBlock(above)) {
						state = state.setValue(SIZE, 4);
						if (isWalledIn(level, pos) || isWalledIn(level, above))
							state = state.setValue(CENTERED, true);
						level.setBlock(above, state.setValue(PART, Part.TOP), 2);
						level.setBlock(pos, state.setValue(PART, Part.MIDDLE), 2);
						this.setIfReplaceable(level, pos.below(), state.setValue(PART, Part.BOTTOM));
					}
				}
			}
			case 4 -> {
				int y = pos.getY();
				var part = state.getValue(PART);
				if (part != Part.BOTTOM)
					y -= (state.getValue(SIZE) > 3 && part == Part.TOP ? 2 : 1);
				BlockPos.MutableBlockPos spreadingPos = new BlockPos.MutableBlockPos();
				for (int layer = -1; layer <= 1; layer++) {
					spreadingPos.setY(y + layer);
					for (int x = -3; x <= 3; x++) {
						spreadingPos.setX(pos.getX() + x);
						for (int z = -3; z <= 3; z++) {
							spreadingPos.setZ(pos.getZ() + z);
							int distanceSquared = x * x + z * z;
							if (distanceSquared > 9) continue;
							if (distanceSquared > 1 && ((distanceSquared == 8 && random.nextBoolean()) || random.nextInt(4) == 0))
								continue;
							int tempY = spreadingPos.getY();
							spreadingPos.setY(tempY - 1);
							if (!this.mayPlaceOn(level.getBlockState(spreadingPos), level, spreadingPos)) {
								spreadingPos.setY(tempY);
								continue;
							}
							spreadingPos.setY(tempY);
							BlockState blockState = level.getBlockState(spreadingPos);
							if (!blockState.isAir() && !(blockState.is(this) && blockState.getValue(SIZE) == 1)) continue;
							BlockPos above = spreadingPos.above();
							if (level.isEmptyBlock(above) && distanceSquared <= 2 && random.nextBoolean()) {
								BlockState medium = this.defaultBlockState().setValue(SIZE, 2);
								level.setBlock(spreadingPos, medium.setValue(PART, Part.BOTTOM), 2);
								level.setBlock(above, medium.setValue(PART, Part.TOP), 2);
							} else {
								level.setBlock(spreadingPos, this.defaultBlockState(), 2);
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected MapCodec<? extends BushBlock> codec() {
		return CODEC;
	}

	private boolean isNotMalformedPart(LevelReader level, BlockPos pos, BlockState state) {
		BlockState stateAtPos = level.getBlockState(pos);
		return stateAtPos.is(this) && state.getValue(SIZE).equals(stateAtPos.getValue(SIZE));
	}

	private void setIfReplaceable(ServerLevel level, BlockPos pos, BlockState state) {
		BlockState stateToReplace = level.getBlockState(pos);
		if (!stateToReplace.isAir() && !stateToReplace.is(this)) return;
		level.setBlock(pos, state, 2);
	}

	public static boolean isWalledIn(BlockGetter getter, BlockPos pos) {
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			BlockState state = getter.getBlockState(pos.relative(direction));
			if (!state.isAir() && !state.is(EnvironmentalBlocks.SHRUB.get()) && !state.is(BlockTags.REPLACEABLE_BY_TREES) && !state.is(BlockTags.FLOWERS))
				return true;
		}
		return false;
	}

	public enum Part implements StringRepresentable {
		BOTTOM("bottom"),
		MIDDLE("middle"),
		TOP("top");

		private final String name;

		Part(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
