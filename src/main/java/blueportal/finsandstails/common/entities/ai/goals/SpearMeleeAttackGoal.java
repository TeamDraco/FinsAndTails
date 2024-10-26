package blueportal.finsandstails.common.entities.ai.goals;

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.phys.Vec3;
import blueportal.finsandstails.common.entities.TealArrowfishEntity;

public class SpearMeleeAttackGoal extends MeleeAttackGoal {
    protected final TealArrowfishEntity fish;


    public SpearMeleeAttackGoal(TealArrowfishEntity mob, double speedMod, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedMod, followingTargetEvenIfNotSeen);
        this.fish = mob;
    }

    @Override
    public boolean canUse() {
        return fish.getTarget() != null && fish.getTarget().isAlive() && --fish.killCooldown <= 0 && !fish.isFollower() && super.canUse();
    }

    @Override
    public void tick() {
        super.tick();

        if (fish.getTarget() == null || !fish.getTarget().isAlive()) {
            stop();
        }
        else {
            Vec3 pos = fish.getTarget().position();

            double speedX = pos.x - fish.position().x;
            double speedY = pos.y - fish.position().y;
            double speedZ = pos.z - fish.position().z;
            double x = speedX > 0.0D ? Math.min(0.25D, speedX) : Math.max(-0.25D, speedX);
            double y = speedY > 0.0D ? Math.min(0.25D, speedY) : Math.max(-0.25D, speedY);
            double z = speedZ > 0.0D ? Math.min(0.25D, speedZ) : Math.max(-0.25D, speedZ);
            fish.setDeltaMovement(x, y, z);
            fish.lookAt(fish.getTarget(), 90.0F, 90.0F);
        }
    }
}
