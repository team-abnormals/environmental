package com.teamabnormals.environmental.common.entity.ai.goal;

import com.google.common.collect.Lists;
import com.teamabnormals.environmental.common.entity.animal.PineconeGolem;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.EnumSet;
import java.util.List;

public class PineconeGolemPlantSaplingGoal extends Goal {
    private final PineconeGolem golem;
    private final double moveSpeed;
    private int nextStartTicks;
    private int tryTicks;
    private int maxStayTicks;
    private boolean canPlant;
    private BlockPos targetPos = BlockPos.ZERO;
    private ItemStack heldItem;
    private BlockState saplingState;

    public PineconeGolemPlantSaplingGoal(PineconeGolem golem, double speed) {
        this.golem = golem;
        this.moveSpeed = speed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.nextStartTicks > 0) {
            --this.nextStartTicks;
            return false;
        } else {
            this.nextStartTicks = reducedTickDelay(20);
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.golem.level, this.golem))
                return false;

            ItemStack itemstack = this.golem.getMainHandItem();

            if (itemstack.is(ItemTags.SAPLINGS) && itemstack.getItem() instanceof BlockItem blockitem) {
                BlockState blockstate = blockitem.getBlock().defaultBlockState();
                BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();

                for (int y = 0; y >= -1; y = y < 0 ? -y : -y - 1) {
                    List<BlockPos> list = Lists.newArrayList();
                    for (int x = -3; x <= 3; x++) {
                        for (int z = -3; z <= 3; z++) {
                            list.add(new BlockPos(mutablepos.setWithOffset(this.golem.blockPosition(), x, y, z)));
                        }
                    }

                    while (!list.isEmpty()) {
                        BlockPos blockpos = list.remove(this.golem.getRandom().nextInt(list.size()));
                        if (this.isValidTarget(blockpos, blockstate) && this.golem.isWithinRestriction(blockpos) && this.hasSpaceForTree(blockpos) && this.moveMobToBlock(blockpos)) {
                            this.targetPos = blockpos;
                            this.heldItem = itemstack;
                            this.saplingState = blockstate;
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.tryTicks < -this.maxStayTicks || this.tryTicks > 1200) {
            return false;
        } else if (this.golem.getMainHandItem() != this.heldItem) {
            return false;
        } else {
            return this.canPlant && this.isValidTarget(this.targetPos, this.saplingState);
        }
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.tryTicks = 0;
        this.maxStayTicks = this.golem.getRandom().nextInt(this.golem.getRandom().nextInt(1200) + 1200) + 1200;
        this.canPlant = true;
    }

    @Override
    public void stop() {
        this.heldItem = null;
        this.saplingState = null;
        this.golem.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (!this.targetPos.closerToCenterThan(this.golem.position(), 1.25D)) {
            ++this.tryTicks;
            if (this.tryTicks % 40 == 0) {
                if (!this.moveMobToBlock(this.targetPos))
                    this.canPlant = false;
            }
        } else {
            if (this.hasSpaceForTree(this.targetPos) && !ForgeEventFactory.onBlockPlace(this.golem, BlockSnapshot.create(this.golem.level.dimension(), this.golem.level, this.targetPos.below()), net.minecraft.core.Direction.UP)) {
                this.golem.level.setBlockAndUpdate(this.targetPos, this.saplingState);
                SoundType soundtype = this.saplingState.getSoundType(this.golem.level, this.targetPos, this.golem);
                this.golem.level.playSound(null, this.targetPos, this.saplingState.getSoundType(this.golem.level, this.targetPos, this.golem).getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                this.golem.level.gameEvent(GameEvent.BLOCK_PLACE, this.targetPos, GameEvent.Context.of(this.golem, this.saplingState));
                this.golem.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            }

            this.canPlant = false;
        }
    }

    private boolean isValidTarget(BlockPos pos, BlockState state) {
        return this.golem.level.isEmptyBlock(pos) && state.getBlock().canSurvive(state, this.golem.level, pos);
    }

    private boolean hasSpaceForTree(BlockPos pos) {
        BlockPos.MutableBlockPos mutablepos = new BlockPos.MutableBlockPos();
        for (int y = -2; y <= 6; y++) {
            for (int x = -3; x <= 3; x++) {
                for (int z = -3; z <= 3; z++) {
                    mutablepos.setWithOffset(pos, x, y, z);
                    if ((y > 0 && !TreeFeature.validTreePos(this.golem.level, mutablepos)) || this.golem.level.getBlockState(mutablepos).is(BlockTags.SAPLINGS))
                        return false;
                }
            }
        }
        return true;
    }

    private boolean moveMobToBlock(BlockPos pos) {
        Path path = this.golem.getNavigation().createPath(pos.getX(), pos.getY(), pos.getZ(), 0);
        if (path != null && path.canReach()) {
            this.golem.getNavigation().moveTo(path, this.moveSpeed);
            return true;
        } else {
            return false;
        }
    }
}