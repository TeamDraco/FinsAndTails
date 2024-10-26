package blueportal.finsandstails.common.entities.ai.goals;

import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.Goal;
import blueportal.finsandstails.common.entities.ai.base.SchoolingTamableAnimal;

import java.util.List;
import java.util.function.Predicate;

public class TamableFollowLeaderGoal extends Goal {
   private final SchoolingTamableAnimal mob;
   private int timeToRecalcPath;
   private int nextStartTick;

   public TamableFollowLeaderGoal(SchoolingTamableAnimal p_25249_) {
      this.mob = p_25249_;
      this.nextStartTick = this.nextStartTick(p_25249_);
   }

   protected int nextStartTick(SchoolingTamableAnimal p_25252_) {
      return reducedTickDelay(200 + p_25252_.getRandom().nextInt(200) % 20) * 4;
   }

   public boolean canUse() {
      if (this.mob.hasFollowers()) {
         return false;
      } else if (this.mob.isFollower()) {
         return true;
      } else if (this.nextStartTick > 0) {
         --this.nextStartTick;
         return false;
      } else {
         this.nextStartTick = this.nextStartTick(this.mob);

         Predicate<SchoolingTamableAnimal> predicate = (schoolingAnimal) -> schoolingAnimal.canBeFollowed() || !schoolingAnimal.isFollower();
         List<? extends SchoolingTamableAnimal> list = this.mob.level().getEntitiesOfClass(this.mob.getClass(), this.mob.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), predicate);
         SchoolingTamableAnimal schoolingAnimal = DataFixUtils.orElse(list.stream().filter(SchoolingTamableAnimal::canBeFollowed).findAny(), this.mob);
         schoolingAnimal.addFollowers(list.stream().filter((schoolingAnimal1) -> !schoolingAnimal1.isFollower()));
         return this.mob.isFollower();
      }
   }

   public boolean canContinueToUse() {
      return this.mob.isFollower() && this.mob.inRangeOfLeader();
   }

   public void start() {
      this.timeToRecalcPath = 0;
   }

   public void stop() {
      this.mob.stopFollowing();
   }

   public void tick() {
      if (--this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = this.adjustedTickDelay(40);
         this.mob.pathToLeader();
      }
   }
}