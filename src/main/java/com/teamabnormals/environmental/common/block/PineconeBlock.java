package com.teamabnormals.environmental.common.block;

import com.google.common.collect.Lists;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public class PineconeBlock extends FallingBlock implements BonemealableBlock {

	public PineconeBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (canFall(level, pos) && pos.getY() >= level.getMinBuildHeight()) {
			FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, pos, state);
			this.falling(fallingblockentity);
		}
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.is(Items.HONEYCOMB)) {
			if (player instanceof ServerPlayer) {
				CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, stack);
			}

			if (!player.getAbilities().instabuild) {
				stack.shrink(1);
			}
			BlockState newState = EnvironmentalBlocks.WAXED_PINECONE.get().defaultBlockState();
			level.setBlock(pos, newState, 11);
			level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState));
			level.levelEvent(player, 3003, pos, 0);
			return InteractionResult.sidedSuccess(level.isClientSide);
		}

		return super.use(state, level, pos, player, hand, result);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (random.nextInt(3) != 0) {
			BlockState sapling = EnvironmentalBlocks.PINE_SAPLING.get().defaultBlockState();
			List<BlockPos> list = Lists.newArrayList();
			addPossibleSaplingPositionsFromNeighbors(4, 4, 4, list, level, pos.offset(-4, -4, -4), new BlockPos.MutableBlockPos(), new boolean[9][9][9]);

			if (!list.isEmpty()) {
				this.spawnBoneMealParticles(level, this.defaultBlockState(), pos);
			}
			for (int i = 0; i < 12; i++) {
				if (list.isEmpty())
					break;

				BlockPos blockpos = list.remove(random.nextInt(list.size()));
				if (level.isAreaLoaded(blockpos, 1) && level.getMaxLocalRawBrightness(blockpos.above()) >= 9 && hasSpaceForTree(blockpos, level, false)) {
					level.setBlockAndUpdate(blockpos, sapling);
					this.spawnBoneMealParticles(level, sapling, blockpos);
					return;
				}
			}
		}
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
	}

	public static boolean canFall(ServerLevel level, BlockPos pos) {
		return !supportedFromAbove(level, pos) && isFree(level.getBlockState(pos.below()));
	}

	public static boolean supportedFromAbove(ServerLevel level, BlockPos pos) {
		BlockState blockstate = level.getBlockState(pos.above());
		return Block.isFaceFull(blockstate.getCollisionShape(level, pos.above()), Direction.DOWN) && !(blockstate.getBlock() instanceof FallingBlock);
	}

	@Override
	public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level p_220878_, RandomSource random, BlockPos p_220880_, BlockState p_220881_) {
		return random.nextInt(3) != 0;
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		BlockState sapling = EnvironmentalBlocks.PINE_SAPLING.get().defaultBlockState();
		List<BlockPos> list = Lists.newArrayList();
		addPossibleSaplingPositionsFromNeighbors(4, 4, 4, list, level, pos.offset(-4, -4, -4), new BlockPos.MutableBlockPos(), new boolean[9][9][9]);

		for (int i = 0; i < 12; i++) {
			if (list.isEmpty())
				break;

			BlockPos blockpos = list.remove(random.nextInt(list.size()));
			if (level.isAreaLoaded(blockpos, 1) && hasSpaceForTree(blockpos, level, true)) {
				level.setBlockAndUpdate(blockpos, sapling);
				this.spawnBoneMealParticles(level, sapling, blockpos);
				return;
			}
		}
	}

	private void spawnBoneMealParticles(ServerLevel level, BlockState state, BlockPos pos) {
		int amount = 15;
		double d0 = 0.5D;
		double d1;
		if (state.isSolidRender(level, pos)) {
			pos = pos.above();
			amount *= 3;
			d0 = 3.0D;
			d1 = 1.0D;
		} else {
			d1 = state.getShape(level, pos).max(Direction.Axis.Y);
		}

		level.sendParticles(ParticleTypes.HAPPY_VILLAGER, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, 0, 0.0D, 0.0D, 0.0D, 0.0D);
		RandomSource random = level.getRandom();

		for (int i = 0; i < amount; ++i) {
			double d2 = random.nextGaussian() * 0.02D;
			double d3 = random.nextGaussian() * 0.02D;
			double d4 = random.nextGaussian() * 0.02D;
			double d5 = 0.5D - d0;
			double d6 = (double) pos.getX() + d5 + random.nextDouble() * d0 * 2.0D;
			double d7 = (double) pos.getY() + random.nextDouble() * d1;
			double d8 = (double) pos.getZ() + d5 + random.nextDouble() * d0 * 2.0D;
			level.sendParticles(ParticleTypes.HAPPY_VILLAGER, d6, d7, d8, 0, d2, d3, d4, 0.0D);
		}
	}

	private static void addPossibleSaplingPositions(int x, int y, int z, List<BlockPos> list, ServerLevel level, BlockPos origin, BlockPos.MutableBlockPos mutablepos, boolean[][][] foundpositions) {
		if (x < 0 || x >= 9 || y < 0 || y >= 9 || z < 0 || z >= 9)
			return;
		else if (foundpositions[x][y][z])
			return;

		mutablepos.setWithOffset(origin, x, y, z);
		foundpositions[x][y][z] = true;

		if (level.getBlockState(mutablepos).is(BlockTags.DIRT)) {
			if (level.isEmptyBlock(mutablepos.move(Direction.UP)))
				list.add(mutablepos.immutable());

			addPossibleSaplingPositionsFromNeighbors(x, y, z, list, level, origin, mutablepos, foundpositions);
		}
	}

	private static void addPossibleSaplingPositionsFromNeighbors(int x, int y, int z, List<BlockPos> list, ServerLevel level, BlockPos origin, BlockPos.MutableBlockPos mutablepos, boolean[][][] foundpositions) {
		addPossibleSaplingPositions(x - 1, y, z, list, level, origin, mutablepos, foundpositions);
		addPossibleSaplingPositions(x + 1, y, z, list, level, origin, mutablepos, foundpositions);
		addPossibleSaplingPositions(x, y - 1, z, list, level, origin, mutablepos, foundpositions);
		addPossibleSaplingPositions(x, y + 1, z, list, level, origin, mutablepos, foundpositions);
		addPossibleSaplingPositions(x, y, z - 1, list, level, origin, mutablepos, foundpositions);
		addPossibleSaplingPositions(x, y, z + 1, list, level, origin, mutablepos, foundpositions);
	}

	private static boolean hasSpaceForTree(BlockPos pos, ServerLevel level, boolean bonemeal) {
		BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();
		for (int y = -2; y <= 3; y++) {
			for (int x = -3; x <= 3; x++) {
				for (int z = -3; z <= 3; z++) {
					mutablepos.setWithOffset(pos, x, y, z);
					if ((y > 0 && !TreeFeature.validTreePos(level, mutablepos) && !bonemeal) || level.getBlockState(mutablepos).is(BlockTags.SAPLINGS))
						return false;
				}
			}
		}
		return true;
	}
}