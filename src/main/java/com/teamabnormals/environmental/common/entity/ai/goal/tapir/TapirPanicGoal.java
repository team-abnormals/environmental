package com.teamabnormals.environmental.common.entity.ai.goal.tapir;

import com.teamabnormals.environmental.common.entity.animal.tapir.Tapir;
import net.minecraft.world.entity.ai.goal.PanicGoal;

public class TapirPanicGoal extends PanicGoal {
    private final Tapir tapir;

    public TapirPanicGoal(Tapir tapir) {
        super(tapir, 1.1D);
        this.tapir = tapir;
    }

    @Override
    public void start() {
        super.start();
        this.tapir.stopTracking();
        this.tapir.setRunning(true);
    }

    @Override
    public void stop() {
        super.stop();
        this.tapir.setRunning(false);
    }
}