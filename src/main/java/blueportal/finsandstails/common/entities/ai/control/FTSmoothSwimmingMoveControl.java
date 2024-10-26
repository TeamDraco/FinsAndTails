package blueportal.finsandstails.common.entities.ai.control;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;

public class FTSmoothSwimmingMoveControl extends SmoothSwimmingMoveControl {

    public FTSmoothSwimmingMoveControl(Mob mob, int maxTurnX, int maxTurnY, float inWaterSpeedModifier, float outsideWaterSpeedModifier, boolean applyGravity) {
        super(mob, maxTurnX, maxTurnY, inWaterSpeedModifier, outsideWaterSpeedModifier, applyGravity);
    }

    @Override
    public void tick() {
        super.tick();

        if (mob.getTarget() != null && mob.getTarget().isAlive() && mob.isInWater()) {
            this.mob.setDeltaMovement(this.mob.getDeltaMovement().add(0.0D, -0.0005D, 0.0D));
        }
    }
}
