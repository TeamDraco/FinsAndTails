package blueportal.finsandstails.common.entities.ai.goals;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class WaterJumpGoal extends JumpGoal {
   private static final int[] STEPS_TO_CHECK = new int[]{0, 1, 4, 5, 6, 7};
   private final PathfinderMob entity;
   private final int interval;
   private boolean breached;

   public WaterJumpGoal(PathfinderMob entity, int p_25169_) {
      this.entity = entity;
      this.interval = p_25169_;
   }

   public boolean canUse() {
      if (this.entity.getRandom().nextInt(this.interval) != 0) {
         return false;
      } else {
         Direction direction = this.entity.getMotionDirection();
         int i = direction.getStepX();
         int j = direction.getStepZ();
         BlockPos blockpos = this.entity.blockPosition();

         for(int k : STEPS_TO_CHECK) {
            if (!this.waterIsClear(blockpos, i, j, k) || !this.surfaceIsClear(blockpos, i, j, k)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean waterIsClear(BlockPos p_25173_, int p_25174_, int p_25175_, int p_25176_) {
      BlockPos blockpos = p_25173_.offset(p_25174_ * p_25176_, 0, p_25175_ * p_25176_);
      return this.entity.level().getFluidState(blockpos).is(FluidTags.WATER) && !this.entity.level().getBlockState(blockpos).blocksMotion();
   }

   private boolean surfaceIsClear(BlockPos p_25179_, int p_25180_, int p_25181_, int p_25182_) {
      return this.entity.level().getBlockState(p_25179_.offset(p_25180_ * p_25182_, 1, p_25181_ * p_25182_)).isAir() && this.entity.level().getBlockState(p_25179_.offset(p_25180_ * p_25182_, 2, p_25181_ * p_25182_)).isAir();
   }

   public boolean canContinueToUse() {
      double d0 = this.entity.getDeltaMovement().y;
      return (!(d0 * d0 < (double)0.03F) || this.entity.getXRot() == 0.0F || !(Math.abs(this.entity.getXRot()) < 10.0F) || !this.entity.isInWater()) && !this.entity.onGround();
   }

   public boolean isInterruptable() {
      return false;
   }

   public void start() {
      Direction direction = this.entity.getMotionDirection();
      this.entity.setDeltaMovement(this.entity.getDeltaMovement().add((double)direction.getStepX() * 0.6D, 0.7D, (double)direction.getStepZ() * 0.6D));
      this.entity.getNavigation().stop();
   }

   public void stop() {
      this.entity.setXRot(0.0F);
   }

   public void tick() {
      boolean flag = this.breached;
      if (!flag) {
         FluidState fluidstate = this.entity.level().getFluidState(this.entity.blockPosition());
         this.breached = fluidstate.is(FluidTags.WATER);
      }

      if (this.breached && !flag) {
         this.entity.playSound(SoundEvents.DOLPHIN_JUMP, 1.0F, 1.0F);
      }

      Vec3 vec3 = this.entity.getDeltaMovement();
      if (vec3.y * vec3.y < (double)0.03F && this.entity.getXRot() != 0.0F) {
         this.entity.setXRot(Mth.rotLerp(this.entity.getXRot(), 0.0F, 0.2F));
      } else if (vec3.length() > (double)1.0E-5F) {
         double d0 = vec3.horizontalDistance();
         double d1 = Math.atan2(-vec3.y, d0) * (double)(180F / (float)Math.PI);
         this.entity.setXRot((float)d1);
      }

   }
}