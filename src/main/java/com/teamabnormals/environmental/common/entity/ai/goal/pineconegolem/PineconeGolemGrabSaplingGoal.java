package com.teamabnormals.environmental.common.entity.ai.goal.pineconegolem;

import com.teamabnormals.environmental.common.entity.animal.PineconeGolem;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class PineconeGolemGrabSaplingGoal extends Goal {
    private final PineconeGolem golem;
    private final double moveSpeed;
    private final Predicate<ItemEntity> itemPredicate;
    private ItemEntity itemEntity;
    private int delayCounter;

    public PineconeGolemGrabSaplingGoal(PineconeGolem golem, double speed) {
        this.golem = golem;
        this.moveSpeed = speed;
        this.itemPredicate = (entity) -> golem.canHoldItem(entity.getItem());
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (!this.golem.getMainHandItem().isEmpty()) {
            return false;
        } else {
            List<ItemEntity> list = this.golem.getLevel().getEntitiesOfClass(ItemEntity.class, this.golem.getBoundingBox().inflate(12.0D, 4.0D, 12.0D), this.itemPredicate);
            ItemEntity entity = null;
            double d0 = Double.MAX_VALUE;

            for (ItemEntity entity1 : list) {
                double d1 = this.golem.distanceToSqr(entity1);
                if (d1 < d0) {
                    d0 = d1;
                    entity = entity1;
                }
            }

            if (entity == null) {
                return false;
            } else {
                this.itemEntity = entity;
                return true;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.golem.getMainHandItem().isEmpty() && this.itemEntity != null && this.itemEntity.isAlive();
    }

    @Override
    public void start() {
        this.delayCounter = 0;
    }

    @Override
    public void stop() {
        this.itemEntity = null;
        this.golem.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = this.adjustedTickDelay(20);
            Path path = this.golem.getNavigation().createPath(this.itemEntity, 0);
            if (path != null) {
                this.golem.getNavigation().moveTo(path, this.moveSpeed);
            }
        }
    }
}