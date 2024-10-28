package blueportal.finsandstails.common.entities.ai.goals;

import blueportal.finsandstails.common.entities.ai.base.IKillCooldown;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class CooldownMeleeAttackGoal extends MeleeAttackGoal {
    protected final PathfinderMob pathfinder;

    public CooldownMeleeAttackGoal(PathfinderMob mob, double speedMod, boolean followingTargetEvenIfNotSeen) {
        super(mob, speedMod, followingTargetEvenIfNotSeen);
        this.pathfinder = mob;
    }

    @Override
    public boolean canUse() {
        if (pathfinder instanceof IKillCooldown killCooldown) {
            return pathfinder.getTarget() != null && pathfinder.getTarget().isAlive() && killCooldown.incrementCooldown() <= 0 && super.canUse();
        }
        return false;
    }
}
