package com.teamabnormals.environmental.core.mixin;

import com.teamabnormals.environmental.common.entity.animal.MuddyPig;
import com.teamabnormals.environmental.common.entity.animal.Tapir;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.MudBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

	@Inject(method = "entityInside", at = @At("HEAD"))
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci) {
		BlockBehaviour block = (BlockBehaviour) (Object) this;

		if (block instanceof MudBlock && entity instanceof Pig pig && MuddyPig.enabled()) {
			if (!MuddyPig.isMuddy(pig)) {
				MuddyPig.setMuddy(pig, true);
				MuddyPig.updateDryingTime(pig, 200);
			} else {
				MuddyPig.updateDryingTime(pig, 20);
			}
		}

		if (block instanceof LeavesBlock && entity instanceof Tapir) {
			entity.resetFallDistance();
			Vec3 vec3 = entity.getDeltaMovement();
			entity.setDeltaMovement(vec3.x * 0.8D, Math.max(vec3.y, -0.08D), vec3.z * 0.8D);
		}
	}

	@Inject(method = "getCollisionShape", at = @At("RETURN"), cancellable = true)
	public void getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
		BlockBehaviour block = (BlockBehaviour) (Object) this;
		if (block instanceof LeavesBlock && context instanceof EntityCollisionContext entityContext) {
			if (entityContext.getEntity() instanceof Tapir) {
				cir.setReturnValue(Shapes.empty());
			}
		}
	}
}
