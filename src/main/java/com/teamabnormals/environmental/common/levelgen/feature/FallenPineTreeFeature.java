package com.teamabnormals.environmental.common.levelgen.feature;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class FallenPineTreeFeature extends Feature<NoneFeatureConfiguration> {
	private static final List<Direction> XY_PLANE = List.of(Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST);
	private static final List<Direction> YZ_PLANE = List.of(Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH);

	private HashMap<BlockPos, Axis> logPositions;
	private Set<BlockPos> leafPositions;

	public FallenPineTreeFeature(Codec<NoneFeatureConfiguration> config) {
		super(config);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		BlockPos origin = context.origin();

		Direction direction = Plane.HORIZONTAL.getRandomDirection(random);
		int length = this.getLength(random);

		int xOffset = direction.getAxis() == Axis.X ? length / 2 : 0;
		int zOffset = direction.getAxis() == Axis.Z ? length / 2 : 0;

		for (int i = 0; i < 6; i++) {
			BlockPos pos = origin.offset(random.nextInt(4) - random.nextInt(4) - xOffset, random.nextInt(2) - random.nextInt(2), random.nextInt(4) - random.nextInt(4) - zOffset);
			if (canGenerateAt(level, pos, direction, length)) {
				this.logPositions = Maps.newHashMap();
				this.leafPositions = Sets.newHashSet();

				this.doPlace(level, random, pos, length, direction);

				for (BlockPos leafPos : this.leafPositions) {
					level.setBlock(leafPos, EnvironmentalBlocks.PINE_LEAVES.get().defaultBlockState(), 19);
				}

				for (BlockPos logPos : this.logPositions.keySet()) {
					level.setBlock(logPos, EnvironmentalBlocks.PINE_LOG.get().defaultBlockState().setValue(BlockStateProperties.AXIS, this.logPositions.get(logPos)), 19);
					if (level.getBlockState(logPos.below()).is(Blocks.GRASS_BLOCK))
						level.setBlock(logPos.below(), Blocks.DIRT.defaultBlockState(), 2);
				}

				BoundingBox.encapsulatingPositions(Iterables.concat(this.logPositions.keySet(), this.leafPositions)).ifPresent((boundingBox) -> {
					DiscreteVoxelShape shape = TreeFeature.updateLeaves(level, boundingBox, this.logPositions.keySet(), Set.of(), Set.of());
					StructureTemplate.updateShapeAtEdge(level, 3, shape, boundingBox.minX(), boundingBox.minY(), boundingBox.minZ());
				});

				return true;
			}
		}

		return false;
	}

	private void doPlace(WorldGenLevel level, RandomSource random, BlockPos origin, int length, Direction direction) {
		List<Direction> plane = direction.getAxis() == Axis.X ? YZ_PLANE : XY_PLANE;

		for (int j = 0; j < length; j++) {
			this.logPositions.put(origin.relative(direction, j), direction.getAxis());
		}

		boolean topleaves = random.nextInt(3) != 0;

		if (topleaves)
			this.createTopLeaves(level, origin.relative(direction, length), direction);

		if (random.nextBoolean())
			this.createRootDirt(level, random, origin.relative(direction.getOpposite()), direction);

		float f = random.nextFloat();
		int topbranches = f > 0.6F ? 3 : f > 0.25F ? 2 : f > 0.05F ? 1 : 0;

		List<Direction> branchdirections = Lists.newArrayList();
		plane.forEach(branchdirections::add);

		int l = topleaves ? length - 4 : length - 2;
		while (l > 0) {
			BlockPos blockpos = origin.relative(direction, l);

			if (topbranches > 0 || random.nextInt(l > 4 ? 2 : 4) == 0) {
				Direction branchdirection = topbranches > 0 ? branchdirections.remove(random.nextInt(branchdirections.size())) : plane.get(random.nextInt(4));
				BlockPos branchpos = blockpos.relative(branchdirection);
				if (TreeFeature.validTreePos(level, branchpos))
					this.logPositions.put(branchpos, branchdirection.getAxis());

				if (topbranches > 0)
					topbranches--;
			}

			if (topbranches == 0)
				l -= 2 + random.nextInt(2);
			else if (topbranches == 1 && random.nextInt(3) == 0)
				l -= 2;
			else
				l--;
		}
	}

	private void createTopLeaves(WorldGenLevel level, BlockPos pos, Direction direction) {
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for (int y = 0; y <= 3; y++) {
			int r = y < 3 ? y : 1;
			for (int x = -r; x <= r; x++) {
				for (int z = r; z >= -r; z--) {
					if (z < 2 && Math.abs(x) + Math.abs(z) <= r) {
						mutable.setWithOffset(pos, rotateToDirection(x, 1 - y, z, direction));
						if (TreeFeature.validTreePos(level, mutable))
							this.leafPositions.add(mutable.immutable());
						else
							break;
					}
				}
			}
		}
	}

	private void createRootDirt(WorldGenLevel level, RandomSource random, BlockPos pos, Direction direction) {
		if (!TreeFeature.validTreePos(level, pos))
			return;

		Axis axis = direction.getAxis();
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for (int h = 1; h >= -1; h--) {
			for (int y = 1; y >= -1; y--) {
				if (h == 0 || y == 0 || random.nextBoolean()) {
					if (axis == Axis.X)
						mutable.setWithOffset(pos, 0, y, h);
					else
						mutable.setWithOffset(pos, h, y, 0);

					if (TreeFeature.validTreePos(level, mutable))
						level.setBlock(mutable, Blocks.ROOTED_DIRT.defaultBlockState(), 2);
					else
						break;
				}
			}
		}
	}

	private static Vec3i rotateToDirection(int x, int y, int z, Direction direction) {
		return new Vec3i(direction.getStepZ() * x + direction.getStepX() * y, z, direction.getStepX() * x + direction.getStepZ() * y);
	}

	private static boolean canGenerateAt(WorldGenLevel level, BlockPos pos, Direction direction, int length) {
		if (!isDirt(level.getBlockState(pos.relative(direction.getOpposite()).below())))
			return false;

		MutableBlockPos mutable = pos.mutable();
		int supportblocks = 0;

		for (int i = 0; i < length; i++) {
			if (TreeFeature.validTreePos(level, mutable)) {
				BlockPos belowpos = mutable.below();
				BlockState belowstate = level.getBlockState(belowpos);
				if (belowstate.isFaceSturdy(level, belowpos, Direction.UP))
					supportblocks++;
			} else {
				return false;
			}

			mutable.move(direction);
		}

		return supportblocks >= length * 0.7F;
	}

	protected int getLength(RandomSource random) {
		return 7 + random.nextInt(6);
	}
}