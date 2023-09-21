package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import com.teamabnormals.environmental.common.entity.animal.slabfish.Slabfish;
import com.teamabnormals.environmental.common.entity.animal.slabfish.SlabfishOverlay;
import com.teamabnormals.environmental.core.other.EnvironmentalDataProcessors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MudBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MudBlock.class)
public class MudBlockMixin extends Block {

	public MudBlockMixin(Properties properties) {
		super(properties);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof Pig pig) {
			IDataManager dataManager = (IDataManager) pig;
			dataManager.setValue(EnvironmentalDataProcessors.IS_MUDDY, true);
			dataManager.setValue(EnvironmentalDataProcessors.MUD_DRYING_TIME, dataManager.getValue(EnvironmentalDataProcessors.MUD_DRYING_TIME) + 300);
		}

		super.entityInside(state, level, pos, entity);
	}
}
