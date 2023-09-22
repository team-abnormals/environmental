package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.blueprint.common.world.storage.tracking.IDataManager;
import com.teamabnormals.environmental.core.other.EnvironmentalDataProcessors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockMixin implements IForgeBlock {

	@Override
	public float getFriction(BlockState state, LevelReader level, BlockPos pos, Entity entity) {
		if (entity instanceof Pig) {
			IDataManager data = (IDataManager) entity;
			if (data.getValue(EnvironmentalDataProcessors.IS_MUDDY) && data.getValue(EnvironmentalDataProcessors.MUD_DRYING_TIME) > 0) {
				return 0.999F;
			}
		}

		return ((Block) (Object) this).getFriction();
	}

}