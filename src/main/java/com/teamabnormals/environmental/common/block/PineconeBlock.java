package com.teamabnormals.environmental.common.block;

import com.google.common.collect.Lists;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;

import java.util.List;

public class PineconeBlock extends FallingBlock {

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
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(3) == 0)
            return;

        List<BlockPos> list = Lists.newArrayList();
        addPossibleSaplingPositionsFromNeighbors(4, 4, 4, list, level, pos.offset(-4, -4, -4), new BlockPos.MutableBlockPos(), new boolean[9][9][9]);

        for (int i = 0; i < 12; i++) {
            if (list.isEmpty())
                break;

            BlockPos blockpos = list.remove(random.nextInt(list.size()));
            if (level.isAreaLoaded(blockpos, 1) && level.getMaxLocalRawBrightness(blockpos.above()) >= 9 && hasSpaceForTree(blockpos, level)) {
                level.setBlock(blockpos, EnvironmentalBlocks.PINE_SAPLING.get().defaultBlockState(), 2);
                break;
            }
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

    private static boolean hasSpaceForTree(BlockPos pos, ServerLevel level) {
        BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();
        for (int y = -2; y <= 3; y++) {
            for (int x = -3; x <= 3; x++) {
                for (int z = -3; z <= 3; z++) {
                    mutablepos.setWithOffset(pos, x, y, z);
                    if ((y > 0 && !TreeFeature.validTreePos(level, mutablepos)) || level.getBlockState(mutablepos).is(BlockTags.SAPLINGS))
                        return false;
                }
            }
        }
        return true;
    }

    public static boolean canFall(ServerLevel level, BlockPos pos) {
        return !supportedFromAbove(level, pos) && isFree(level.getBlockState(pos.below()));
    }

    public static boolean supportedFromAbove(ServerLevel level, BlockPos pos) {
        BlockState blockstate = level.getBlockState(pos.above());
        return Block.isFaceFull(blockstate.getCollisionShape(level, pos.above()), Direction.DOWN) && !(blockstate.getBlock() instanceof FallingBlock);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
    }
}