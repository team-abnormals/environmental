package com.minecraftabnormals.environmental.common.entity.goals;

import com.minecraftabnormals.environmental.common.block.SlabfishEffigyBlock;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.EnumSet;
import java.util.stream.Stream;

public class SlabbyFindEffigyGoal extends Goal {
    private final SlabfishEntity slabfish;
    private BlockPos effigyPos;

    public SlabbyFindEffigyGoal(SlabfishEntity slabfish) {
        this.slabfish = slabfish;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean shouldExecute() {
        if (this.slabfish.getEffigy() != null)
            return false;
        if (!this.slabfish.hasBackpack())
            return false;
        if (this.slabfish.isSitting())
            return false;

        AxisAlignedBB aabb = this.slabfish.getBoundingBox().grow(12.0D, 4.0D, 12.0D);
        Stream<BlockPos> blocks = BlockPos.getAllInBox(aabb);

        this.slabfish.getNavigator().clearPath();

        blocks.filter(pos -> this.slabfish.world.getBlockState(pos).getBlock() instanceof SlabfishEffigyBlock).forEach(pos -> {
            double distance = this.slabfish.getDistanceSq(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            double closest = this.effigyPos == null ? Double.MAX_VALUE : this.slabfish.getDistanceSq(this.effigyPos.getX() + 0.5, this.effigyPos.getY(), this.effigyPos.getZ() + 0.5);
            if (distance < closest) {
                if (this.slabfish.getNavigator().getPathToPos(this.effigyPos, 0) == null)
                    return;

                this.effigyPos = pos.toImmutable();
            }
        });

        return this.effigyPos != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.slabfish.hasBackpack() && !this.slabfish.isSitting() && this.slabfish.getEffigy() == null && this.effigyPos != null;
    }

    @Override
    public void resetTask() {
        this.effigyPos = null;
        this.slabfish.getNavigator().clearPath();
    }

    @Override
    public void tick() {
        Path path = this.slabfish.getNavigator().getPathToPos(this.effigyPos, 0);
        if (path != null) {
            this.slabfish.getNavigator().setPath(path, 1.1D);
            if (this.slabfish.getDistanceSq(this.effigyPos.getX(), this.effigyPos.getY(), this.effigyPos.getZ()) < 2) {
                this.slabfish.setEffigy(this.effigyPos);
                this.slabfish.world.setEntityState(this.slabfish, (byte) 8);
            }
        } else this.effigyPos = null;
    }
}
