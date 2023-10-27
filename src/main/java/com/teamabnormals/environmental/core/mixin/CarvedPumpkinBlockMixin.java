package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.common.entity.animal.PineconeGolem;
import com.teamabnormals.environmental.core.registry.EnvironmentalBlocks;
import com.teamabnormals.environmental.core.registry.EnvironmentalEntityTypes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CarvedPumpkinBlock.class)
public final class CarvedPumpkinBlockMixin {

    @Inject(method = "canSpawnGolem", at = @At("RETURN"), cancellable = true)
    private void canSpawnGolem(LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (level.getBlockState(pos.below()).getBlock() == EnvironmentalBlocks.PINECONE.get())
            cir.setReturnValue(true);
    }

    @Inject(method = "trySpawnGolem", at = @At("HEAD"), cancellable = true)
    private void trySpawnGolem(Level level, BlockPos pos, CallbackInfo ci) {
        BlockPos belowpos = pos.below();
        BlockState belowstate = level.getBlockState(pos.below());
        if (belowstate.getBlock() == EnvironmentalBlocks.PINECONE.get()) {
            BlockState state = level.getBlockState(pos);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            level.setBlock(belowpos, Blocks.AIR.defaultBlockState(), 2);
            level.levelEvent(2001, pos, Block.getId(state));
            level.levelEvent(2001, belowpos, Block.getId(belowstate));

            PineconeGolem pineconegolem = EnvironmentalEntityTypes.PINECONE_GOLEM.get().create(level);

            float yRot = state.getValue(CarvedPumpkinBlock.FACING).toYRot();
            pineconegolem.moveTo((double) belowpos.getX() + 0.5D, (double) belowpos.getY() + 0.05D, (double) belowpos.getZ() + 0.5D);
            pineconegolem.yHeadRot = yRot;
            pineconegolem.yBodyRot = yRot;
            level.addFreshEntity(pineconegolem);

            for (ServerPlayer serverplayer : level.getEntitiesOfClass(ServerPlayer.class, pineconegolem.getBoundingBox().inflate(5.0D))) {
                CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayer, pineconegolem);
            }

            level.blockUpdated(pos, Blocks.AIR);
            level.blockUpdated(belowpos, Blocks.AIR);

            ci.cancel();
        }
    }
}