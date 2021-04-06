package com.minecraftabnormals.environmental.common.entity.goals;

import com.minecraftabnormals.environmental.common.block.SlabfishEffigyBlock;
import com.minecraftabnormals.environmental.common.entity.SlabfishEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.EnumSet;

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
        Iterable<BlockPos> blocks = BlockPos.getAllInBoxMutable(new BlockPos(MathHelper.floor(aabb.minX), MathHelper.floor(aabb.minY), MathHelper.floor(aabb.minZ)), new BlockPos(MathHelper.floor(aabb.maxX), MathHelper.floor(aabb.maxY), MathHelper.floor(aabb.maxZ)));

        this.slabfish.getNavigator().clearPath();

        BlockPos effigyPos = null;
        double closest = Double.MAX_VALUE;
        for (BlockPos pos : blocks) {
            if (!(this.slabfish.world.getBlockState(pos).getBlock() instanceof SlabfishEffigyBlock))
                continue;
            double distance = this.slabfish.getDistanceSq(pos.getX(), pos.getY(), pos.getZ());
            if (distance < closest) {
                closest = distance;
                effigyPos = pos.toImmutable();
            }
        }

        if (effigyPos == null)
            return false;
        else {
            this.effigyPos = effigyPos;
            return true;
        }
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
